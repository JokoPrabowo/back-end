package org.binaracademy.finalproject.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderTicketRequest {
    @NotNull
    List<Long> guestId;
    @NotNull
    Long scheduleId;
    @NotNull
    List<Long> seatId;
    @NotNull
    String userEmail;

}
