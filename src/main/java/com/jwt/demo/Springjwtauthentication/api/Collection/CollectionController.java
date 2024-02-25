package com.jwt.demo.Springjwtauthentication.api.Collection;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.annotation.security.RolesAllowed;
import java.net.URI;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/collection")
public class CollectionController {

    @Autowired
    private CollectionService collectionService;


    @PostMapping("/add")
    ResponseEntity<?> addNewCollection(@RequestBody Optional<CollectionReq> collection, @RequestHeader Optional<String> userId){
        try {

            if(!collection.isPresent()){
            throw new HttpClientErrorException(HttpStatus.NO_CONTENT, "Expected request body missing");
            }
            if(!userId.isPresent()){
                throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "User is not authorized");
            }
            URI collUri = URI.create("/collections");

            return ResponseEntity.created(collUri).body(collectionService.addNewCollection((CollectionReq) collection.get(), userId.get()));



        }catch(HttpClientErrorException exception){
            Map<String, Object> errorMap = new LinkedHashMap<>();

            errorMap.put("status", exception.getStatusText());
            errorMap.put("statusCode", exception.getStatusCode());
            errorMap.put("reason", exception.getStatusText());

            return new ResponseEntity<>(errorMap, exception.getStatusCode());

        }


    }
}
