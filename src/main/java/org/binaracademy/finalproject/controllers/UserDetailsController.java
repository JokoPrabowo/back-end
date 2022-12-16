package org.binaracademy.finalproject.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.binaracademy.finalproject.dto.Request.UserDetailsRequest;
import org.binaracademy.finalproject.dto.Request.UserRegisterRequest;
import org.binaracademy.finalproject.dto.ResponseData;
import org.binaracademy.finalproject.entity.TicketEntity;
import org.binaracademy.finalproject.entity.UserDetailsEntity;
import org.binaracademy.finalproject.entity.UserEntity;
import org.binaracademy.finalproject.services.TicketService;
import org.binaracademy.finalproject.services.UsersDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "UserDetails", description = "Operation about User Details")
public class UserDetailsController {
    private final UsersDetailsService userDetailsService;

    @PostMapping("/user/edit_profile")
    public ResponseEntity<ResponseData<Object>> create(@Valid @RequestBody UserDetailsRequest data, Errors errors){
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

            UserDetailsEntity user = userDetailsService.create(
                                        new UserDetailsEntity(
                                                data.getBirthDate(),
                                                data.getGender(),
                                                data.getAddress(),
                                                data.getUser_id()));

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

    @GetMapping("/get/user/edit_profile/{user_id}")
    public ResponseEntity<ResponseData<UserDetailsEntity>> findByGuestId(@PathVariable Long user_id){
        ResponseData<UserDetailsEntity> response = new ResponseData<>();

        UserDetailsEntity exist = userDetailsService.findByUserid(user_id);
        if (exist != null){
            response.setData(exist);
            response.setSuccess(true);
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("Successfully!");
            return ResponseEntity.ok(response);
        }
        else{
            response.setData(null);
            response.setSuccess(true);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("No user details found");
            return ResponseEntity.internalServerError().body(response);

        }
    }

}
