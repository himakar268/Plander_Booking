package com.mdx.xyphose.usermgmt.api;

import com.mdx.xyphose.usermgmt.entity.Payment;
import com.mdx.xyphose.usermgmt.entity.dto.PaymentDTO;
import com.mdx.xyphose.usermgmt.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> processPayment(@RequestBody PaymentDTO paymentDTO) {
        String responseMessage = paymentService.processPayment(paymentDTO);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", responseMessage);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userEmail}")
    public ResponseEntity<List<Payment>> getBookingsByUserEmail(@PathVariable String userEmail) {
        List<Payment> bookings = paymentService.getBookingsByUserEmail(userEmail);
        return ResponseEntity.ok(bookings);
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<String> cancelBooking(@PathVariable Long bookingId) {
        paymentService.cancelBooking(bookingId);
        return ResponseEntity.ok("Booking cancelled successfully!");
    }
}