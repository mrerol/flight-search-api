package com.fsa.manager;

import com.fsa.dto.Flight;
import com.fsa.repository.FlightRepository;
import com.fsa.types.FlightSearchRequest;
import com.fsa.utils.DateUtils;
import com.fsa.utils.ModelUtils;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FlightManager {

  private static final Logger logger = LoggerFactory.getLogger(FlightManager.class);
  private final FlightRepository flightRepository;

  public FlightManager(FlightRepository flightRepository) {
    this.flightRepository = flightRepository;
  }

  @Transactional
  public void saveFlight() {
    logger.info("Saving flight");
  }

  @Transactional
  public Flight saveFlight(Flight flight) {
    logger.info("Saving flight");
    com.fsa.model.Flight flightEntity = ModelUtils.convert(flight, com.fsa.model.Flight.class);
    return ModelUtils.convert(flightRepository.save(flightEntity), Flight.class);
  }

  @Transactional
  public List<Flight> saveFlights(List<Flight> flightList) {
    flightList.stream()
        .map(flight -> ModelUtils.convert(flight, com.fsa.model.Flight.class))
        .forEach(flightRepository::save);
    return flightList;
  }

  public Optional<Flight> getFlightById(Long id) {
    Optional<com.fsa.model.Flight> optFlight = this.flightRepository.findById(id);
    return optFlight.map(flight -> ModelUtils.convert(flight, Flight.class));
  }

  public List<Flight> getAllFlights() {
    List<com.fsa.model.Flight> flightList = this.flightRepository.findAll();
    return flightList.stream().map(flight -> ModelUtils.convert(flight, Flight.class)).toList();
  }

  @Transactional
  public void deleteFlightById(Long id) {
    this.flightRepository.deleteById(id);
  }

  public List<Flight> findFlights(
      FlightSearchRequest outboundFlightSearchRQ, FlightSearchRequest inboundFlightSearchRQ) {
    String outBoundDepartureAirport = outboundFlightSearchRQ.getDepartureAirport();
    String outBoundArrivalAirport = outboundFlightSearchRQ.getArrivalAirport();
    LocalDateTime outBoundDepartureDateTime =
        DateUtils.convertDateToLocalDateTime(outboundFlightSearchRQ.getDepartureDateTime());
    if (inboundFlightSearchRQ != null) {
      return this.flightRepository
          .findFlights(
              outBoundDepartureAirport,
              outBoundArrivalAirport,
              outBoundDepartureDateTime.toLocalDate(),
              DateUtils.convertDateToLocalDateTime(inboundFlightSearchRQ.getDepartureDateTime())
                  .toLocalDate())
          .stream()
          .map(flight -> ModelUtils.convert(flight, Flight.class))
          .toList();
    }
    return this.flightRepository
        .findFlights(
            outBoundDepartureAirport,
            outBoundArrivalAirport,
            outBoundDepartureDateTime.toLocalDate())
        .stream()
        .map(flight -> ModelUtils.convert(flight, Flight.class))
        .toList();
  }
}
