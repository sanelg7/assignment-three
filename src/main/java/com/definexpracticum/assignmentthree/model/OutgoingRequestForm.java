package com.definexpracticum.assignmentthree.model;


import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OutgoingRequestForm {


    // OutGoingRequestForm -> This class is used to pass the request parameters from the model and back.
    private String location;
    private String interval;

}
