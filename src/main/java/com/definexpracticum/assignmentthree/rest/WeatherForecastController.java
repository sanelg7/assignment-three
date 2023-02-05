package com.definexpracticum.assignmentthree.rest;

import com.definexpracticum.assignmentthree.model.DailyWeather;
import com.definexpracticum.assignmentthree.model.HourlyWeather;
import com.definexpracticum.assignmentthree.model.OutgoingRequestForm;
import com.definexpracticum.assignmentthree.util.URIBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/weather")
public class WeatherForecastController {

    private URIBuilder uriBuilder = new URIBuilder();
    @Value("${api.key}")
    private String apiKey;

    @Value("${base.url}")
    private String baseUrl;


    private final RestTemplate template;

    @Autowired
    public WeatherForecastController(RestTemplate template) {
        this.template = template;
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

    @PostMapping("/daily")
    public ModelAndView requestHourlyWeather(
            @ModelAttribute OutgoingRequestForm outgoingHourlyRequest,
            ModelAndView modelAndView){

        String location = outgoingHourlyRequest.getLocation();
        String interval = outgoingHourlyRequest.getInterval();
        String url;
        uriBuilder
                .setBaseURL(baseUrl)
                .setApiKey(apiKey)
                .setLocation(location);
        if (interval.equals("Daily")) {
            uriBuilder
                    .setRange("today")
                    .setTimeInterval("hours");
            url = uriBuilder.build();
        }else {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }

        modelAndView = generateWeatherModel(url);

        return modelAndView;
    }

    @PostMapping("/weather-forecast")
    public ModelAndView requestDailyWeather(
            @ModelAttribute OutgoingRequestForm outgoingDailyRequest,
            ModelAndView modelAndView){

        String location = outgoingDailyRequest.getLocation();
        String interval = outgoingDailyRequest.getInterval();
        String url;
        uriBuilder
                .setBaseURL(baseUrl)
                .setApiKey(apiKey)
                .setLocation(location);
        if(interval.equals("Weekly")){
            uriBuilder
                    .setRange("next7days")
                    .setTimeInterval("days");
            url = uriBuilder.build();
        }else if (interval.equals("Monthly")){
            uriBuilder
                    .setRange("next30days")
                    .setTimeInterval("days");
            url = uriBuilder.build();
        }else {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }

        modelAndView = generateWeatherModel(url);
        return modelAndView;
    }

}
