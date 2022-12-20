package org.binaracademy.finalproject.services;

import org.binaracademy.finalproject.dto.Response.OrderResponse;

import java.io.OutputStream;
import java.math.BigDecimal;

public interface InvoiceService {
    byte[] generateOrder(OrderResponse data);
    String toRupiah(BigDecimal value);
}
