package com.definexpracticum.assignmentthree.rest;

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

    private URIBuilder uriBuilder = new URIBuilder();
    @Value("${api.key}")
    private String apiKey;

    @Value("${base.url}")
    private String baseUrl;

    private final WeatherService weatherService;


    private final RestTemplate template;

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

    @PostMapping("/daily")
    public ModelAndView requestHourlyWeather(
            @Valid @ModelAttribute OutgoingRequestForm outgoingRequestForm,
            BindingResult bindingResult,
            ModelAndView modelAndView){

        if(bindingResult.hasErrors()){
            modelAndView.addObject("locationValidationError", "Please enter a city name, or a comma seperated list (max 3 words) for a more specific location.");
            modelAndView.addObject(weatherService.getCurrentWeather());
            modelAndView.setViewName("home");
            return modelAndView;
        }

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

        modelAndView = weatherService.generateWeatherModel(url);

        return modelAndView;
    }

    @PostMapping("/weather-forecast")
    public ModelAndView requestDailyWeather(
            @Valid @ModelAttribute OutgoingRequestForm outgoingDailyRequest,
            BindingResult bindingResult,
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

        modelAndView = weatherService.generateWeatherModel(url);
        return modelAndView;
    }

}
