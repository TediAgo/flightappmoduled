package com.flightapp.service.impl;

import com.flightapp.dto.TripDTO;
import com.flightapp.entity.FlightEntity;
import com.flightapp.entity.TripEntity;
import com.flightapp.entity.UserEntity;
import com.flightapp.enums.Country;
import com.flightapp.enums.Role;
import com.flightapp.enums.TripReason;
import com.flightapp.enums.TripStatus;
import com.flightapp.exception.NotFoundException;
import com.flightapp.exception.ServiceException;
import com.flightapp.mapper.TripConverter;
import com.flightapp.repository.FlightRepository;
import com.flightapp.repository.TripRepository;
import com.flightapp.repository.UserRepository;
import com.flightapp.service.TripService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TripServiceImplementation implements TripService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TripServiceImplementation.class);

    @Autowired
    private final TripRepository tripRepository;
    @Autowired
    private final FlightRepository flightRepository;
    @Autowired
    private final UserRepository userRepository;


    @Override
    public TripDTO getTrip(String loggedEmail, Integer id) {
        if (tripRepository.findById(id).isEmpty() || tripRepository.findById(id).get().getValidity().equals(Boolean.FALSE)) {
            LOGGER.info("Trip not found.");
            throw new NotFoundException("Trip not found.");
        }
        if(userRepository.findByEmail(loggedEmail).get().getRole() == Role.ADMIN) {
            return TripConverter.convertTripEntityToDTO(tripRepository.findById(id).get());
        }
        TripEntity trip = tripRepository.findById(id)
                .filter(t -> t.getUserEntity().getEmail().equals(loggedEmail))
                .orElseThrow(() -> new ServiceException("Only user who create the trip can view."));
        return TripConverter.convertTripEntityToDTO(trip);
    }

    @Override
    public List<TripDTO> getAllTrips() {
        return tripRepository.findAll()
                .stream()
                .filter(trip -> trip.getValidity().equals(Boolean.TRUE))
                .map(TripConverter::convertTripEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TripDTO> getAllTripsByReason(String reason) {
        return tripRepository.findAll()
                .stream()
                .filter(trip -> trip.getValidity().equals(Boolean.TRUE))
                .filter(trip -> String.valueOf(trip.getTripReason()).equals(reason))
                .map(TripConverter::convertTripEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TripDTO> getAllTripsByStatus(String status) {
        return tripRepository.findAll()
                .stream()
                .filter(trip -> trip.getValidity().equals(Boolean.TRUE))
                .filter(trip -> String.valueOf(trip.getTripStatus()).equals(status))
                .map(TripConverter::convertTripEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TripDTO> getAllTripsByReasonAndStatus(String reason, String status) {
        return tripRepository.findAll()
                .stream()
                .filter(trip -> trip.getValidity().equals(Boolean.TRUE))
                .filter(trip -> String.valueOf(trip.getTripReason()).equals(reason))
                .filter(trip -> String.valueOf(trip.getTripStatus()).equals(status))
                .map(TripConverter::convertTripEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TripDTO> getAllMyTrips(String loggedEmail) {
        return tripRepository.findAll()
                .stream()
                .filter(trip -> (trip.getUserEntity().getEmail().equals(loggedEmail)) && trip.getValidity().equals(Boolean.TRUE))
                .map(TripConverter::convertTripEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TripDTO createTrip(String loggedEmail, TripDTO tripDTO) {
        if(tripDTO.getDepartureDate().isAfter(tripDTO.getArrivalDate())) {
            LOGGER.info("Trip Arrival Date should be after Departure Date.");
            throw new ServiceException("Trip Arrival Date should be after Departure Date.");
        }

        TripEntity trip = new TripEntity();
        trip.setTripReason(TripReason.valueOf(tripDTO.getTripReason()));
        trip.setDescription(tripDTO.getDescription());
        trip.setFromCountry(Country.valueOf(tripDTO.getFromCountry()));
        trip.setToCountry(Country.valueOf(tripDTO.getToCountry()));
        trip.setDepartureDate(tripDTO.getDepartureDate());
        trip.setArrivalDate(tripDTO.getArrivalDate());
        trip.setTripStatus(TripStatus.CREATED);
        trip.setValidity(Boolean.TRUE);

        trip.setUserEntity(userRepository.findByEmail(loggedEmail).get());

        tripRepository.save(trip);
        return TripConverter.convertTripEntityToDTO(trip);
    }

    @Override
    public TripDTO approvalRequest(String loggedEmail, Integer tripId) {
        /*if (tripRepository.findById(tripId).isEmpty() || tripRepository.findById(tripId).get().getValidity().equals(Boolean.FALSE)) {
            LOGGER.info("Trip not found.");
            throw new NotFoundException("Trip not found.");
        }
        if(!tripRepository.findById(tripId).get().getUserEntity().getEmail().equals(loggedEmail)) {
            LOGGER.info("Only user who created the trip can send approval request.");
            throw new ServiceException("Only user who created the trip can send approval request.");
        }
        if (tripRepository.findById(tripId).get().getTripStatus() != TripStatus.CREATED) {
            LOGGER.info("Trip should be in Created state.");
            throw new ServiceException("Trip should be in Created state.");
        }

        TripEntity trip = tripRepository.findById(tripId).get();
        trip.setTripStatus(TripStatus.WAITING_FOR_APPROVAL);
        tripRepository.save(trip);*/

        TripEntity trip = tripRepository.findById(tripId)
                .filter(ObjectUtils::isNotEmpty)
                .filter(t -> t.getValidity().equals(Boolean.TRUE))
                .filter(t -> t.getTripStatus() == TripStatus.CREATED)
                .orElseThrow(() -> {
                    LOGGER.info("Trip not found for tripId: {}", tripId);
                    return new NotFoundException("Trip not found.");
                });

        UserEntity loggedUser = userRepository.findByEmail(loggedEmail)
                .filter(userEntity -> userEntity.getId().equals(trip.getUserEntity().getId()))
                .orElseThrow(() -> {
                    LOGGER.info("Only user who created the trip can send the approval request.");
                    return new ServiceException("Only user who created the trip can send the approval request.");
                });

        trip.setTripStatus(TripStatus.WAITING_FOR_APPROVAL);
        tripRepository.save(trip);

        return TripConverter.convertTripEntityToDTO(trip);
    }

    @Override
    public TripDTO approveTrip(Integer tripId) {
        /*if (tripRepository.findById(tripId).isEmpty() || tripRepository.findById(tripId).get().getValidity().equals(Boolean.FALSE)) {
            LOGGER.info("Trip not found.");
            throw new NotFoundException("Trip not found.");
        }
        if (tripRepository.findById(tripId).get().getTripStatus() != TripStatus.WAITING_FOR_APPROVAL) {
            LOGGER.info("Trip should be in Waiting For Approval state.");
            throw new ServiceException("Trip should be in Waiting For Approval state.");
        }

        TripEntity trip = tripRepository.findById(tripId).get();
        trip.setTripStatus(TripStatus.APPROVED);
        tripRepository.save(trip);*/

        TripEntity trip = tripRepository.findById(tripId)
                .filter(ObjectUtils::isNotEmpty)
                .filter(tripEntity -> tripEntity.getValidity().equals(Boolean.TRUE))
                .filter(tripEntity -> tripEntity.getTripStatus() == TripStatus.WAITING_FOR_APPROVAL)
                .orElseThrow(() -> {
                    LOGGER.info("Trip not found for tripId: {}", tripId);
                    return new NotFoundException("Trip not found.");
                });

        trip.setTripStatus(TripStatus.APPROVED);
        tripRepository.save(trip);

        return TripConverter.convertTripEntityToDTO(trip);
    }

    @Override
    public TripDTO addFlight(String loggedEmail, Integer tripId, Integer flightId) {
        /*if (tripRepository.findById(tripId).isEmpty() || tripRepository.findById(tripId).get().getValidity().equals(Boolean.FALSE)) {
            LOGGER.info("Trip not found.");
            throw new NotFoundException("Trip not found.");
        }
        if(!tripRepository.findById(tripId).get().getUserEntity().getEmail().equals(loggedEmail)) {
            LOGGER.info("Only user who created the trip can add flight.");
            throw new ServiceException("Only user who created the trip can add flight.");
        }
        if (tripRepository.findById(tripId).get().getTripStatus() != TripStatus.APPROVED) {
            LOGGER.info("Trip should be in Approved state.");
            throw new ServiceException("Trip should be in Approved state.");
        }
        if (flightRepository.findById(flightId).isEmpty()
                || flightRepository.findById(flightId).get().getValidity().equals(Boolean.FALSE)) {
            LOGGER.info("Flight not found.");
            throw new NotFoundException("Flight not found.");
        }

        TripEntity trip = tripRepository.findById(tripId).get();
        trip.setFlightEntity(flightRepository.findById(flightId).get());
        tripRepository.save(trip);*/

        TripEntity trip = tripRepository.findById(tripId)
                .filter(ObjectUtils::isNotEmpty)
                .filter(tripEntity -> tripEntity.getValidity().equals(Boolean.TRUE))
                .filter(tripEntity -> tripEntity.getTripStatus() == TripStatus.APPROVED)
                .orElseThrow(() -> {
                    LOGGER.info("Trip not found for tripId: {}", tripId);
                    return new NotFoundException("Trip not found.");
                });

        UserEntity loggedUser = userRepository.findByEmail(loggedEmail)
                .filter(userEntity -> userEntity.getId().equals(trip.getUserEntity().getId()))
                .orElseThrow(() -> {
                    LOGGER.info("Only user who created the trip can add flight.");
                    return new ServiceException("Only user who created the trip can add flight.");
                });

        FlightEntity flight = flightRepository.findById(flightId)
                .filter(ObjectUtils::isNotEmpty)
                .filter(flightEntity -> flightEntity.getValidity().equals(Boolean.TRUE))
                .orElseThrow(() -> {
                    LOGGER.info("Flight not found for flightId: {}", flightId);
                    return new NotFoundException("Flight not found");
                });

        trip.setFlightEntity(flight);
        tripRepository.save(trip);

        return TripConverter.convertTripEntityToDTO(trip);
    }

    @Override
    public Integer deleteTrip(Integer id) {
        if (tripRepository.findById(id).isEmpty() || tripRepository.findById(id).get().getValidity().equals(Boolean.FALSE)) {
            LOGGER.info("Trip not found.");
            throw new NotFoundException("Trip not found.");
        }
        TripEntity trip = tripRepository.findById(id).get();
        trip.setValidity(Boolean.FALSE);
        tripRepository.save(trip);
        return id;
    }

    @Override
    public TripDTO restoreTrip(Integer id) {
        if (tripRepository.findById(id).isEmpty() || tripRepository.findById(id).get().getValidity().equals(Boolean.TRUE)) {
            LOGGER.info("Trip not found.");
            throw new NotFoundException("Trip not found.");
        }
        TripEntity trip = tripRepository.findById(id).get();
        trip.setValidity(Boolean.TRUE);
        tripRepository.save(trip);
        return TripConverter.convertTripEntityToDTO(trip);
    }
}
