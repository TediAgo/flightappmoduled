package com.flightapp.dto;

import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightDTO {

    private Integer id;
    @NonNull
    private Long flightNumber;
    @NonNull
    private String fromAirport;
    @NonNull
    private String toAirport;
    @NonNull
    @FutureOrPresent
    private LocalDateTime departureDate;
    @NonNull
    @FutureOrPresent
    private LocalDateTime arrivalDate;
}