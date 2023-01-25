package application.controllers;

import application.dto.HouseCreateDTO;
import application.dto.HouseUpdateDTO;
import application.models.HouseModel;
import application.service.HouseService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/houses/")
@RequiredArgsConstructor
public class HouseController {

    private final HouseService houseService;

    @GetMapping
    public ResponseEntity<?> getAll(@CookieValue(value = "access_token") String access_token,
                                    HttpServletResponse response) {
        return houseService.getAll(access_token, response);
    }

    @PostMapping
    public ResponseEntity<?> createHouse(@RequestBody HouseCreateDTO houseCreateDTO,
                                  @CookieValue(value = "access_token") String access_token,
                                  HttpServletResponse response) {
        return houseService.createHouse(houseCreateDTO, access_token, response);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateHouse(@PathVariable("id") Long id,
                                         @RequestBody HouseUpdateDTO houseUpdateDTO,
                                         @CookieValue(value = "access_token") String access_token,
                                         HttpServletResponse response) {
        return houseService.updateHouse(id, houseUpdateDTO, access_token, response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteHouse(@PathVariable("id") Long id,
                                         @CookieValue(value = "access_token") String access_token,
                                         HttpServletResponse response) {
        return houseService.deleteHouse(id, access_token, response);
    }
}
