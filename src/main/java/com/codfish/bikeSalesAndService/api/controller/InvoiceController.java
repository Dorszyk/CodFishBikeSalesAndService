package com.codfish.bikeSalesAndService.api.controller;

import com.codfish.bikeSalesAndService.api.dto.mapper.InvoiceMapper;
import com.codfish.bikeSalesAndService.infrastructure.database.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class InvoiceController {

    private final String INVOICES_PURCHASES = "/invoices-purchases";
    private final InvoiceMapper invoiceMapper;
    private final InvoiceRepository invoiceRepository;

    @GetMapping(value = INVOICES_PURCHASES)
    public ModelAndView getAllInvoices() {
        Map<String, ?> model = preparePurchaseInvoiceCustomerData();
        return new ModelAndView("info/invoice_purchases", model);
    }

    private Map<String, ?> preparePurchaseInvoiceCustomerData() {
        var availableInvoices = invoiceRepository.findAll().stream()
                .map(invoiceMapper::map)
                .toList();
        return Map.of("availableInvoicesDTOs", availableInvoices);
    }
}

