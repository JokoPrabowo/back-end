package org.binaracademy.finalproject.services.impl;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.binaracademy.finalproject.dto.Response.OrderResponse;
import org.binaracademy.finalproject.dto.Response.TicketResponse;
import org.binaracademy.finalproject.services.InvoiceService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class InvoiceServiceImpl implements InvoiceService {
    @Override
    public byte[] generateOrder(OrderResponse data) {
        try{
//            File file = ResourceUtils.getFile("classpath:data/Order.jrxml");
//            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
            ClassPathResource classPath = new ClassPathResource("data/Order.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(classPath.getInputStream());
            List<Object> state = new ArrayList<>();
            data.getOrderDetail().forEach(x -> state.add(x));
            Map<String, Object> parameter = new HashMap<>();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formatDate = data.getOrderDate().format(format);
            parameter.put("EMAIL", data.getEmail());
            parameter.put("DATE", formatDate);
            parameter.put("TOTAL_PRICE", toRupiah(data.getTotalPrice()));
            parameter.put("TAX", toRupiah(data.getTax()));
            parameter.put("TOTAL_PAY", toRupiah(data.getTotalPay()));
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameter, new JRBeanCollectionDataSource(state));
            log.info("Order invoice has been created");
            return JasperExportManager.exportReportToPdf(jasperPrint);
        }catch(IOException | JRException e){
            log.error("Error detected {}", e);
            return null;
        }
    }

    @Override
    public String toRupiah(BigDecimal value) {
        BigDecimal displayVal = value.setScale(2, RoundingMode.HALF_EVEN);
        DecimalFormat toRupiah = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');
        toRupiah.setDecimalFormatSymbols(formatRp);
        return toRupiah.format(displayVal);
    }
}
