package fondos.fpvfondosbackend.aplication.services;

import fondos.fpvfondosbackend.aplication.dto.FundDto;
import fondos.fpvfondosbackend.domain.entities.FundEntity;
import fondos.fpvfondosbackend.domain.repositories.FundTestRepo;
import fondos.fpvfondosbackend.domain.repositories.IFundRepository;
import fondos.fpvfondosbackend.domain.services.IFundEventService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FundEventServiceImpl implements IFundEventService {

    private final FundTestRepo fundTestRepo;
    private final IFundRepository fundRepository;
    private final ModelMapper Mapper;

    @Autowired
    public FundEventServiceImpl(FundTestRepo fundTestRepo, IFundRepository fundRepository) {
        this.fundTestRepo = fundTestRepo;
        this.fundRepository = fundRepository;
        this.Mapper = new ModelMapper();
    }

    @Override
    public List<FundDto> getAllFunds() {
        List<FundDto> fundDtoList =  new ArrayList<>();
        fundRepository.findAll().forEach(fund -> fundDtoList.add(Mapper.map(fund, FundDto.class)));

        return fundDtoList;
    }

    @Override
    public FundDto getFundById(String id) {
        Optional<FundEntity> fund = fundTestRepo.findById(id);
        FundDto dto = FundDto.builder()
                .id(fund.get().getId())
                .nombre(fund.get().getNombre())
                .categoria(fund.get().getCategoria())
                .montoMinimo(fund.get().getMontoMinimo()).build();

        return dto;
    }
}
