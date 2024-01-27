package com.fsa.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Flight {

  private Airport departureAirport;
  private Airport arrivalAirport;
  private LocalDateTime departureDateTime;
  private LocalDateTime returnDateTime;
  private BigDecimal price;
}
