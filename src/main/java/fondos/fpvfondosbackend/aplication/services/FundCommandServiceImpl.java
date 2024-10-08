package fondos.fpvfondosbackend.aplication.services;

import fondos.fpvfondosbackend.aplication.dto.FundDto;
import fondos.fpvfondosbackend.aplication.dto.UserDto;
import fondos.fpvfondosbackend.domain.auxiliary.SubscribedFund;
import fondos.fpvfondosbackend.domain.auxiliary.TransactionHistory;
import fondos.fpvfondosbackend.domain.entities.FundEntity;
import fondos.fpvfondosbackend.domain.entities.UserEntity;
import fondos.fpvfondosbackend.domain.repositories.IFundRepository;
import fondos.fpvfondosbackend.domain.repositories.IUserRepository;
import fondos.fpvfondosbackend.domain.services.IFundCommandService;
import fondos.fpvfondosbackend.utils.FundNotSubscribedException;
import fondos.fpvfondosbackend.utils.InsufficientBalanceException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class FundCommandServiceImpl implements IFundCommandService {

    private final IUserRepository userRepository;
    private final IFundRepository fundRepository;
    private final ModelMapper mapper;

    @Autowired
    public FundCommandServiceImpl(IUserRepository userRepository, IFundRepository fundRepository ) {
        this.userRepository = userRepository;
        this.fundRepository = fundRepository;
        this.mapper = new ModelMapper();
    }

    @Override
    public FundDto execute(FundDto fund) {

        FundEntity fundEntity  = FundEntity.builder()
                                           .id(UUID.randomUUID().toString())
                                           .nombre(fund.getNombre())
                                           .montoMinimo(fund.getMontoMinimo())
                                           .categoria(fund.getCategoria()).build();

        return  mapper.map(fundRepository.saveAll(fundEntity), FundDto.class);
    }


    @Override
    public FundDto updateFund(FundDto fund) {
          if(fundRepository.findDynamoById(fund.getId()) != null) {

              FundEntity fundEntity  = FundEntity.builder()
                      .id(fund.getId())
                      .nombre(fund.getNombre())
                      .montoMinimo(fund.getMontoMinimo())
                      .categoria(fund.getCategoria()).build();

              return  mapper.map(fundRepository.saveAll(fundEntity), FundDto.class);
          }

        return null;
    }

    @Override
    public UserDto subscribeToFund(String userId, String fundId) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = formatter.format(date);
        UserEntity  user  =  userRepository.findUserById(userId);
        FundEntity  fund  =  fundRepository.findDynamoById(fundId);

       if(user.getSaldo() < fund.getMontoMinimo()) {
           throw new InsufficientBalanceException("No tiene saldo disponible para vincularse al fondo");
       }
       //restamos el monto
       user.setSaldo(user.getSaldo() - fund.getMontoMinimo());

        // Crear un nuevo registro de suscripción
        SubscribedFund subscribedFund = new SubscribedFund(
                fund.getId(),
                fund.getNombre(),
                fund.getMontoMinimo(),
                formattedDate
        );

        TransactionHistory transaction = new TransactionHistory(
                UUID.randomUUID().toString(),
                fund.getId(),
                fund.getNombre(),
                "subscription",
                fund.getMontoMinimo(),
                formattedDate
        );

        //actualizamos fondos
        user.getFondosSuscritos().add(subscribedFund);
        //actualizamos el historial
        user.getTransactionHistory().add(transaction);
        
        return mapper.map(userRepository.saveAll(user),UserDto.class);
    }

    @Override
    public void unsubscribeFromFund(String userId, String fundId) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = formatter.format(date);
        UserEntity  user  =  userRepository.findUserById(userId);

        SubscribedFund subscribedFund = user.getFondosSuscritos().stream()
                .filter(fun -> fun.getFondoId().equals(fundId))
                .findFirst()
                .orElseThrow(() -> new FundNotSubscribedException("El usuario no está suscrito al fondo con ID: " + fundId));

        user.setSaldo(user.getSaldo() + subscribedFund.getMonto());

        user.getFondosSuscritos().remove(subscribedFund);

        // Registrar la transacción de cancelación en el historial de transacciones
        TransactionHistory transaction = new TransactionHistory(
                UUID.randomUUID().toString(),
                subscribedFund.getFondoId(),
                subscribedFund.getNombreFondo(),
                "cancellation",  // Tipo de transacción: "cancellation"
                subscribedFund.getMonto(),
                formattedDate
        );
        user.getTransactionHistory().add(transaction);

        userRepository.saveAll(user);
    }

}
