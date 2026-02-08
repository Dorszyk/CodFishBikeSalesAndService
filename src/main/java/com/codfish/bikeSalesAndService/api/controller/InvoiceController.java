package com.codfish.bikeSalesAndService.api.controller;

import com.codfish.bikeSalesAndService.api.dto.InvoiceDTO;
import com.codfish.bikeSalesAndService.business.PdfService;
import com.codfish.bikeSalesAndService.infrastructure.database.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class InvoiceController {

    private static final String INVOICE_PURCHASES_PATH = "/invoices_purchases";
    private static final String DOWNLOAD_INVOICE_PDF_PATH = "/invoices/download/{invoiceNumber}/**";

    private final InvoiceRepository invoiceRepository;
    private final PdfService pdfService;

    @GetMapping(value = INVOICE_PURCHASES_PATH)
    public ModelAndView getAllInvoices() {
        Map<String, ?> model = preparePurchaseInvoiceCustomerData();
        return new ModelAndView("info/invoice_purchases", model);
    }

    @GetMapping(value = DOWNLOAD_INVOICE_PDF_PATH)
    public ResponseEntity<byte[]> downloadInvoicePdf(jakarta.servlet.http.HttpServletRequest request) throws IOException {
        String path = (String) request.getAttribute(org.springframework.web.servlet.HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String invoiceNumber = path.substring("/invoices/download/".length());

        InvoiceDTO invoice = invoiceRepository.findByInvoiceNumber(invoiceNumber);

        byte[] pdfContent = pdfService.generateInvoicePdf(invoice);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=invoice_" + invoiceNumber.replace("/", "_") + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfContent);
    }

    private Map<String, ?> preparePurchaseInvoiceCustomerData() {
        var availableInvoices = invoiceRepository.findAllInvoicesDTO();
        return Map.of("availableInvoicesDTOs", availableInvoices);
    }
}

