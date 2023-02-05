package com.definexpracticum.assignmentthree.rest;

import com.definexpracticum.assignmentthree.model.CurrentWeather;
import com.definexpracticum.assignmentthree.model.OutgoingRequestForm;
import com.definexpracticum.assignmentthree.util.URIBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/")
public class CurrentWeatherController {

    private URIBuilder uriBuilder = new URIBuilder();
    @Value("${api.key}")
    private String apiKey;

    @Value("${base.url}")
    private String baseUrl;

    private final RestTemplate template;

    @Autowired
    public CurrentWeatherController(RestTemplate template) {
        this.template = template;
    }

    @GetMapping("/home")
    public String showHomePage(Model model, @ModelAttribute OutgoingRequestForm outgoingRequestForm){
        model.addAttribute("currentWeather", getCurrentWeather());
        model.addAttribute("outgoingRequestForm", outgoingRequestForm);
        return "home";

    }
    @GetMapping("/current-weather")
    public CurrentWeather getCurrentWeather(){

        String url = uriBuilder
                .setLocation("istanbul")
                .setTimeInterval("current")
                .setRange("today")
                .setBaseURL(baseUrl)
                .setApiKey(apiKey)
                .build();
        System.out.println(uriBuilder);


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

}
