package com.fsa.rest.services;

import com.fsa.dto.Flight;
import com.fsa.manager.FlightManager;
import com.fsa.types.FlightSearchRequest;
import com.fsa.types.FlightSearchResponse;
import java.util.Comparator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FlightSearchService {

  private static final Logger logger = LoggerFactory.getLogger(FlightSearchService.class);
  private final FlightManager flightManager;

  public FlightSearchService(FlightManager flightManager) {
    this.flightManager = flightManager;
  }

  public FlightSearchResponse callFlightSearch(List<FlightSearchRequest> flightSearchRQList) {
    if (flightSearchRQList.isEmpty()) {
      String errorMessage = "Provide at least one direction!";
      logger.error(errorMessage);
      throw new IllegalArgumentException(errorMessage);
    } else if (flightSearchRQList.size() > 2) {
      String errorMessage = "Provide at most two directions!";
      logger.error(errorMessage);
      throw new IllegalArgumentException(errorMessage);
    }
    List<FlightSearchRequest> sortedFlightSearchRQList =
        flightSearchRQList.stream()
            .sorted(Comparator.comparing(FlightSearchRequest::getDepartureDateTime))
            .toList();
    FlightSearchRequest outBoundFlightSearchRQ = sortedFlightSearchRQList.get(0);
    FlightSearchRequest inBoundFlightSearchRQ = null;
    if (sortedFlightSearchRQList.size() == 2) {
      inBoundFlightSearchRQ = sortedFlightSearchRQList.get(1);
      validateFlightRequest(outBoundFlightSearchRQ, inBoundFlightSearchRQ);
    }
    List<Flight> flightList =
        this.flightManager.findFlights(outBoundFlightSearchRQ, inBoundFlightSearchRQ);
    FlightSearchResponse flightSearchRS = new FlightSearchResponse();
    flightSearchRS.setFlightLis(flightList);
    return flightSearchRS;
  }

  private void validateFlightRequest(
      FlightSearchRequest outBoundFlightSearchRQ, FlightSearchRequest inBoundFlightSearchRQ) {
    if (!outBoundFlightSearchRQ
            .getArrivalAirport()
            .equals(inBoundFlightSearchRQ.getDepartureAirport())
        || !outBoundFlightSearchRQ
            .getDepartureAirport()
            .equals(inBoundFlightSearchRQ.getArrivalAirport())) {
      String errorMessage = "Departure and arrival airports must be same for round trip!";
      logger.error(errorMessage);
      throw new IllegalArgumentException(errorMessage);
    }
  }
}
