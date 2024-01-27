package com.fsa.repository;

import com.fsa.model.Flight;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FlightRepository extends JpaRepository<Flight, Long> {
  // crud operations
  Flight save(Flight flight);

  Optional<Flight> findById(Long id);

  void deleteById(Long id);

  List<Flight> findAll();

  @Query(
      "SELECT f FROM Flight f WHERE "
          + "f.departureAirport.city = :departureAirport AND "
          + "f.arrivalAirport.city = :arrivalAirport AND "
          + "DATE(f.departureDateTime) = :departureDateTime AND "
          + "f.returnDateTime IS NULL")
  List<Flight> findFlights(
      @Param("departureAirport") String departureAirport,
      @Param("arrivalAirport") String arrivalAirport,
      @Param("departureDateTime") LocalDate departureDateTime);

  @Query(
      "SELECT f FROM Flight f WHERE "
          + "f.departureAirport.city = :departureAirport AND "
          + "f.arrivalAirport.city = :arrivalAirport AND "
          + "DATE(f.departureDateTime) = :departureDateTime AND "
          + "DATE(f.returnDateTime) = :returnDateTime")
  List<Flight> findFlights(
      @Param("departureAirport") String departureAirport,
      @Param("arrivalAirport") String arrivalAirport,
      @Param("departureDateTime") LocalDate departureDateTime,
      @Param("returnDateTime") LocalDate returnDateTime);
}
