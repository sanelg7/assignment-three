package com.definexpracticum.assignmentthree.rest;

import com.definexpracticum.assignmentthree.model.CurrentWeather;
import com.definexpracticum.assignmentthree.model.DailyWeather;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/daily")
public class DailyWeatherController {

    @Value("${api.key}")
    private String apiKey;


    private final RestTemplate template;

    @Autowired
    public DailyWeatherController(RestTemplate template) {
        this.template = template;
    }

    @GetMapping("/this-week")
    public void getDailyWeatherForWeek(){
        String url = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/istanbul/next7days?unitGroup=metric&include=days&key=" +
                apiKey +
                "&contentType=json";

        String w = template.getForObject(url, String.class);
        ObjectMapper mapper = new ObjectMapper();
        DailyWeather dailyWeather;
        try {
            dailyWeather = mapper.readValue(w, DailyWeather.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(dailyWeather);
    }

    @GetMapping("/this-month")
    public void getDailyWeatherForMonth(){
        String url = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/istanbul/next30days?unitGroup=metric&include=days&key=" +
                apiKey +
                "&contentType=json";



        String w = template.getForObject(url, String.class);
        ObjectMapper mapper = new ObjectMapper();
        DailyWeather dailyWeather;
        try {
            dailyWeather = mapper.readValue(w, DailyWeather.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(dailyWeather);
    }

}
