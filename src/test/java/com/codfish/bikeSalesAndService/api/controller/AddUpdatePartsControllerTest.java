package com.codfish.bikeSalesAndService.api.controller;

import com.codfish.bikeSalesAndService.api.dto.mapper.PartMapper;
import com.codfish.bikeSalesAndService.business.PartCatalogService;
import com.codfish.bikeSalesAndService.infrastructure.database.repository.jpa.PartJpaRepository;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestPropertySource(locations = "classpath:application-test.yml")
@WebMvcTest(AddUpdatePartsController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class AddUpdatePartsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PartCatalogService partCatalogService;

    @MockBean
    private PartMapper partMapper;

    @MockBean
    private PartJpaRepository partJpaRepository;

    @Test
    public void testPartsAddUpdatePartPage() throws Exception {
        mockMvc.perform(get("/add_update_parts"))
                .andExpect(status().isOk())
                .andExpect(view().name("info/add_update_parts"))
                .andExpect(model().attributeExists("partDTOs", "partSerialNumbers"));
    }
}