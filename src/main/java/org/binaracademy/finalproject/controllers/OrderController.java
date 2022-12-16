package org.binaracademy.finalproject.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.binaracademy.finalproject.dto.Response.HistoriesResponse;
import org.binaracademy.finalproject.dto.Response.OrderResponse;
import org.binaracademy.finalproject.dto.Response.TicketResponse;
import org.binaracademy.finalproject.dto.ResponseData;
import org.binaracademy.finalproject.entity.OrderEntity;
import org.binaracademy.finalproject.repositories.OrderRepo;
import org.binaracademy.finalproject.services.InvoiceService;
import org.binaracademy.finalproject.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Order", description = "Operation about Order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @Autowired
    InvoiceService invoiceService;

    @Operation(summary = "Get order histories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = @Content(examples = {
                    @ExampleObject(name = "List Orders",
                            description = "Menampilkan semua transaksi yang telah dipesan",
                            value = "{\n"
                                    + "    \"success\": true,\n"
                                    + "    \"statusCode\": 200,\n"
                                    + "    \"message\": \"Successfully!\",\n"
                                    + "    \"data\": [\n"
                                    + "        {\n"
                                    + "            \"totalPrice\": 1600000,\n"
                                    + "            \"tax\": \"160000\"\n"
                                    + "            \"totalPay\": \"176000\"\n"
                                    + "            \"scheduleId\": \"1\"\n"
                                    + "            \"expiredAt\": \"2022-12-08\"\n"
                                    + "        },\n"
                                    + "        {\n"
                                    + "            \"totalPrice\": 1200000,\n"
                                    + "            \"tax\": \"120000\"\n"
                                    + "            \"totalPay\": \"132000\"\n"
                                    + "            \"scheduleId\": \"2\"\n"
                                    + "            \"expiredAt\": \"2022-12-27\"\n"
                                    + "        }\n"
                                    + "    ]\n"
                                    + "}")
            },  mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "500", content = @Content(examples = {
                    @ExampleObject(name = "Server Error",
                            description = "Tampilan jika server error",
                            value = "{\n"
                                    + "    \"success\": false,\n"
                                    + "    \"statusCode\": 500,\n"
                                    + "    \"message\": \"Server Error Message\",\n"
                                    + "    \"data\": []\n"
                                    + "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @GetMapping("/getHistories/{email}")
    public ResponseEntity<ResponseData<List<HistoriesResponse>>> getHistories(@PathVariable String email) {

        ResponseData<List<HistoriesResponse>> response = new ResponseData<>();

        try {
            List<HistoriesResponse> data = new ArrayList<>();
            orderService.FindMyOrders(email).forEach(order ->
                    data.add(HistoriesResponse.builder()
                            .totalPrice(order.getTotalPrice())
                            .tax(order.getTax())
                            .totalPay(order.getTotalPay())
                            .scheduleId(order.getScheduleId())
                            .expiredAt(order.getExpiredAt()).build()));
            response.setSuccess(true);
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("Successfully!");
            response.setData(data);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
            response.setData(null);
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/generateOrder/{id}")
    public ResponseEntity<ResponseData<OrderResponse>> generateFile(HttpServletResponse response, @PathVariable Long id){
        ResponseData<OrderResponse> data = new ResponseData<>();
        try{
            OrderEntity order = orderService.getById(id);
            List<TicketResponse> ticket = new ArrayList<>();
            order.getTicket().forEach(x -> ticket.add(TicketResponse.builder()
                    .guest(x.getGuest().getFirstName()+ " "+x.getGuest().getLastName())
                    .departure(x.getSchedule().getDepartureAiport())
                    .arrival(x.getSchedule().getArrivalAirport())
                    .pesawat(x.getSchedule().getPesawat().getName())
                    .seat(x.getSeat().getSeatName())
                    .date(x.getSchedule().getDate())
                    .link("https://febe6.up.railway.app/api/generateTicket/"+ x.getId()).build()));
            OrderResponse sample = OrderResponse.builder()
                    .email(order.getUserEmail())
                    .orderDate(order.getCreateAt())
                    .orderDetail(ticket)
                    .totalPrice(order.getTotalPrice())
                    .tax(order.getTax())
                    .totalPay(order.getTotalPay())
                    .build();
            response.addHeader("Content-Disposition", String.format("attachment; filename=\"invoiceOrder"+order.getId()+".pdf\""));
            OutputStream output = response.getOutputStream();
            invoiceService.generateOrder(sample, output);
            response.flushBuffer();
            data.setSuccess(true);
            data.setStatusCode(HttpStatus.ACCEPTED.value());
            data.setMessage("Successfully");
            data.setData(sample);
            return ResponseEntity.accepted().body(data);
        }catch (Exception e){
            data.setSuccess(false);
            data.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            data.setMessage(e.getMessage());
            data.setData(null);
            return ResponseEntity.internalServerError().body(data);
        }
    }
}
