package application.service;

import application.dto.HouseCreateDTO;
import application.dto.HouseUpdateDTO;
import application.models.HouseModel;
import application.models.UserModel;
import application.repository.HouseRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HouseService {

    private final JwtTokenService jwtTokenService;
    private final HouseRepository houseRepository;
    private final AuthorizeService authorizeService;
    private final UserService userService;

    public ResponseEntity<?> getAll(String accessToken, HttpServletResponse response) {
        if(jwtTokenService.validToken(accessToken)) {
            Long userId = jwtTokenService.parseToken(accessToken);
            return ResponseEntity.ok().body(houseRepository.getUserHouses(userId));
        } else {
            return authorizeService.logout(response);
        }
    }

    public ResponseEntity<?>  createHouse(HouseCreateDTO houseCreateDTO, String accessToken, HttpServletResponse response) {
        if(jwtTokenService.validToken(accessToken)) {
            HouseModel houseModel = new HouseModel();

            houseModel.setAddress(houseCreateDTO.getAddress());
            houseModel.setOwner_id(jwtTokenService.parseToken(accessToken));

            return ResponseEntity.ok(houseRepository.save(houseModel));
        } else {
            return authorizeService.logout(response);
        }
    }

    public ResponseEntity<?> updateHouse(Long id, HouseUpdateDTO houseUpdateDTO, String accessToken, HttpServletResponse response) {
        if(jwtTokenService.validToken(accessToken)) {
            Long userId = jwtTokenService.parseToken(accessToken);

            Optional<HouseModel> houseModelOptional = houseRepository.getUserHouse(id, userId);

            if (houseModelOptional.isPresent()) {
                HouseModel houseModel = houseModelOptional.get();

                Long userIdSet = houseUpdateDTO.getUserId();

                Optional<UserModel> userModelOptional = userService.findById(userIdSet);

                if (userModelOptional.isPresent()) {
                    UserModel userModel = userModelOptional.get();

                    switch (houseUpdateDTO.getAction()) {
                        case REMOVE_MEMBER -> houseRepository.save(houseModel.removeMember(userModel));
                        case ADD_MEMBER -> houseRepository.save(houseModel.addMember(userModel));
                    }
                    return ResponseEntity.ok().body(String.format("Была произведена опреация с участником с id %s в доме %s", userIdSet, id));
                }

                return ResponseEntity.badRequest().body("Пользователя c id " + userIdSet + " нет в базе");
            }

            return ResponseEntity.badRequest().body("Такого дома не существует/либо вы не владелец");
        } else {
            return authorizeService.logout(response);
        }
    }

    public ResponseEntity<?> deleteHouse(Long id, String accessToken, HttpServletResponse response) {
        if(jwtTokenService.validToken(accessToken)) {
            Long userId = (Long) jwtTokenService.parseToken(accessToken);

            return ResponseEntity.ok().body(houseRepository.deleteUserHouse(id, userId));
        } else {
            return authorizeService.logout(response);
        }
    }
}
