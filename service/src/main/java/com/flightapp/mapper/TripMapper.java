package com.flightapp.mapper;


import com.flightapp.dto.TripDTO;
import com.flightapp.entity.TripEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TripMapper {

    TripMapper INSTANCE = Mappers.getMapper(TripMapper.class);

    TripDTO tripToDTO(TripEntity trip);

    TripEntity tipDTOToTrip(TripDTO tripDTO);
}
