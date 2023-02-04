package com.definexpracticum.assignmentthree.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Hour(
        @JsonProperty("datetime") String hour,
        double temp,
        @JsonProperty("feelslike") double feelsLike,
        double humidity,

        double dew,

        @JsonProperty("precipprob") int precipitationProbability,
        @JsonProperty("preciptype") List<String> precipitationType,

        @JsonProperty("windspeed") double windSpeed,

        String conditions
) {
}
