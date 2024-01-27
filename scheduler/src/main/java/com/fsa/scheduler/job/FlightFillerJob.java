package com.fsa.scheduler.job;

import com.fsa.dto.Flight;
import com.fsa.manager.AirportManager;
import com.fsa.manager.FlightManager;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.IntStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class FlightFillerJob {

  private static final Logger logger = LoggerFactory.getLogger(FlightFillerJob.class);
  private final FlightManager flightManager;
  private final AirportManager airportManager;

  public FlightFillerJob(FlightManager flightManager, AirportManager airportManager) {
    this.flightManager = flightManager;
    this.airportManager = airportManager;
  }

  @Scheduled(fixedRate = 24 * 60 * 60 * 1000) // 24 hours
  public void execute() {
    try {
      logger.info("Flight Filler Job started");
      fillAvailability();
      logger.info("Flight Filler Job ended");
    } catch (Exception e) {
      logger.error("Error: " + FlightFillerJob.class, e);
    }
  }

  public void fillAvailability() {
    List<Flight> createFlights = createFlights();
    this.flightManager.saveFlights(createFlights);
  }

  private List<Flight> createFlights() {
    SecureRandom random = new SecureRandom();
    int flightSize = random.nextInt(2, 5);
    logger.info("Creating flights as size {}", flightSize);
    return IntStream.range(0, flightSize).mapToObj(i -> createFlight()).toList();
  }

  private Flight createFlight() {
    SecureRandom randomPrice = new SecureRandom();
    Flight flight = new Flight();
    flight.setDepartureAirport(this.airportManager.findRandomAirport());
    flight.setDepartureDateTime(createRandomDate());
    flight.setArrivalAirport(this.airportManager.findRandomAirport());
    if (randomPrice.nextBoolean()) {
      flight.setReturnDateTime(createRandomDate(flight.getDepartureDateTime()));
    }
    flight.setPrice(BigDecimal.valueOf(randomPrice.nextInt(15, 1000)));
    return flight;
  }

  private LocalDateTime createRandomDate() {
    try {
      LocalDateTime start = LocalDateTime.of(2024, Month.JANUARY, 1, 0, 0);
      long days = ChronoUnit.DAYS.between(start, LocalDateTime.now());
      int day = new SecureRandom().nextInt((int) days + 1);
      long hours = ChronoUnit.HOURS.between(start, LocalDateTime.now());
      int hour = new SecureRandom().nextInt((int) hours + 1);
      return start.plusDays(day).plusHours(hour);
    } catch (Exception e) {
      logger.error("Error: " + FlightFillerJob.class, e);
      return LocalDateTime.now();
    }
  }

  private LocalDateTime createRandomDate(LocalDateTime start) {
    try {
      int day = new SecureRandom().nextInt(30);
      int hour = new SecureRandom().nextInt(24);
      return start.withSecond(0).plusDays(day).plusHours(hour);
    } catch (Exception e) {
      logger.error("Error: " + FlightFillerJob.class, e);
      return LocalDateTime.now();
    }
  }
}
