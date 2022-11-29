package org.binaracademy.finalproject.dto.Request;

import org.binaracademy.finalproject.entity.CategoryClassEntity;
import org.binaracademy.finalproject.entity.PesawatEntity;
import org.binaracademy.finalproject.entity.ScheduleTimeEntity;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

public class ScheduleRequest {

    @NotEmpty(message = "departureAiport is required")
    private String departureAiport;
    @NotEmpty(message = "arrivalAirport is required")
    private String arrivalAirport;
    @NotEmpty(message = "price is required")
    private BigDecimal price;
    @NotEmpty(message = "maxSeat is required")
    private Integer maxSeat;
    private ScheduleTimeEntity scheduleTime;
    private CategoryClassEntity categoryClass;
    private PesawatEntity pesawat;

}
