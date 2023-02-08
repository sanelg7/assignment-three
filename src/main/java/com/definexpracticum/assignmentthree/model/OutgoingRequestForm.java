package com.definexpracticum.assignmentthree.model;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Getter
@Setter
public class OutgoingRequestForm {


    private String location;
    private String interval;

}
