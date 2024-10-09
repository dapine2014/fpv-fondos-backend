package fondos.fpvfondosbackend.infrastructure.adapters.inbound.rest.controller;

import fondos.fpvfondosbackend.aplication.dto.FundDto;
import fondos.fpvfondosbackend.aplication.ports.inbound.IReplaceService;
import fondos.fpvfondosbackend.aplication.ports.inbound.ICreateFundService;
import fondos.fpvfondosbackend.aplication.ports.outbound.IReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/funds")
public class FundController {

    private final ICreateFundService subscriptionService;
    private final IReplaceService replaceService;
    private final IReadService readService;

    @Autowired
    public FundController(ICreateFundService subscriptionService, IReplaceService replaceService, IReadService readService) {
        this.subscriptionService = subscriptionService;
        this.replaceService = replaceService;
        this.readService = readService;
    }

    @PostMapping("/create")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Object> subscribeFund(@RequestBody FundDto fund) {
        try {
            // Ejecutar el caso de uso de suscripción
            FundDto result = subscriptionService.createFund(fund);
            // Respuesta exitosa con el fondo creadov
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (IllegalArgumentException e) {
            // Manejo de excepciones de negocio (p. ej., validaciones fallidas)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            // Manejo de cualquier otra excepción inesperada
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocurrió un error inesperado al crear el fondo.");
        }
    }

    @PutMapping("/update")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Object> updateFund(@RequestBody FundDto fund) {
        try {
            FundDto result = replaceService.replaceFund(fund);

            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (IllegalArgumentException e){

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error inesperado al actualizar el fondo.");
        }
    }

    @GetMapping("/read")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Object> readAllFund() {
       try {
          return  ResponseEntity.status(HttpStatus.OK).body(readService.readFundAll());
       } catch (IllegalArgumentException e){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
       } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error inesperado en la consulta del fondo.");
       }
    }

    @GetMapping("/read/{data}")
    public ResponseEntity<Object> readFundById(@PathVariable String data) {
      try {
          return  ResponseEntity.status(HttpStatus.OK).body(readService.readFundById(data));
      } catch (IllegalArgumentException e){
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
      } catch (Exception e) {
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error inesperado en la consulta del fondo.");
      }
    }

}

