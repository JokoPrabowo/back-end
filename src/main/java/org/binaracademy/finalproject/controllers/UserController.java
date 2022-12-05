package org.binaracademy.finalproject.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.binaracademy.finalproject.dto.Request.GuestRequest;
import org.binaracademy.finalproject.dto.Request.UserLoginRequest;
import org.binaracademy.finalproject.dto.Request.UserRegisterRequest;
import org.binaracademy.finalproject.dto.Response.CountryResponse;
import org.binaracademy.finalproject.dto.ResponseData;
import org.binaracademy.finalproject.entity.ContactGuestEntity;
import org.binaracademy.finalproject.entity.GuestEntity;
import org.binaracademy.finalproject.entity.UserEntity;
import org.binaracademy.finalproject.services.CountryService;
import org.binaracademy.finalproject.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/test")
    public String test(){
        return "OK";
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseData<Object>> create(@Valid @RequestBody UserRegisterRequest data, Errors errors){
        try{
            ResponseData<Object> res = new ResponseData();
            if(errors.hasErrors()){
                res.setSuccess(false);
                res.setStatusCode(HttpStatus.BAD_REQUEST.value());
                res.setMessage("Failed! ");
                res.setData(null);
                errors.getAllErrors().forEach(x ->{
                    System.out.println(x);
                });
                return ResponseEntity.badRequest().body(res);
            }

            UserEntity user = userService.create(new UserEntity(data.getUsername(),
                    data.getEmail(), data.getPassword()));

            res.setSuccess(true);
            res.setStatusCode(HttpStatus.CREATED.value());
            res.setMessage("Successfully!");
            res.setData(user);

            return ResponseEntity.ok(res);
        }catch (Exception e){
            ResponseData res = new ResponseData();
            res.setSuccess(false);
            res.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            res.setMessage("Failed!");
            res.setData(null);
            return ResponseEntity.internalServerError().body(res);
        }
    }


}
