package com.flightapp.service.impl;

import com.flightapp.repository.TripRepository;
import com.flightapp.service.TripService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TripServiceImplementation implements TripService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TripServiceImplementation.class);

    @Autowired
    private TripRepository tripRepository;
}
