package com.definexpracticum.assignmentthree.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public record CurrentWeather(
        String resolvedAddress,
        @JsonProperty("tzoffset") int timeZone,
        CurrentConditions currentConditions
        ) {

}
