package com.fsa.rest.controllers.interfaces;

import com.fsa.rest.controllers.SwaggerConstants;
import com.fsa.types.FlightSearchRequest;
import com.fsa.types.FlightSearchResponse;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@OpenAPIDefinition(info = @Info(title = "Flight Search API", version = "v1"))
@SecurityScheme(name = "basicAuth", type = SecuritySchemeType.HTTP, scheme = "basic")
@Tag(name = "The Flight Search API", description = "The Flight Search API")
@RestController
@RequestMapping(
    consumes = {MediaType.APPLICATION_JSON_VALUE},
    produces = {MediaType.APPLICATION_JSON_VALUE})
public interface FlightSearchControllerInterface {

  @Operation(
      method = "Flight Search",
      requestBody =
          @io.swagger.v3.oas.annotations.parameters.RequestBody(
              content = {
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = FlightSearchRequest.class),
                    examples = {
                      @ExampleObject(
                          name = "FlightSearch-RQ-OW",
                          value = SwaggerConstants.FLIGHT_SEARCH_RQ_OW),
                      @ExampleObject(
                          name = "FlightSearch-RQ-RT",
                          value = SwaggerConstants.FLIGHT_SEARCH_RQ_OW)
                    })
              }),
      security = {@SecurityRequirement(name = "basicAuth")},
      parameters = {
        @Parameter(
            in = ParameterIn.HEADER,
            name = "Accept-Encoding",
            description = "Gzip is a file format used for file compression and decompression.",
            required = true,
            example = "gzip",
            schema = @Schema(type = "string"))
      },
      description = SwaggerConstants.FLIGHT_SEARCH_DESC)
  @ApiResponse(
      responseCode = "200",
      description = "The request has succeeded.",
      content = {
        @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = FlightSearchResponse.class))
      })
  @ApiResponse(
      responseCode = "500",
      description = "Internal server error.",
      content = {@Content})
  @ApiResponse(
      responseCode = "400",
      description = "Bad request. Invalid extension",
      content = {@Content})
  @ApiResponse(
      responseCode = "404",
      description = "Domain not found",
      content = {@Content})
  ResponseEntity<FlightSearchResponse> flightSearch(
      @RequestBody List<FlightSearchRequest> availabilityRQList);
}
