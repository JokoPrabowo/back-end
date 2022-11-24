package org.binaracademy.finalproject.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuestRequest {
    private String firstName;
    private String lastName;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthDate;
    private String nationality;
    private String country;
    private String passport;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate endPassport;
    private String googleId;
    private Long userId;
    private String noTelp;
    private String email;
}
