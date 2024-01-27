package com.fsa.rest.controllers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SwaggerConstants {

  public static final String FLIGHT_SEARCH_RQ_OW =
      "[\n"
          + "    {\n"
          + "        \"departureDateTime\": \"2024-01-25\",\n"
          + "        \"departureAirport\": \"Toronto\",\n"
          + "        \"arrivalAirport\": \"Moscow\"\n"
          + "    }\n"
          + "]";

  public static final String FLIGHT_SEARCH_RQ_RT =
      "[\n"
          + "    {\n"
          + "        \"departureDateTime\": \"2024-01-29\",\n"
          + "        \"departureAirport\": \"Dubai\",\n"
          + "        \"arrivalAirport\": \"Sydney\"\n"
          + "    },\n"
          + "        {\n"
          + "        \"departureDateTime\": \"2024-01-31\",\n"
          + "        \"departureAirport\": \"Sydney\",\n"
          + "        \"arrivalAirport\": \"Dubai\"\n"
          + "    }\n"
          + "]";

  public static final String FLIGHT_SEARCH_DESC = "The flight search API";
}
