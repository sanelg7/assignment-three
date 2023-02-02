package com.definexpracticum.assignmentthree.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CurrentConditions(@JsonProperty("currentConditions") Object currentConditions) {
}
