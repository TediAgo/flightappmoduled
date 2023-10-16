package com.flightapp.service;

import com.flightapp.dto.TripDTO;

import java.util.List;

public interface TripService {
    TripDTO getTrip(String loggedEmail, Integer id);

    List<TripDTO> getAllTrips();

    List<TripDTO> getAllTripsByReason(String reason);

    List<TripDTO> getAllTripsByStatus(String status);

    List<TripDTO> getAllTripsByReasonAndStatus(String reason, String status);

    List<TripDTO> getAllMyTrips(String loggedEmail);

    TripDTO createTrip(String loggedEmail, TripDTO tripDTO);

    TripDTO approvalRequest(String loggedEmail, Integer tripId);

    TripDTO approveTrip(Integer tripId);

    TripDTO addFlight(String loggedEmail, Integer tripId, Integer flightId);

    Integer deleteTrip(Integer id);

    TripDTO restoreTrip(Integer id);
}
