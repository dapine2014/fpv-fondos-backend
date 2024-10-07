package fondos.fpvfondosbackend.aplication.services;

import fondos.fpvfondosbackend.aplication.dto.FundDto;
import fondos.fpvfondosbackend.domain.repositories.IFundRepository;
import fondos.fpvfondosbackend.domain.services.IFundListService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class FundListServiceImpl implements IFundListService {

    private final IFundRepository fundRepository;
    private final ModelMapper Mapper;

    @Autowired
    public FundListServiceImpl(IFundRepository fundRepository) {
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
        return Mapper.map(fundRepository.findDynamoById(id),FundDto.class);
    }
}
