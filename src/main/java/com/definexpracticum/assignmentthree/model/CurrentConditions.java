package com.definexpracticum.assignmentthree.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CurrentConditions(
        @JsonProperty("datetimeepoch") long epoch,
        double temp,
        @JsonProperty("feelslike") double feelsLike,
        double humidity,
        double dew,
        double snow,
        @JsonProperty("windspeed") double windSpeed,
        String conditions,
        long sunriseEpoch,
        long sunsetEpoch
){


}
