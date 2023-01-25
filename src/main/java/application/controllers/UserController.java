package application.controllers;

import application.dto.UserDTO;
import application.models.UserModel;
import application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/")
public class UserController {
    private final UserService userService;

    @PostMapping
    public UserModel createUser(@RequestBody UserDTO request) {
        return userService.createUser(request);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO dto,
                                     @PathVariable("id") Long id) throws Exception {
        return userService.updateUser(id, dto);
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
    }

    @GetMapping
    public List<UserModel> getAll() {
        return userService.getAll();
    }

    @GetMapping("{id}")
    public Optional<UserModel> getOne(@PathVariable("id") Long id) {
        return userService.findById(id);
    }
}
