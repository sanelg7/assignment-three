package com.definexpracticum.assignmentthree.rest;

import com.definexpracticum.assignmentthree.model.CurrentConditions;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/test")
public class TestConnectionController {

    @Value("${api.key}")
    private String apiKey;


    @GetMapping
    public void getCurrentWeather(){
        System.out.println(apiKey);
        String url = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/istanbul?unitGroup=us&include=current&key=" +
                apiKey + "&contentType=json";
        RestTemplate template = new RestTemplate();


        CurrentConditions w = template.getForObject(url, CurrentConditions.class);
        System.out.println(w);
    }




}
