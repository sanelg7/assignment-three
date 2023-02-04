package com.definexpracticum.assignmentthree.rest;

import com.definexpracticum.assignmentthree.model.CurrentWeather;
import com.definexpracticum.assignmentthree.util.URIBuilder;
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
@RequestMapping("/")
public class CurrentWeatherController {



    private URIBuilder uriBuilder = new URIBuilder();


    @Value("${api.key}")
    private String apiKey;

    @Value("${base.url}")
    private String baseUrl;

    @GetMapping
    public void getCurrentWeather(){

        String url = uriBuilder
                .setLocation("istanbul")
                .setTimeInterval("current")
                .setBaseURL(baseUrl)
                .setApiKey(apiKey)
                .build();
        System.out.println(uriBuilder);

        //System.out.println(url);
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
