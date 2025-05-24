package com.mdx.xyphose.usermgmt.api;

import com.mdx.xyphose.usermgmt.entity.dto.VenueDTO;
import com.mdx.xyphose.usermgmt.service.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/venues")
@CrossOrigin
public class VenueController {

    @Autowired
    private VenueService venueService;

    @GetMapping
    public List<VenueDTO> getAllVenues() {
        return venueService.getAllVenues();
    }

    @GetMapping("/game/{gameId}")
    public List<VenueDTO> getVenuesByGameId(@PathVariable Long gameId) {
        return venueService.getVenuesByGameId(gameId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VenueDTO> getVenueById(@PathVariable Long id) {
        VenueDTO venue = venueService.getVenueById(id);
        return ResponseEntity.ok(venue);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public VenueDTO addVenue(@RequestBody VenueDTO venueDTO) {
        return venueService.addVenue(venueDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VenueDTO> updateVenue(@PathVariable Long id, @RequestBody VenueDTO venueDTO) {
        VenueDTO updatedVenue = venueService.updateVenue(id, venueDTO);
        return ResponseEntity.ok(updatedVenue);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteVenue(@PathVariable Long id) {
        venueService.deleteVenue(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{venueId}/decrement")
    public ResponseEntity<?> decrementAvailableCapacity(
            @PathVariable Long venueId,
            @RequestParam int seatsBooked) {
        try {
            VenueDTO updatedVenue = venueService.decrementAvailableCapacity(venueId, seatsBooked);
            return ResponseEntity.ok(updatedVenue);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{venueId}/increment")
    public ResponseEntity<?> incrementAvailableCapacity(
            @PathVariable Long venueId,
            @RequestParam int seatsBooked) {
        try {
            VenueDTO updatedVenue = venueService.incrementAvailableCapacity(venueId, seatsBooked);
            return ResponseEntity.ok(updatedVenue);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}