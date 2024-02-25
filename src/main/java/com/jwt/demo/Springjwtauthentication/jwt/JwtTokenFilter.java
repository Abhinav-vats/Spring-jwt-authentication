package com.jwt.demo.Springjwtauthentication.jwt;

import com.jwt.demo.Springjwtauthentication.user.Role;
import com.jwt.demo.Springjwtauthentication.user.User;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtility utility;


    private boolean hasAuthorizationHeader(HttpServletRequest request){

        String header = request.getHeader("Authorization");

        if(ObjectUtils.isEmpty(header) || !header.startsWith("Bearer ")){
            return false;
        }

        return true;

    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(!hasAuthorizationHeader(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token  = getAccessToken(request);

        if(!utility.validateAccessToken(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        setAuthenticationContext(token, request);
        filterChain.doFilter(request, response);



    }

    private void setAuthenticationContext(String token, HttpServletRequest request) {
        UserDetails userDetails = getUserDetails(token);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    private UserDetails getUserDetails(String token) {

        User user = new User();
        Claims claims = utility.parseClaims(token);

        String claimRoles = (String) claims.get("roles");

//        System.out.println(claimRoles);

        claimRoles = claimRoles.replace("[", "").replace("]", "");
        String[] roleNames  = claimRoles.split(",");

        for(String aRole: roleNames){
            user.addRole(new Role(aRole));
        }

        System.out.println(user.toString());

        String subject = (String) claims.get(Claims.SUBJECT);

        String[] subjectArray = subject.split(",");

        String[] subjects = utility.getSubject(token).split(",");
        user.setId(Integer.parseInt(subjects[0]));
        user.setEmail(subjects[1]);

        return user;

    }


    private String getAccessToken(HttpServletRequest request){
        String header = request.getHeader("Authorization");

        String token = header.split(" ")[1].trim();

        return token;
    }
}
