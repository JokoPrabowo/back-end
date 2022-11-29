package org.binaracademy.finalproject.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TimeData {
    private Long id;
    private LocalTime start_time;
    private LocalTime end_time;
}
