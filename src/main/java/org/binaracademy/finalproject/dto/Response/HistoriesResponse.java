package org.binaracademy.finalproject.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
public class HistoriesResponse {
    private BigDecimal totalPrice;
    private BigDecimal tax;
    private BigDecimal totalPay;
    private Long scheduleId;
    private LocalDateTime expiredAt;
}
