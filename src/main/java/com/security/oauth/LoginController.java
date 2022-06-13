package com.security.oauth;

import com.security.oauth.DTO.RegisterUserDTO;
import com.security.oauth.config.JwtTokenProvider;
import com.security.oauth.response.ResponseMessage;
import com.security.oauth.user.User;
import com.security.oauth.DTO.ReturnUserDTO;
import com.security.oauth.user.UserRepository;
import com.security.oauth.user.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserService userService;

    @PostMapping(value = "/login")
    public ResponseEntity<ResponseMessage> login(@RequestBody RegisterUserDTO registerUserDTO) throws AuthenticationException {
        Map<String,String> resultMap = userService.loginUser(registerUserDTO);

        ResponseMessage responseMessage = ResponseMessage.builder()
                .responseTime(new Date())
                .data(resultMap)
                .build();

        return new ResponseEntity<ResponseMessage>(responseMessage,HttpStatus.OK);
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<ResponseMessage> signup(RegisterUserDTO registerUserDTO){
        if(userService.findUserByUserName(registerUserDTO.getUsername())!=null){
            ResponseMessage responseMessage = ResponseMessage.builder()
                    .responseTime(new Date())
                    .data("Already Exists")
                    .build();
            return new ResponseEntity<ResponseMessage>(responseMessage,HttpStatus.ALREADY_REPORTED);
        }
        else{
            userService.registerUser(registerUserDTO);

            ResponseMessage responseMessage = ResponseMessage.builder()
                    .responseTime(new Date())
                    .data("Create Account Success")
                    .build();
            return new ResponseEntity<ResponseMessage>(responseMessage,HttpStatus.OK);
        }
    }

    @RequestMapping(value="/find")
    public ResponseEntity<ResponseMessage> findByToken(String token){
        String subject = jwtTokenProvider.getUserPk(token);
        ReturnUserDTO user = userService.findUserByUserName(subject);

        ResponseMessage responseMessage = ResponseMessage.builder()
                .responseTime(new Date())
                .data(user)
                .build();

        return new ResponseEntity<ResponseMessage>(responseMessage,HttpStatus.OK);
    }
}
