package com.security.oauth;

import com.security.oauth.config.JwtTokenProvider;
import com.security.oauth.config.WebSecurityconfig;
import com.security.oauth.user.User;
import com.security.oauth.user.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class LoginController {
    @Autowired
    UserDao userDao;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping(value = "/login")
    public String login(@RequestBody Map<String,String> body) throws Exception {
//        User user=userDao.findByUsername(body.get("username"));
//        if(user==null)throw new Exception("INCORRECT");
//        if(passwordEncoder.matches(body.get("password"),user.getPassword())) {
//            return jwtTokenProvider.createToken(body.get("username"));
//        }
        //위의 방법대로 해도 되지만, authentication manager를 통해 검증을 해보려고 한다.
        if(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(body.get("username"),body.get("password"))).isAuthenticated()){
            User user = userDao.findByUsername(body.get("username"));
            return jwtTokenProvider.createToken(body.get("username"),user.getKoreanname());
        }
        else throw new Exception("INCORRECT");
        //else return "Wrong Password!";
    }
}
