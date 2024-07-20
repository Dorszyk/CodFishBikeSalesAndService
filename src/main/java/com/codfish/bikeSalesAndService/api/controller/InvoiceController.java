package com.codfish.bikeSalesAndService.api.controller;

import com.codfish.bikeSalesAndService.infrastructure.database.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class InvoiceController {

    private static final String INVOICE_PURCHASES_PATH = "/invoices_purchases";

    private final InvoiceRepository invoiceRepository;

    @GetMapping(value = INVOICE_PURCHASES_PATH)
    public ModelAndView getAllInvoices() {
        Map<String, ?> model = preparePurchaseInvoiceCustomerData();
        return new ModelAndView("info/invoice_purchases", model);
    }

    private Map<String, ?> preparePurchaseInvoiceCustomerData() {
        var availableInvoices = invoiceRepository.findAllInvoicesDTO();
        return Map.of("availableInvoicesDTOs", availableInvoices);
    }
}

