package com.codfish.bikeSalesAndService.api.controller;

import com.codfish.bikeSalesAndService.api.dto.mapper.PersonRepairingMapper;
import com.codfish.bikeSalesAndService.api.dto.mapper.UserMapper;
import com.codfish.bikeSalesAndService.business.BikeServiceRequestService;
import com.codfish.bikeSalesAndService.business.PersonRepairingService;
import com.codfish.bikeSalesAndService.business.UserService;
import com.codfish.bikeSalesAndService.infrastructure.database.repository.jpa.PersonRepairingJpaRepository;
import com.codfish.bikeSalesAndService.infrastructure.security.RoleRepository;
import com.codfish.bikeSalesAndService.infrastructure.security.UserJpaRepository;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@TestPropertySource(locations = "classpath:application-test.yml")
@WebMvcTest(AddUpdatePersonRepairingController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
@ExtendWith(MockitoExtension.class)
class AddUpdatePersonRepairingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @MockBean
    PersonRepairingService personRepairingService;

    @MockBean
    PersonRepairingJpaRepository personRepairingJpaRepository;

    @MockBean
    PersonRepairingMapper personRepairingMapper;

    @MockBean
    BikeServiceRequestService bikeServiceRequestService;

    @MockBean
    UserJpaRepository userJpaRepository;

    @MockBean
    RoleRepository roleRepository;

    @MockBean
    UserService userService;

    @MockBean
    UserMapper userMapper;
    private AddUpdatePersonRepairingController controller;

    @Test
    public void testPersonRepairingAddUpdatePage() {
        Model mockModel = mock(Model.class);
        AddUpdatePersonRepairingController controller = new AddUpdatePersonRepairingController(
                bCryptPasswordEncoder,
                personRepairingService,
                personRepairingJpaRepository,
                personRepairingMapper,
                bikeServiceRequestService,
                userJpaRepository,
                roleRepository,
                userService,
                userMapper

        );

        String view = controller.personRepairingAddUpdatePage(mockModel);
        Assertions.assertEquals("info/add_update_person_repairing", view);
        verify(mockModel).addAttribute(eq("availableUserDTOs"), any());
        verify(mockModel).addAttribute(eq("availablePersonRepairingDTOs"), any());
    }

    @Test
    public void testDeletePersonRepairing() {

        Model mockModel = mock(Model.class);
        String codeNameSurname = "testCodeNameSurname";

        String viewName = controller.deletePersonRepairing(codeNameSurname, mockModel);

        assertEquals("info/delete_person_repairing_done", viewName);
        verify(personRepairingService).deletePersonRepairingByCodeNameSurname(eq(codeNameSurname));
    }

}