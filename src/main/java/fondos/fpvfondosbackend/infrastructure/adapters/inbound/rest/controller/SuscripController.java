package fondos.fpvfondosbackend.infrastructure.adapters.inbound.rest.controller;

import fondos.fpvfondosbackend.aplication.dto.UserDto;
import fondos.fpvfondosbackend.aplication.ports.inbound.IUserSucribeToFundService;
import fondos.fpvfondosbackend.utils.InsufficientBalanceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class SuscripController {

    private final IUserSucribeToFundService sucribeToFundService;

    @Autowired
    public SuscripController(IUserSucribeToFundService sucribeToFundService) {
        this.sucribeToFundService = sucribeToFundService;
    }

    @GetMapping("/suscrip/{userId}/{fundId}")
    public ResponseEntity<Object> sucribeToFund(@PathVariable String userId, @PathVariable String fundId) {
        try {
            UserDto userDto = sucribeToFundService.sucribeToFund(userId, fundId);
            return ResponseEntity.status(HttpStatus.OK).body("User subscribed to fund successfully." + userDto.getId() );
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error inesperado en la consulta del fondo."+ e.getMessage());
        }
    }

    @GetMapping("/unsubscribe/{userId}/{fundId}")
    public ResponseEntity<Object> unsubscribeFromFund(@PathVariable String userId, @PathVariable String fundId) {
        try {
            sucribeToFundService.unsubscribeFromFund(userId, fundId);
            return ResponseEntity.status(HttpStatus.OK).body("User unsubscribed from fund successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred while unsubscribing from the fund." + e.getMessage());
        }
    }
}
