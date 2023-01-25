package application.service;

import application.dto.UserDTO;
import application.models.UserModel;
import application.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserModel createUser(UserDTO userDTO) {
        UserModel user = new UserModel();

        user.setAge(userDTO.getAge());
        user.setName(userDTO.getName());
        user.setPassword(userDTO.getPassword());

        return userRepository.save(user);
    }

    public Optional<UserModel> findById(Long id) {
        return userRepository.findById(id);
    }

    public List<UserModel> getAll() {
        return userRepository.findAll();
    }

    public ResponseEntity<?> updateUser(Long id, UserDTO dto) throws Exception {
        Optional<UserModel> userOptional = this.findById(id);

        if(userOptional.isPresent()) {
            UserModel user = userOptional.get();

            user.setName(dto.getName());
            user.setAge(dto.getAge());
            user.setPassword(dto.getPassword());

            return ResponseEntity.ok().body(userRepository.save(user));
        }
        return ResponseEntity.badRequest().body("Такого пользователя не существует");
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
