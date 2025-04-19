package fondos.fpvfondosbackend.infrastructure.adapters.inbound.rest.controller;

import fondos.fpvfondosbackend.aplication.usecases.BorradoGeneral;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/delete")
public class DeleteController {

    private final BorradoGeneral borradoGeneral;

    @Autowired
    public DeleteController(BorradoGeneral borradoGeneral) {
        this.borradoGeneral = borradoGeneral;
    }

    @DeleteMapping("{id}")
    public void borrar(@PathVariable  String id){
        borradoGeneral.borrar(id);
    }

}
