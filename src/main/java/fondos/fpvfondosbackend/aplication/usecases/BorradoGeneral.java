package fondos.fpvfondosbackend.aplication.usecases;

import fondos.fpvfondosbackend.domain.repositories.FundTestRepo;
import org.springframework.stereotype.Component;

@Component
public class BorradoGeneral {

    private final FundTestRepo repo;

    public BorradoGeneral(FundTestRepo repo) {
        this.repo = repo;
    }

    public void borrar(String id) {
        repo.Delete(id);
    }
}
