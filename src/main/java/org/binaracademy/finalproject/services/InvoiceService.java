package org.binaracademy.finalproject.services;

import org.binaracademy.finalproject.dto.Response.OrderResponse;

import java.io.OutputStream;
import java.math.BigDecimal;

public interface InvoiceService {
    void generateOrder(OrderResponse data, OutputStream output);
    String toRupiah(BigDecimal value);
}
