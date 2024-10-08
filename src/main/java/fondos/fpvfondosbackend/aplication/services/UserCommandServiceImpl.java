package fondos.fpvfondosbackend.aplication.services;

import fondos.fpvfondosbackend.aplication.dto.SubscribedFundDto;
import fondos.fpvfondosbackend.aplication.dto.TransactionHistoryDto;
import fondos.fpvfondosbackend.aplication.dto.UserDto;
import fondos.fpvfondosbackend.domain.auxiliary.SubscribedFund;
import fondos.fpvfondosbackend.domain.auxiliary.TransactionHistory;
import fondos.fpvfondosbackend.domain.entities.UserEntity;
import fondos.fpvfondosbackend.domain.repositories.IUserRepository;
import fondos.fpvfondosbackend.domain.services.IUserCommandService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserCommandServiceImpl implements IUserCommandService {

    private final IUserRepository userRepository;
    private final ModelMapper mapper;

    @Autowired
    public UserCommandServiceImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
        this.mapper = new ModelMapper();
    }

    @Override
    public UserDto execute(UserDto userDto) {
        UserEntity userEntity = UserEntity.builder()
                                .id(UUID.randomUUID().toString())
                                .nombre(userDto.getNombre())
                                .email(userDto.getEmail())
                                .telefono(userDto.getTelefono())
                                .saldo(userDto.getSaldo())
                                .fondosSuscritos( convertSubscribedFunds(userDto.getFondosSuscritos()))
                                .transactionHistory(convertTransactionHistory(userDto.getTransactionHistory()))
                                .build();
        UserEntity savedUserEntity = userRepository.saveAll(userEntity);

        return mapper.map(savedUserEntity, UserDto.class);
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        UserEntity user  =  userRepository.findUserById(userDto.getId());
        if( user != null){
            user.setNombre(userDto.getNombre());
            user.setEmail(userDto.getEmail());
            user.setTelefono(userDto.getTelefono());
            user.setSaldo(userDto.getSaldo());
            user.setFondosSuscritos(convertSubscribedFunds(userDto.getFondosSuscritos()));
            user.setTransactionHistory(convertTransactionHistory(userDto.getTransactionHistory()));
            UserEntity updatedUserEntity = userRepository.saveAll(user);

            return mapper.map(updatedUserEntity, UserDto.class);
        }

        return null;
    }

    private List<SubscribedFund> convertSubscribedFunds(List<SubscribedFundDto> subscribedFunds) {
        List<SubscribedFund> subscribedFundList = new ArrayList<>();
        subscribedFunds.forEach(subscribedFund -> subscribedFundList.add( mapper.map(subscribedFund,SubscribedFund.class)));

        return subscribedFundList;
    }

    private List<TransactionHistory> convertTransactionHistory(List<TransactionHistoryDto> transactionHistoryDtos) {
        List<TransactionHistory> transactionHistoryList = new ArrayList<>();
        transactionHistoryDtos.forEach(transactionHistoryDto -> transactionHistoryList.add(mapper.map(transactionHistoryDto, TransactionHistory.class)));

        return transactionHistoryList;
    }


}
