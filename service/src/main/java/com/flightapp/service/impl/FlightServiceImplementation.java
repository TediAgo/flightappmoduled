package com.flightapp.service.impl;

import com.flightapp.repository.FlightRepository;
import com.flightapp.service.FlightService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlightServiceImplementation implements FlightService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FlightServiceImplementation.class);

    @Autowired
    private FlightRepository flightRepository;

}
