package com.flightapp.entity;

import com.flightapp.enums.Airport;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "flight")
public class FlightEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "flight_number", unique = true)
    private Long flightNumber;
    @Column(name = "from_airport")
    @Enumerated(EnumType.STRING)
    private Airport fromAirport;
    @Column(name = "to_airport")
    @Enumerated(EnumType.STRING)
    private Airport toAirport;
    @Column(name = "departure_date")
    private LocalDateTime departureDate;
    @Column(name = "arrival_date")
    private LocalDateTime arrivalDate;
    @Column(name = "validity")
    private Boolean validity;
}