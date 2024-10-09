package fondos.fpvfondosbackend.infrastructure.adapters.inbound.rest.controller;


import fondos.fpvfondosbackend.aplication.dto.RegisterDto;
import fondos.fpvfondosbackend.aplication.dto.UserDto;
import fondos.fpvfondosbackend.aplication.ports.inbound.IUserSucribeToFundService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class SuscripController {

    private final IUserSucribeToFundService sucribeToFundService;

    @Autowired
    public SuscripController(IUserSucribeToFundService sucribeToFundService) {
        this.sucribeToFundService = sucribeToFundService;
    }

    @PostMapping("/subscribe")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Object> sucribeToFund(@RequestBody RegisterDto registerDto) {
        try {
            UserDto userDto = sucribeToFundService.sucribeToFund(registerDto.getUserId(), registerDto.getFundId());
            return ResponseEntity.status(HttpStatus.OK).body("User subscribed to fund successfully." + userDto.getId() );
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error inesperado en la consulta del fondo."+ e.getMessage());
        }
    }

    @PostMapping("/unsubscribe")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Object> unsubscribeFromFund(@RequestBody RegisterDto registerDto) {
        try {
            sucribeToFundService.unsubscribeFromFund(registerDto.getUserId(), registerDto.getFundId());
            return ResponseEntity.status(HttpStatus.OK).body("User unsubscribed from fund successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred while unsubscribing from the fund." + e.getMessage());
        }
    }
}
