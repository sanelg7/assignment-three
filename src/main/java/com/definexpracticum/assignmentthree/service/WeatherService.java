package com.definexpracticum.assignmentthree.service;

import com.definexpracticum.assignmentthree.model.CurrentWeather;
import com.definexpracticum.assignmentthree.model.DailyWeather;
import com.definexpracticum.assignmentthree.model.HourlyWeather;
import com.definexpracticum.assignmentthree.service.util.URIBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@Service
public class WeatherService {

    private final RestTemplate template;
    private static URIBuilder uriBuilder = new URIBuilder();

    @Value("${api.key}")
    private String apiKey;

    @Value("${base.url}")
    private String baseUrl;

    @Autowired
    public WeatherService(RestTemplate restTemplate) {
        this.template = restTemplate;

    }

    // Gets the current weather data to show on homepage using RestTemplate. It can not be called by the user.
    public CurrentWeather getCurrentWeather(){

        String url = uriBuilder
                .setLocation("istanbul")
                .setTimeInterval("current")
                .setRange("today")
                .setBaseURL(baseUrl)
                .setApiKey(apiKey)
                .build();

        String weatherData = template.getForObject(url, String.class);
        ObjectMapper mapper = new ObjectMapper();
        CurrentWeather currentWeather;
        try {
            currentWeather = mapper.readValue(weatherData, CurrentWeather.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return currentWeather;
    }


    public ModelAndView generateWeatherModel(String url){
        String weatherData = template.getForObject(url, String.class);
        ObjectMapper mapper = new ObjectMapper();
        ModelAndView model = new ModelAndView();
        if(weatherData.contains("hours")){
            HourlyWeather hourlyWeather;
            try {
                hourlyWeather = mapper.readValue(weatherData, HourlyWeather.class);
                model.setViewName("hourly");

                model.addObject("hourlyWeather", hourlyWeather);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            DailyWeather dailyWeather;
            try {
                dailyWeather = mapper.readValue(weatherData, DailyWeather.class);
                model.setViewName("daily");
                model.addObject(dailyWeather);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        return model;


    }
}
