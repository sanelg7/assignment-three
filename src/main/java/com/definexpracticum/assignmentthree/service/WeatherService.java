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

    // Builds URL for outgoing request to the external weather API.
    private static URIBuilder uriBuilder = new URIBuilder();

    // Api key stored in configuration file.
    @Value("${api.key}")
    private String apiKey;

    // Base url for outgoing request, stored in configuration file.
    @Value("${base.url}")
    private String baseUrl;

    // Autowired the rest template
    @Autowired
    public WeatherService(RestTemplate restTemplate) {
        this.template = restTemplate;

    }

    // Gets the current weather data to show on homepage using RestTemplate.
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


    // Generates a ModelAndView object that is passed to the view in the controller.
    // It can map hourly and daily representation according to the incoming weather data, using the prebuilt request url and RestTemplate.
    public ModelAndView generateWeatherModel(String url){

        // URL is checked within the controller method.

        String weatherData = template.getForObject(url, String.class);
        ObjectMapper mapper = new ObjectMapper();
        ModelAndView model = new ModelAndView();

        // Hourly and daily data are similar. Hourly data contains a json array "hours".
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
