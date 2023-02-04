package com.definexpracticum.assignmentthree.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Day(
        @JsonProperty("datetimeEpoch") long epoch,
        @JsonProperty("tempmax") double tempMax,
        @JsonProperty("tempmin") double tempMin,

        double temp,
        @JsonProperty("feelslike") double feelsLike,
        double dew,

        double humidity,

        @JsonProperty("precipprob") int precipitationProbability,
        @JsonProperty("preciptype") List<String> precipitationType,

        @JsonProperty("windspeed") double windSpeed,
        long sunriseEpoch,
        long sunsetEpoch,
        String conditions,

        List<Hour> hours



) {

}
