package com.definexpracticum.assignmentthree.rest;

import com.definexpracticum.assignmentthree.model.OutgoingRequestForm;
import com.definexpracticum.assignmentthree.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class CurrentWeatherController {


    private final WeatherService getCurrentWeatherService;
    private final RestTemplate template;

    @Autowired
    public CurrentWeatherController(WeatherService getCurrentWeather, RestTemplate template)
    {
        this.getCurrentWeatherService = getCurrentWeather;
        this.template = template;
    }

    // Homepage, shows current weather.
    @GetMapping("/home")
    public ModelAndView showHomePage(@ModelAttribute OutgoingRequestForm outgoingRequestForm){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("currentWeather", getCurrentWeatherService.getCurrentWeather());
        modelAndView.addObject("outgoingRequestForm", outgoingRequestForm);
        modelAndView.setViewName("home");
        return modelAndView;

    }



}
