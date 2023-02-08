package com.definexpracticum.assignmentthree.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Day(
        @JsonProperty("datetimeEpoch") long epoch,

        String date,
        @JsonProperty("tempmax") double tempMax,
        @JsonProperty("tempmin") double tempMin,

        double temp,
        @JsonProperty("feelslike") double feelsLike,
        double dew,

        double humidity,

        @JsonProperty("precipprob") int precipitationProbability,
        @JsonProperty("preciptype") List<String> precipitationType,

        @JsonProperty("windspeed") double windSpeed,
        String conditions,

        String sunrise,

        String sunset,

        List<Hour> hours
) {

    public Day {

        // Compact constructor used to ensure some operations are injected into the actual constructor.
        // Converting epoch time received to date in yyyy/mm/dd format.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime localDate = LocalDateTime.ofEpochSecond(epoch, 0, ZoneOffset.UTC);
        date = localDate.format(formatter);

    }

}
