package com.mdx.xyphose.usermgmt.service;

import com.mdx.xyphose.usermgmt.entity.Game;
import com.mdx.xyphose.usermgmt.entity.Venue;
import com.mdx.xyphose.usermgmt.entity.dto.VenueDTO;
import com.mdx.xyphose.usermgmt.repo.GameRepositry;
import com.mdx.xyphose.usermgmt.repo.VenueRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VenueService {
    @Autowired
    private VenueRepositry venueRepository;

    @Autowired
    private GameRepositry gameRepository;

    // Runs every hour at minute 0 (e.g., 1:00, 2:00, etc.)
    @Scheduled(cron = "0 0 * * * *")
    public void resetAllVenuesCapacity() {
        List<Venue> venues = venueRepository.findAll();
        for (Venue venue : venues) {
            venue.setAvailableCapacity(venue.getMaxCapacity());
            venueRepository.save(venue);
        }
        System.out.println("All venue capacities reset to max capacity at: " + new Date());
    }

    public List<VenueDTO> getAllVenues() {
        return venueRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<VenueDTO> getVenuesByGameId(Long gameId) {
        return venueRepository.findByGameId(gameId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public VenueDTO getVenueById(Long id) {
        Venue venue = venueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venue not found with id: " + id));
        return convertToDTO(venue);
    }

    public VenueDTO addVenue(VenueDTO venueDTO) {
        Game game = gameRepository.findById(venueDTO.getGameId())
                .orElseThrow(() -> new RuntimeException("Game not found with id: " + venueDTO.getGameId()));

        Venue venue = new Venue();
        venue.setName(venueDTO.getName());
        venue.setImage(venueDTO.getImage());
        venue.setMaxCapacity(venueDTO.getMaxCapacity());
        venue.setAvailableCapacity(venueDTO.getAvailableCapacity());
        venue.setRatePerHour(venueDTO.getRatePerHour());
        venue.setGame(game);

        Venue savedVenue = venueRepository.save(venue);
        return convertToDTO(savedVenue);
    }

    public VenueDTO updateVenue(Long id, VenueDTO venueDTO) {
        Venue venue = venueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venue not found with id: " + id));

        Game game = gameRepository.findById(venueDTO.getGameId())
                .orElseThrow(() -> new RuntimeException("Game not found with id: " + venueDTO.getGameId()));

        venue.setName(venueDTO.getName());
        venue.setImage(venueDTO.getImage());
        venue.setMaxCapacity(venueDTO.getMaxCapacity());
        venue.setAvailableCapacity(venueDTO.getAvailableCapacity());
        venue.setRatePerHour(venueDTO.getRatePerHour());
        venue.setGame(game);

        Venue updatedVenue = venueRepository.save(venue);
        return convertToDTO(updatedVenue);
    }

    public void deleteVenue(Long id) {
        Venue venue = venueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venue not found with id: " + id));
        venueRepository.delete(venue);
    }

    public VenueDTO decrementAvailableCapacity(Long venueId, int seatsBooked) {
        Optional<Venue> optionalVenue = venueRepository.findById(venueId);
        if (optionalVenue.isPresent()) {
            Venue venue = optionalVenue.get();
            int availableCapacity = venue.getAvailableCapacity();
            if (availableCapacity >= seatsBooked) {
                venue.setAvailableCapacity(availableCapacity - seatsBooked);
                Venue updatedVenue = venueRepository.save(venue);
                return convertToDTO(updatedVenue);
            } else {
                throw new RuntimeException("Not enough available capacity.");
            }
        } else {
            throw new RuntimeException("Venue not found.");
        }
    }

    public VenueDTO incrementAvailableCapacity(Long venueId, int seatsBooked) {
        Optional<Venue> optionalVenue = venueRepository.findById(venueId);
        if (optionalVenue.isPresent()) {
            Venue venue = optionalVenue.get();
            int availableCapacity = venue.getAvailableCapacity();
            int maxCapacity = venue.getMaxCapacity();
            if (availableCapacity + seatsBooked <= maxCapacity) {
                venue.setAvailableCapacity(availableCapacity + seatsBooked);
                Venue updatedVenue = venueRepository.save(venue);
                return convertToDTO(updatedVenue);
            } else {
                throw new RuntimeException("Exceeds max capacity.");
            }
        } else {
            throw new RuntimeException("Venue not found.");
        }
    }

    private VenueDTO convertToDTO(Venue venue) {
        VenueDTO dto = new VenueDTO();
        dto.setId(venue.getId());
        dto.setName(venue.getName());
        dto.setImage(venue.getImage());
        dto.setMaxCapacity(venue.getMaxCapacity());
        dto.setAvailableCapacity(venue.getAvailableCapacity());
        dto.setRatePerHour(venue.getRatePerHour());

        if (venue.getGame() != null) {
            dto.setGameId(venue.getGame().getId());
            dto.setGameName(venue.getGame().getName());
        }

        return dto;
    }
}