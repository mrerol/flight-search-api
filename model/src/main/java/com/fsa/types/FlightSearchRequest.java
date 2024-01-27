package com.fsa.types;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlightSearchRequest {

  @JsonProperty("departureDateTime")
  @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE_TIME)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private Date departureDateTime;

  @JsonProperty("departureAirport")
  private String departureAirport;

  @JsonProperty("arrivalAirport")
  private String arrivalAirport;
}
