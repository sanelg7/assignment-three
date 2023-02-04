package com.definexpracticum.assignmentthree.rest;

import com.definexpracticum.assignmentthree.model.CurrentWeather;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/")
public class CurrentWeatherController {

    @Value("${api.key}")
    private String apiKey;


    @GetMapping
    public void getCurrentWeather(){
        String url = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/istanbul?unitGroup=us&include=current&key=" +
                apiKey + "&contentType=json";
        RestTemplate template = new RestTemplate();


        String w = template.getForObject(url, String.class);
        ObjectMapper mapper = new ObjectMapper();
        CurrentWeather currentWeather;
        try {
            currentWeather = mapper.readValue(w, CurrentWeather.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(currentWeather);
    }

    String week = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/istanbul/next7days?unitGroup=us&include=days&key=4RF7P2JBX4AVBQA44KH747BML&contentType=json";
    String month = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/istanbul/next30days?unitGroup=us&include=days&key=4RF7P2JBX4AVBQA44KH747BML&contentType=json";

    @GetMapping("week")
    public void getWeekly(){
        System.out.println(apiKey);
        String url = week;
        RestTemplate template = new RestTemplate();


        String w = template.getForObject(url, String.class);
        ObjectMapper mapper = new ObjectMapper();
        CurrentWeather currentWeather;
        try {
            currentWeather = mapper.readValue(w, CurrentWeather.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(currentWeather);
    }

    @GetMapping("month")
    public void getMonthly(){
        System.out.println(apiKey);
        String url = month;
        RestTemplate template = new RestTemplate();


        String w = template.getForObject(url, String.class);
        ObjectMapper mapper = new ObjectMapper();
        CurrentWeather currentWeather;
        try {
            currentWeather = mapper.readValue(w, CurrentWeather.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(currentWeather);
    }

}
