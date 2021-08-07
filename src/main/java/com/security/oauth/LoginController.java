package com.security.oauth;

import com.security.oauth.config.JwtTokenProvider;
import com.security.oauth.config.WebSecurityconfig;
import com.security.oauth.user.User;
import com.security.oauth.user.UserDTO;
import com.security.oauth.user.UserDao;
import com.security.oauth.user.UserInformation;
import io.jsonwebtoken.Jwts;
import org.modelmapper.ModelMapper;
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

    @Autowired
    ModelMapper modelMapper;

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
            return jwtTokenProvider.createToken(body.get("username"),user.getKoreanname(),user.getBirthday());
        }
        else throw new Exception("INCORRECT");
        //else return "Wrong Password!";
    }
    @PostMapping(value = "/signin")
    public String signin(@RequestParam(value="username") String username, @RequestParam("password") String password,@RequestParam("koreanname")String koreanname){
        if(userDao.findByUsername(username)!=null){
            return "Already Exists";
        }
        else{
            User user = new User();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));
            user.setKoreanname(koreanname);
            user.setUserType(1);
            userDao.save(user);
            return "success";
        }
    }
    @RequestMapping(value="/find")
    public UserDTO findByToken(@RequestBody Map<String,String> body){
        String token = body.get("token");
        String subject = jwtTokenProvider.getUserPk(token);
        User user = userDao.findByUsername(subject);
        return modelMapper.map(user,UserDTO.class);
    }
    @PostMapping(value="/practice")
    public String asf(){
        System.out.println("ff");
        return "ffff";
    }
}
