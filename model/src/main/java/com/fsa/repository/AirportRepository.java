package com.fsa.repository;

import com.fsa.model.Airport;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AirportRepository extends JpaRepository<Airport, Long> {

  @Query(value = "SELECT * FROM Airport ORDER BY RAND() LIMIT 1", nativeQuery = true)
  Airport findRandomAirport();

  // crud operations
  Airport save(Airport airport);

  Optional<Airport> findById(Long id);

  void deleteById(Long id);

  List<Airport> findAll();
}
