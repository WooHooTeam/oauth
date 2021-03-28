package com.security.oauth;

import com.security.oauth.config.JwtTokenProvider;
import com.security.oauth.config.WebSecurityconfig;
import com.security.oauth.user.User;
import com.security.oauth.user.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping(value = "/login")
    public String login(@RequestBody Map<String,String> body){
        User user=userDao.findByUsername(body.get("username"));
        if(user==null)return "Your Username is incorrect";
        if(passwordEncoder.matches(body.get("password"),user.getPassword())) {

            return jwtTokenProvider.createToken(body.get("username"));
        }
        else return "Wrong Password!";
    }
}
