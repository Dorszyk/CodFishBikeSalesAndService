package com.codfish.bikeSalesAndService.api.controller;

import com.codfish.bikeSalesAndService.business.InvoiceService;
import com.codfish.bikeSalesAndService.business.PdfService;
import com.codfish.bikeSalesAndService.infrastructure.database.repository.InvoiceRepository;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@TestPropertySource(locations = "classpath:application-test.yml")
@WebMvcTest({FragmentsController.class, InvoiceController.class})
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
@ExtendWith(MockitoExtension.class)
class FragmentsControllerTest {

    @MockitoBean
    private InvoiceService invoiceService;

    @MockitoBean
    private InvoiceRepository invoiceRepository;

    @MockitoBean
    private PdfService pdfService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getFragmentsHomeTest() throws Exception {
        mockMvc.perform(get("/home/home"))
                .andExpect(status().isOk())
                .andExpect(view().name("home/home"));
    }


    @Test
    void getFragmentsManagementUserSalesmanTest() throws Exception {
        mockMvc.perform(get("/info/add_update_salesman"))
                .andExpect(status().isOk())
                .andExpect(view().name("info/add_update_salesman"));
    }

    @Test
    void getFragmentsManagementUserPersonRepairingTest
            () throws Exception {
        mockMvc.perform(get("/info/add_update_person_repairing"))
                .andExpect(status().isOk())
                .andExpect(view().name("info/add_update_person_repairing"));
    }

    @Test
    void getFragmentsInvoicesTest() throws Exception {
        mockMvc.perform(get("/invoices_purchases"))
                .andExpect(status().isOk())
                .andExpect(view().name("info/invoice_purchases"));
    }
}