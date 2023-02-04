package com.definexpracticum.assignmentthree.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DailyWeather(
        String resolvedAddress,
        @JsonProperty("tzoffset") int timeZone,

        List<Day> days


) {

}
