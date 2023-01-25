package application.service;

import application.dto.JwtTokenGenerated;
import application.dto.LoginRequest;
import application.models.UserModel;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorizeService {
    private final JwtTokenService jwtTokenService;
    private final UserService userService;

    public ResponseEntity<?> login(LoginRequest request, HttpServletResponse response) {

        Optional<UserModel> userModelOptional = userService.findById(request.getId());
        if (userModelOptional.isPresent()) {
            UserModel userModel = userModelOptional.get();

            if (userModel.getPassword().equals(request.getPassword())) {
                JwtTokenGenerated jwt = jwtTokenService.generateToken(userModel.getId());

                Cookie cookie = new Cookie("access_token", jwt.getToken());
                cookie.setPath("/");

                int maxAge = (int) ((jwt.getExpire().getTime() - new Date().getTime()) / 1000);

                cookie.setMaxAge(maxAge);

                response.addCookie(cookie);
                response.setContentType("text/plain");

                return ResponseEntity.ok().body(jwt);
            }
            return ResponseEntity.badRequest().body("Пароли не совпадают");
        }
        return ResponseEntity.badRequest().body("Пользователя нет");
    }

    public ResponseEntity<?> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("access_token", "");
        cookie.setPath("/");
        cookie.setMaxAge(-1);
        response.addCookie(cookie);
        response.setContentType("text/plain");
        return ResponseEntity.ok().body("OK");
    }
}
