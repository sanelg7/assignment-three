package com.definexpracticum.assignmentthree.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;

@Aspect
@Component
public class WeatherServiceExceptionHandler {


    ModelAndView modelAndView;

    @AfterThrowing(pointcut = "execution(* com.definexpracticum.assignmentthree.service.WeatherService.*(..))", throwing = "ex")
    public ModelAndView afterThrowingJsonProcessingExc(JoinPoint joinPoint, JsonProcessingException ex) {
        String message = "Received corrupted weather data. Redirecting in 5 seconds...";
        modelAndView = new ModelAndView();
        modelAndView.setViewName("error");
        modelAndView.addObject("errorMessage", message);
        return modelAndView;
    }



    @AfterThrowing(pointcut = "execution(* com.definexpracticum.assignmentthree.controller.*.*(..))", throwing = "ex")
    public ModelAndView afterThrowingHttpClientExc(JoinPoint joinPoint, HttpClientErrorException ex) {
        String message = "Requested weather data time interval is not valid. Redirecting in 5 seconds...";
        modelAndView = new ModelAndView();
        modelAndView.setViewName("error");
        modelAndView.addObject("errorMessage", message);
        return modelAndView;
    }

    @AfterThrowing(pointcut = "execution(* com.definexpracticum.assignmentthree.controller.*.*(..))", throwing = "ex")
    public ModelAndView afterThrowingHttpClientExc(JoinPoint joinPoint, Exception ex) {
        String message = "An unexpected error has occurred.";
        modelAndView = new ModelAndView();
        modelAndView.setViewName("error");
        modelAndView.addObject("errorMessage", message);
        System.out.println(modelAndView.getModelMap());
        return modelAndView;
    }



}
