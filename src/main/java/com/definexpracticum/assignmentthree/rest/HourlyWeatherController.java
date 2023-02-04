package com.definexpracticum.assignmentthree.rest;

import com.definexpracticum.assignmentthree.model.HourlyWeather;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/hourly")
public class HourlyWeatherController {

    @Value("${api.key}")
    private String apiKey;

    private final RestTemplate template;

    @Autowired
    public HourlyWeatherController(RestTemplate template) {
        this.template = template;
    }

    @GetMapping("/today")
    public void getTodaysWeatherHourly(){
        String url = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/istanbul/today?unitGroup=metric&include=hours&key=" +
                apiKey +
                "&contentType=json";

        String w = template.getForObject(url, String.class);
        ObjectMapper mapper = new ObjectMapper();
        HourlyWeather hourlyWeather;
        try {
            hourlyWeather = mapper.readValue(w, HourlyWeather.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(hourlyWeather);
    }
}
