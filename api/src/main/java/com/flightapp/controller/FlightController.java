package com.flightapp.controller;

import com.flightapp.dto.FlightDTO;
import com.flightapp.service.FlightService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flights")
@AllArgsConstructor
public class FlightController {

    private final FlightService flightService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<FlightDTO> getFlight(@NonNull @PathVariable(value = "id") Integer id) {
        return ResponseEntity.ok(flightService.getFlight(id));
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<FlightDTO>> getAllFlights() {
        return ResponseEntity.ok(flightService.getAllFlights());
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<FlightDTO> createFlight(@Valid @NonNull @RequestBody FlightDTO flightDTO) {
        return ResponseEntity.ok(flightService.createFlight(flightDTO));
    }

    @DeleteMapping("/{id}/delete")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Integer> deleteFlight(@NonNull @PathVariable(value = "id") Integer id) {
        return ResponseEntity.ok(flightService.deleteFlight(id));
    }

    @PutMapping("/{id}/restore")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<FlightDTO> restoreFlight(@NonNull @PathVariable(value = "id") Integer id) {
        return ResponseEntity.ok(flightService.restoreFlight(id));
    }
}
