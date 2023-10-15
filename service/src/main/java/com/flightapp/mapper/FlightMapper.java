package com.flightapp.mapper;


import com.flightapp.dto.FlightDTO;
import com.flightapp.entity.FlightEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FlightMapper {

    FlightMapper INSTANCE = Mappers.getMapper(FlightMapper.class);

    FlightDTO flightToDTO(FlightEntity flight);

    FlightEntity flightDTOToFlight(FlightDTO flightDTO);
}

