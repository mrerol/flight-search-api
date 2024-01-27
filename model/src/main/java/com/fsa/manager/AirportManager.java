package com.fsa.manager;

import com.fsa.dto.Airport;
import com.fsa.repository.AirportRepository;
import com.fsa.utils.ModelUtils;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AirportManager {

  private static final Logger logger = LoggerFactory.getLogger(AirportManager.class);
  private final AirportRepository airportRepository;

  public AirportManager(AirportRepository airportRepository) {
    this.airportRepository = airportRepository;
  }

  public Airport findRandomAirport() {
    com.fsa.model.Airport airportEntity = airportRepository.findRandomAirport();
    return ModelUtils.convert(airportEntity, Airport.class);
  }

  @Transactional
  public Airport saveAirport(Airport airport) {
    com.fsa.model.Airport airportEntity = ModelUtils.convert(airport, com.fsa.model.Airport.class);
    return ModelUtils.convert(airportRepository.save(airportEntity), Airport.class);
  }

  public Optional<Airport> getAirportById(Long id) {
    Optional<com.fsa.model.Airport> airportList = this.airportRepository.findById(id);
    return airportList.map(airport -> ModelUtils.convert(airport, Airport.class));
  }

  public List<Airport> getAllAirports() {
    List<com.fsa.model.Airport> airportList = this.airportRepository.findAll();
    return airportList.stream().map(airport -> ModelUtils.convert(airport, Airport.class)).toList();
  }

  @Transactional
  public void deleteAirportById(Long id) {
    this.airportRepository.deleteById(id);
  }
}
