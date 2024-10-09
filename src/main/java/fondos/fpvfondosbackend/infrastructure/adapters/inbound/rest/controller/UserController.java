package fondos.fpvfondosbackend.infrastructure.adapters.inbound.rest.controller;

import fondos.fpvfondosbackend.aplication.dto.UserDto;
import fondos.fpvfondosbackend.aplication.ports.inbound.IUserCreateService;
import fondos.fpvfondosbackend.aplication.ports.outbound.IListUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final IUserCreateService userCreateService;
    private final IListUserService listUserService;

    @Autowired
    public UserController(IUserCreateService userCreateService, IListUserService listUserService) {
        this.userCreateService = userCreateService;
        this.listUserService = listUserService;
    }


    @PostMapping("/create")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Object> createUser(@RequestBody UserDto userDto) {
        try {
            UserDto result = userCreateService.registerUser(userDto);

            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (IllegalArgumentException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e){

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocurrió un error inesperado al crear el usuario.");
        }
    }

    @GetMapping("/read")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Object> listUsers() {
        try {
            List<UserDto> users= listUserService.getListUsers();

            return ResponseEntity.status(HttpStatus.OK).body(users);
        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocurrió un error inesperado al obtener la lista de usuarios.");
        }
    }

}
