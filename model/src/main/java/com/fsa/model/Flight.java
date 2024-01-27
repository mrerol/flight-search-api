package com.fsa.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "flight")
@Getter
@Setter
@NoArgsConstructor
public class Flight {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "departure_airport_id", nullable = false)
  private Airport departureAirport;

  @ManyToOne
  @JoinColumn(name = "arrival_airport_id", nullable = false)
  private Airport arrivalAirport;

  @Column(name = "departure_date_time", nullable = false)
  private LocalDateTime departureDateTime;

  @Column(name = "return_date_time")
  private LocalDateTime returnDateTime;

  @Column(name = "price", nullable = false)
  private BigDecimal price;
}
