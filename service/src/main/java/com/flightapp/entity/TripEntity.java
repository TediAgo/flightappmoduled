package com.flightapp.entity;

import com.flightapp.enums.Country;
import com.flightapp.enums.TripReason;
import com.flightapp.enums.TripStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "trip")
public class TripEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "trip_reason")
    @Enumerated(EnumType.STRING)
    private TripReason tripReason;
    @Column(name = "description")
    private String description;
    @Column(name = "from_country")
    @Enumerated(EnumType.STRING)
    private Country fromCountry;
    @Column(name = "to_country")
    @Enumerated(EnumType.STRING)
    private Country toCountry;
    @Column(name = "departure_date")
    private LocalDateTime departureDate;
    @Column(name = "arrival_date")
    private LocalDateTime arrivalDate;
    @Column(name = "trip_status")
    @Enumerated(EnumType.STRING)
    private TripStatus tripStatus;
    @ManyToOne
    @JoinColumn(name = "flight_id")
    private FlightEntity flight;
    @Column(name = "validity")
    private Boolean validity;
}
