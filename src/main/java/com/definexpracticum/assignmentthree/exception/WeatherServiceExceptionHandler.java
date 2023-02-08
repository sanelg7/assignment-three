package com.definexpracticum.assignmentthree.exception;

import com.definexpracticum.assignmentthree.service.WeatherService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;

@Aspect
public class CustomControllerExceptionHandler {

    private WeatherService getCurrentWeatherService;

    @Autowired
    public CustomControllerExceptionHandler(WeatherService getCurrentWeatherService) {
        this.getCurrentWeatherService = getCurrentWeatherService;
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ModelAndView handleWeatherDateIntervalError(HttpServletRequest request, HttpClientErrorException e){

        if(!(e instanceof HttpClientErrorException)){
            return null;
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("weatherDateIntervalError", "Unexpected error. You will be redirected to home page in 5 seconds...");
        modelAndView.addObject("url", request.getRequestURL());
        modelAndView.setViewName("error");
        return modelAndView;
    }
}
