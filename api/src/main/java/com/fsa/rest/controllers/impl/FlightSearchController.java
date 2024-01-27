package com.fsa.rest.controllers.impl;

import com.fsa.rest.controllers.interfaces.FlightSearchControllerInterface;
import com.fsa.rest.services.FlightSearchService;
import com.fsa.types.FlightSearchRequest;
import com.fsa.types.FlightSearchResponse;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class FlightSearchController implements FlightSearchControllerInterface {

  private static final Logger logger = LoggerFactory.getLogger(FlightSearchController.class);

  private final FlightSearchService searchService;

  public FlightSearchController(FlightSearchService flightSearchService) {
    this.searchService = flightSearchService;
  }

  @PostMapping(value = "/flight-search")
  @Override
  public ResponseEntity<FlightSearchResponse> flightSearch(
      @RequestBody List<FlightSearchRequest> availabilityRQList) {
    try {
      return new ResponseEntity<>(
          searchService.callFlightSearch(availabilityRQList), HttpStatus.OK);
    } catch (Exception e) {
      logger.error("Error: " + FlightSearchController.class, e);
      FlightSearchResponse flightSearchRS = new FlightSearchResponse();
      flightSearchRS.setError(new Error(e));
      return new ResponseEntity<>(flightSearchRS, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
