package fondos.fpvfondosbackend.infrastructure.adapters.inbound.rest.controller;

import fondos.fpvfondosbackend.aplication.dto.FundDto;
import fondos.fpvfondosbackend.aplication.ports.inbound.IReplaceService;
import fondos.fpvfondosbackend.aplication.ports.inbound.ISubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/funds")
public class SuscripcionController {

    private final ISubscriptionService subscriptionService;
    private final IReplaceService replaceService;

    @Autowired
    public SuscripcionController(ISubscriptionService subscriptionService, IReplaceService replaceService) {
        this.subscriptionService = subscriptionService;
        this.replaceService = replaceService;
    }

    @PostMapping("/create")
    public ResponseEntity<Object> subscribeFund(@RequestBody FundDto fund) {
        try {
            // Ejecutar el caso de uso de suscripción
            FundDto result = subscriptionService.createSubscription(fund);
            // Respuesta exitosa con el fondo creado
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

}

