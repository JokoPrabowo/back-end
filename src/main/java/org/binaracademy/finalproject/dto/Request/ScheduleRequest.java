package org.binaracademy.finalproject.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.binaracademy.finalproject.entity.ScheduleTimeEntity;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleRequest {

    @NotEmpty(message = "departureAiport is required")
    private String departureAiport;
    @NotEmpty(message = "arrivalAirport is required")
    private String arrivalAirport;
    @NotEmpty(message = "price is required")
    private BigDecimal price;
    @NotEmpty(message = "maxSeat is required")
    private Integer maxSeat;
    @NotEmpty(message = "date is required")
    private LocalDate date;
    @NotEmpty(message = "scheduleTime is required")
    private Long scheduleTimeId;
    @NotEmpty(message = "categoryClass is required")
    private Long categoryClassId;
    @NotEmpty(message = "pesawat is required")
    private Long pesawatId;

}
