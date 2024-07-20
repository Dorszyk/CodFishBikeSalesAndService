package com.codfish.bikeSalesAndService.api.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FragmentsController {

    private static final String INFO_PATH = "info";

    @GetMapping("/home/home")
    public String getFragmentsHome() {
        return "home/home";
    }

    @GetMapping("/" + INFO_PATH + "/salesman_portal")
    public String getFragmentsSalesman() {
        return INFO_PATH + "/salesman_portal";
    }

    @GetMapping("/" + INFO_PATH + "/bike_purchase")
    public String getFragmentsBuyABike() {
        return INFO_PATH + "/bike_purchase";
    }

    @GetMapping("/" + INFO_PATH + "/bike_service_request")
    public String getFragmentsServiceABike() {
        return INFO_PATH + "/bike_service_request";
    }

    @GetMapping("/" + INFO_PATH + "/bike_history")
    public String getFragmentsHistory() {
        return INFO_PATH + "/bike_history";
    }

    @GetMapping("/" + INFO_PATH + "/add_update_salesman")
    public String getFragmentsManagementUserSalesman() {
        return INFO_PATH + "/add_update_salesman";
    }

    @GetMapping("/" + INFO_PATH + "/add_update_person_repairing")
    public String getFragmentsManagementUserPersonRepairing() {
        return INFO_PATH + "/add_update_person_repairing";
    }

    @GetMapping("/" + INFO_PATH + "/customers_purchases")
    public String getFragmentsCustomers() {
        return INFO_PATH + "/customers_purchases";
    }

    @GetMapping("/" + INFO_PATH + "/invoice_purchases")
    public String getFragmentsInvoices() {
        return INFO_PATH + "/invoice_purchases";
    }

    @GetMapping("/" + INFO_PATH + "/user_info")
    public String getFragmentsUserInfo() {
        return INFO_PATH + "/user_info";
    }
}
