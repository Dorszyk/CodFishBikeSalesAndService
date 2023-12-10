package com.codfish.bikeSalesAndService.api.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FragmentsController {

    @GetMapping("/home/home")
    public  String getFragmentsHome(){
        return "home";
    }
    @GetMapping("/info/salesman_portal")
    public  String getFragmentsSalesman(){
        return "info/salesman_portal";
    }
    @GetMapping("/info/bike_purchase")
    public  String getFragmentsBuyABike(){
        return "info/bike_purchase";
    }
    @GetMapping("/info/bike_service_request")
    public  String getFragmentsServiceABike(){
        return "info/bike_service_request";
    }
    @GetMapping("/info/bike_history")
    public  String getFragmentsBikeHistory(){
        return "info/bike_history";
    }
}
