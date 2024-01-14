package com.codfish.bikeSalesAndService.api.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FragmentsController {

    @GetMapping("/home/home")
    public  String getFragmentsHome(){
        return "home/home";
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
    public  String getFragmentsHistory(){
        return "info/bike_history";
    }

    @GetMapping("/info/add_update_salesman")
    public  String getFragmentsManagementUserSalesman (){
        return "info/add_update_salesman";
    }

    @GetMapping("/info/add_update_person_repairing")
    public  String getFragmentsManagementUserPersonRepairing(){
        return "info/add_update_person_repairing";
    }

    @GetMapping("/info/customers-purchases")
    public  String getFragmentsCustomers(){
        return "info/customers_purchases";
    }
    @GetMapping("/info/invoice-purchases")
    public  String getFragmentsInvoices(){
        return "info/invoice_purchases";
    }
}
