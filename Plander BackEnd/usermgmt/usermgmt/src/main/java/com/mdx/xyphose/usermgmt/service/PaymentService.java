package com.mdx.xyphose.usermgmt.service;

import com.mdx.xyphose.usermgmt.entity.Payment;
import com.mdx.xyphose.usermgmt.entity.dto.PaymentDTO;
import com.mdx.xyphose.usermgmt.repo.PaymentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepo paymentRepository;

    @Autowired
    private RestTemplate restTemplate;

    public String processPayment(PaymentDTO paymentDTO) {
        Payment payment = new Payment();
        payment.setVenueId(paymentDTO.getVenueId());
        payment.setDate(paymentDTO.getDate());
        payment.setTime(paymentDTO.getTime());
        payment.setSlot(paymentDTO.getSlot());
        payment.setUserEmail(paymentDTO.getUserEmail());
        payment.setAmount(paymentDTO.getAmount());
        payment.setPaymentMethod(paymentDTO.getPaymentMethod());
        payment.setPaymentStatus("SUCCESS"); // Default status
        paymentRepository.save(payment);

        // Decrement capacity in the venue service
        decrementVenueCapacity(paymentDTO.getVenueId(), paymentDTO.getSlot());

        return "Payment successful! Payment ID: " + payment.getId();
    }

    private void decrementVenueCapacity(String venueId, int seatsBooked) {
        String url = "http://localhost:8085/api/venues/" + venueId + "/decrement?seatsBooked=" + seatsBooked;
        restTemplate.postForObject(url, null, Void.class);
    }

    private void incrementVenueCapacity(String venueId, int seatsBooked) {
        String url = "http://localhost:8085/api/venues/" + venueId + "/increment?seatsBooked=" + seatsBooked;
        restTemplate.postForObject(url, null, Void.class);
    }

    public List<Payment> getBookingsByUserEmail(String userEmail) {
        return paymentRepository.findByUserEmail(userEmail);
    }

    public void cancelBooking(Long bookingId) {
        Optional<Payment> paymentOptional = paymentRepository.findById(bookingId);
        if (paymentOptional.isPresent()) {
            Payment payment = paymentOptional.get();
            // Increment capacity in the venue service before deleting the booking
            incrementVenueCapacity(payment.getVenueId(), payment.getSlot());
            paymentRepository.deleteById(bookingId);
        } else {
            throw new RuntimeException("Booking not found with id: " + bookingId);
        }
    }
}