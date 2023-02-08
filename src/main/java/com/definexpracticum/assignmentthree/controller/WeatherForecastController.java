package com.definexpracticum.assignmentthree.controller;

import com.definexpracticum.assignmentthree.model.OutgoingRequestForm;
import com.definexpracticum.assignmentthree.service.WeatherService;
import com.definexpracticum.assignmentthree.service.util.URIBuilder;
import com.definexpracticum.assignmentthree.validation.CommaDelimitedLocationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/weather")
public class WeatherForecastController {

    // WeatherForecastController -> Controller to handle parameterized api calls from the user.

    // Builds URL for outgoing request to the external weather API.
    private URIBuilder uriBuilder = new URIBuilder();

    // Api key stored in configuration file.
    @Value("${api.key}")
    private String apiKey;


    // Base url for outgoing request, stored in configuration file.
    @Value("${base.url}")
    private String baseUrl;

    private final WeatherService weatherService;


    private final RestTemplate template;


    // Autowired the rest template and weather service to be used inside controller methods.
    @Autowired
    public WeatherForecastController(WeatherService getCurrentWeather, RestTemplate template) {
        this.weatherService = getCurrentWeather;
        this.template = template;
    }


    // Preprocessing web requests using custom validator.
    @InitBinder("outgoingRequestForm")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(new CommaDelimitedLocationValidator());
    }

    // Controller method that handles getting weather data for today.
    @PostMapping("/daily")
    public ModelAndView requestHourlyWeather(
            @Valid @ModelAttribute OutgoingRequestForm outgoingRequestForm,
            BindingResult bindingResult,
            ModelAndView modelAndView){

        // Checks if form data is valid.
        if(bindingResult.hasErrors()){
            modelAndView.addObject("locationValidationError", "Please enter a city name, or a comma seperated list (max 3 words) for a more specific location.");
            modelAndView.addObject(weatherService.getCurrentWeather());
            modelAndView.setViewName("home");
            return modelAndView;
        }

        // Getting the request parameters from the form.
        String location = outgoingRequestForm.getLocation();
        String interval = outgoingRequestForm.getInterval();
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

        // Adding the weather data received to the model and view.
        modelAndView = weatherService.generateWeatherModel(url);
        return modelAndView;
    }

    // Controller method that handles getting weather data for the week or month.
    @PostMapping("/weather-forecast")
    public ModelAndView requestDailyWeather(
            @Valid @ModelAttribute OutgoingRequestForm outgoingRequestForm,
            BindingResult bindingResult,
            ModelAndView modelAndView){

        // Checks if form data is valid.
        if(bindingResult.hasErrors()){
            modelAndView.addObject("locationValidationError", "Please enter a city name, or a comma seperated list (max 3 words) for a more specific location.");
            modelAndView.addObject(weatherService.getCurrentWeather());
            modelAndView.setViewName("home");
            return modelAndView;
        }

        // Getting the request parameters from the form.
        String location = outgoingRequestForm.getLocation();
        String interval = outgoingRequestForm.getInterval();
        String url;
        uriBuilder
                .setBaseURL(baseUrl)
                .setApiKey(apiKey)
                .setLocation(location);
        // Checking if the request is for weekly or monthly weather forecast.
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

        // Adding the weather data received to the model and view.
        modelAndView = weatherService.generateWeatherModel(url);
        return modelAndView;
    }

}
