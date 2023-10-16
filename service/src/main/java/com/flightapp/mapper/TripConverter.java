package com.flightapp.mapper;

import com.flightapp.dto.TripDTO;
import com.flightapp.entity.TripEntity;

public class TripConverter {

    public static TripDTO convertTripEntityToDTO(TripEntity tripEntity) {
        TripDTO tripDTO = new TripDTO();

        tripDTO.setId(tripEntity.getId());
        tripDTO.setTripReason(String.valueOf(tripEntity.getTripReason()));
        tripDTO.setDescription(tripEntity.getDescription());
        tripDTO.setFromCountry(String.valueOf(tripEntity.getFromCountry()));
        tripDTO.setToCountry(String.valueOf(tripEntity.getToCountry()));
        tripDTO.setDepartureDate(tripEntity.getDepartureDate());
        tripDTO.setArrivalDate(tripEntity.getArrivalDate());
        tripDTO.setTripStatus(String.valueOf(tripEntity.getTripStatus()));

        tripDTO.setUserDTO(UserConverter.convertUserEntityToDTO(tripEntity.getUserEntity()));

        if(tripEntity.getFlightEntity() != null) {
            tripDTO.setFlightDTO(FlightConverter.convertFlightEntityToDTO(tripEntity.getFlightEntity()));
        }

        return tripDTO;
    }
}
