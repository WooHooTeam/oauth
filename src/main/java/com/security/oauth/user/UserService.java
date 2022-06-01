package com.security.oauth.user;

import com.security.oauth.DTO.RegisterUserDTO;
import com.security.oauth.DTO.ReturnUserDTO;
import com.security.oauth.config.JwtTokenProvider;
import com.security.oauth.response.ResponseMessage;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public ModelMapper modelMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Transactional
    public void registerUser(RegisterUserDTO registerUserDTO) {
        User user = new User();
        user.setUsername(registerUserDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerUserDTO.getPassword()));
        user.setKoreanname(registerUserDTO.getKoreanname());
        user.setUserType(1);
        userRepository.save(user);
    }

    public ReturnUserDTO findUserByUserName(RegisterUserDTO registerUserDTO){
        String username = registerUserDTO.getUsername();
        User user = userRepository.findByUsername(username);

        return modelMapper.map(user, ReturnUserDTO.class);
    }

    public Map<String,String> loginUser(RegisterUserDTO registerUserDTO){
        String username = registerUserDTO.getUsername();
        String password = registerUserDTO.getPassword();

        try {
            if (authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password)).isAuthenticated()) {
                User user = userRepository.findByUsername(username);
                String token = jwtTokenProvider.createToken(username, user.getKoreanname(), user.getBirthday());

                Map<String, String> tokenMap = new HashMap<String, String>() {{
                    put("token", token);
                }};

                return tokenMap;
            }
        }
        catch(AuthenticationException ex){
            throw ex;
        }
        return null;
    }
}
