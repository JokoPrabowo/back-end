package org.binaracademy.finalproject.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsRequest {

    @NotNull(message = "user_id shouldn't be null")
    private LocalDate birthDate;

    @NotNull(message = "user_id shouldn't be null")
    private String gender;

    @NotNull(message = "user_id shouldn't be null")
    private String address;

    @NotNull(message = "user_id shouldn't be null")
    private Long user_id;

}
