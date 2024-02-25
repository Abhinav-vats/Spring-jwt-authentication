package com.jwt.demo.Springjwtauthentication.jwt;

import com.jwt.demo.Springjwtauthentication.user.User;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;

@Component
public class JwtTokenUtility {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtility.class);

    private static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000;

    @Value("${app.jwt.secret}")
    private String secretKey;

    public String generateAccessToken(User user){
        return Jwts.builder()
                .setSubject(user.getId()+","+user.getEmail())
                .claim("roles", user.getRoles().toString())
                .setIssuer("Abhinav")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRE_DURATION))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }


    public boolean validateAccessToken(String token){

        try{
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        }catch(ExpiredJwtException expEx){
            LOGGER.error("JWT expired: ", expEx.getMessage());
        }catch(IllegalArgumentException expEx){
            LOGGER.error("Token is null, empty or has only whitespaces ", expEx.getMessage());
        }catch(MalformedJwtException expEx){
            LOGGER.error("JWT is invalid : ", expEx.getMessage());
        }catch(UnsupportedJwtException expEx){
            LOGGER.error("JWT is not supported", expEx.getMessage());
        }catch(SignatureException expEx){
            LOGGER.error("Signature validation failed", expEx.getMessage());
        }

        return false;
    }

    public String getSubject(String token){
        return parseClaims(token).getSubject();

    }

    public Claims parseClaims(String token){
        return  Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean checkAllowedRoles(String token, String... args){
        Boolean isAllowed = false;
        Claims claims = parseClaims(token);
        String claimRoles = (String) claims.get("roles");   //        System.out.println(claimRoles);
        claimRoles = claimRoles.replace("[", "").replace("]", "");
        String[] roleNames  = claimRoles.split(",");
        for (String arg : roleNames) {
            isAllowed = Arrays.stream(args).anyMatch(a -> a.equalsIgnoreCase(arg));
            if(isAllowed){
                break;
            }
        }
        return  isAllowed;
    }
}
