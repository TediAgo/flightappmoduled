package com.flightapp.dto;

import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightDTO {

    private Integer id;
    private Long flightNumber;
    private String fromAirport;
    private String toAirport;
    @FutureOrPresent
    private LocalDateTime departureDate;
    @FutureOrPresent
    private LocalDateTime arrivalDate;
}