package com.definexpracticum.assignmentthree.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public record HourlyWeather(
        String resolvedAddress,
        @JsonProperty("tzoffset") int timeZone,

        List<Day> days,

        List<Hour> hours


) {
    public HourlyWeather{
        hours = days.stream()
                .flatMap(day -> day.hours().stream())
                .collect(Collectors.toList());
    }

}
