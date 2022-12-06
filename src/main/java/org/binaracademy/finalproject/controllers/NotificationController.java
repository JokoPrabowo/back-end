package org.binaracademy.finalproject.controllers;

import lombok.RequiredArgsConstructor;
import org.binaracademy.finalproject.dto.Response.NotificationResponse;
import org.binaracademy.finalproject.dto.ResponseData;
import org.binaracademy.finalproject.entity.NotificationEntity;
import org.binaracademy.finalproject.services.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/notification/{userId}")
    public ResponseEntity<ResponseData<List<NotificationResponse>>> getNotification(@PathVariable("userId") Long userId){
        ResponseData<List<NotificationResponse>> response = new ResponseData<>();
        try {
            List<NotificationResponse> notifications = new ArrayList<>();
            notificationService.getAllNotifByUserId(userId).forEach(s -> {
                notifications.add(NotificationResponse.builder()
                        .id(s.getId())
                        .content(s.getContent())
                        .orderId(s.getOrderId())
                        .userId(s.getUserId())
                        .date(s.getCreateAt())
                        .build());
            });
            response.setData(notifications);
            response.setSuccess(true);
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("Successfully!");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.setSuccess(false);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
            response.setData(null);
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/getNotification/{notifId}")
    public ResponseEntity<ResponseData<NotificationEntity>> getOneNotification(@PathVariable("notifId") Long notifId){
        ResponseData<NotificationEntity> response = new ResponseData<>();
        try {
            NotificationEntity notifications = notificationService.getById(notifId);
            response.setData(notifications);
            response.setSuccess(true);
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("Successfully!");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.setSuccess(false);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
            response.setData(null);
            return ResponseEntity.internalServerError().body(response);
        }
    }

}
