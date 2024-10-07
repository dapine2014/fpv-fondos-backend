package fondos.fpvfondosbackend.aplication.services;

import fondos.fpvfondosbackend.aplication.dto.FundDto;
import fondos.fpvfondosbackend.domain.entities.FundEntity;
import fondos.fpvfondosbackend.domain.repositories.IFundRepository;
import fondos.fpvfondosbackend.domain.services.IFundService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FundServiceImpl implements IFundService {

    private final IFundRepository fundRepository;
    private final ModelMapper Mapper;

    @Autowired
    public FundServiceImpl(IFundRepository fundRepository ) {
        this.fundRepository = fundRepository;
        this.Mapper = new ModelMapper();
    }

    @Override
    public FundDto execute(FundDto fund) {

        FundEntity fundEntity  = FundEntity.builder()
                                           .id(UUID.randomUUID().toString())
                                           .nombre(fund.getNombre())
                                           .montoMinimo(fund.getMontoMinimo())
                                           .categoria(fund.getCategoria()).build();

        return  Mapper.map(fundRepository.saveAll(fundEntity), FundDto.class);
    }


    @Override
    public FundDto updateFund(FundDto fund) {
          if(fundRepository.findDynamoById(fund.getId()) != null) {

              FundEntity fundEntity  = FundEntity.builder()
                      .id(fund.getId())
                      .nombre(fund.getNombre())
                      .montoMinimo(fund.getMontoMinimo())
                      .categoria(fund.getCategoria()).build();

              return  Mapper.map(fundRepository.saveAll(fundEntity), FundDto.class);
          }

        return null;
    }

}
