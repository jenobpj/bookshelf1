package com.revature.project2.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.project2.model.User;
import com.revature.project2.model.UserJwtDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;

@Service
public class JwtService {

    private Key key;

    public JwtService(){
        byte[] secret="my_secret_passwordhkjhkgkgjhghjgkjgkgfgftufutfdsfsdfsdfgsdgsdgdgsdg".getBytes();
        key= Keys.hmacShaKeyFor(secret);
    }

    public  String createJwt(User user) throws JsonProcessingException {

        UserJwtDto dto=new UserJwtDto(user.getId(),user.getFirst_name(),user.getLast_name(),user.getEmailId(),
                user.getUserRole().getUserRole());

        String jwt= Jwts.builder()
                .claim("user_dto",new ObjectMapper().writeValueAsString(dto))
                .signWith(key)
                .compact();
        return  jwt;
    }


    public UserJwtDto parseJwt(String jwt) throws JsonProcessingException {

     Jws<Claims> token =Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);

     String dtoString = (String) token.getBody().get("user_dto");

     return (new ObjectMapper()).readValue(dtoString, UserJwtDto.class);
    }
}
