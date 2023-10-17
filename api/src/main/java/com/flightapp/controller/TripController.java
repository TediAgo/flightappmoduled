package com.flightapp.controller;

import com.flightapp.dto.TripDTO;
import com.flightapp.security.token.TokenUtility;
import com.flightapp.service.TripService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trip")
@AllArgsConstructor
public class TripController {

    private final TripService tripService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<TripDTO> getTrip(@NonNull @PathVariable(value = "id") Integer id) {
        return ResponseEntity.ok(tripService.getTrip(TokenUtility.getUsernameFromToken(), id));
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<TripDTO>> getAllTrips() {
        return ResponseEntity.ok(tripService.getAllTrips());
    }

    @GetMapping("/allByReason/{reason}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<TripDTO>> getAllTripsByReason(@NonNull @PathVariable(value = "reason") String reason) {
        return ResponseEntity.ok(tripService.getAllTripsByReason(reason));
    }

    @GetMapping("/allByStatus/{status}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<TripDTO>> getAllTripsByStatus(@NonNull @PathVariable(value = "status") String status) {
        return ResponseEntity.ok(tripService.getAllTripsByStatus(status));
    }

    @GetMapping("/allByReason/{reason}/andStatus/{status}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<TripDTO>> getAllTripsByReasonAndStatus(@NonNull @PathVariable(value = "reason") String reason,
                                                                      @NonNull @PathVariable(value = "status") String status) {
        return ResponseEntity.ok(tripService.getAllTripsByReasonAndStatus(reason, status));
    }

    @GetMapping("/allMyTrips")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<List<TripDTO>> getAllMyTrips() {
        return ResponseEntity.ok(tripService.getAllMyTrips(TokenUtility.getUsernameFromToken()));
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<TripDTO> createTrip(@NonNull @RequestBody TripDTO tripDTO) {
        return ResponseEntity.ok(tripService.createTrip(TokenUtility.getUsernameFromToken(), tripDTO));
    }

    @PutMapping("/{tripId}/approvalRequest")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<TripDTO> approvalRequest(@NonNull @PathVariable(value = "tripId") Integer tripId) {
        return ResponseEntity.ok(tripService.approvalRequest(TokenUtility.getUsernameFromToken(), tripId));
    }

    @PutMapping("/{tripId}/approveTrip")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<TripDTO> approveTrip(@NonNull @PathVariable(value = "tripId") Integer tripId) {
        return ResponseEntity.ok(tripService.approveTrip(tripId));
    }

    @PutMapping("/{tripId}/addFlight/{flightId}")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<TripDTO> addFlight(@NonNull @PathVariable(value = "tripId") Integer tripId,
                                             @NonNull @PathVariable(value = "flightId") Integer flightId) {
        return ResponseEntity.ok(tripService.addFlight(TokenUtility.getUsernameFromToken(), tripId, flightId));
    }

    @DeleteMapping("/{id}/delete")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<Integer> deleteTrip(@NonNull @PathVariable(value = "id") Integer id) {
        return ResponseEntity.ok(tripService.deleteTrip(id));
    }

    @PutMapping("/{id}/restore")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<TripDTO> restoreTrip(@NonNull @PathVariable(value = "id") Integer id) {
        return ResponseEntity.ok(tripService.restoreTrip(id));
    }
}