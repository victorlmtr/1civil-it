package com.backend.user;

import com.backend.user.config.WebSecurityConfig;
import com.backend.user.controller.AddressController;
import com.backend.user.model.dto.AddressDTO;
import com.backend.user.model.dto.CityDTO;
import com.backend.user.model.dto.RoleDTO;
import com.backend.user.model.dto.UserDTO;
import com.backend.user.service.AddressService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AddressController.class)
@EnableWebSecurity
@Import(WebSecurityConfig.class)
public class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressService addressService;

    @Test
    void testCreateAddress() throws Exception {
        // Création des objets DTO
        CityDTO city = CityDTO.builder()
                .id(1) // L'ID est défini car c'est une donnée déjà existante
                .cityname("Paris")
                .postcode("75000")
                .inseecode("75530")
                .build();

        RoleDTO role = RoleDTO.builder()
                .id(1) // L'ID est défini car c'est une donnée déjà existante
                .rolename("Admin")
                .build();

        UserDTO user = UserDTO.builder()
                .id(1) // L'ID est défini car l'utilisateur existe déjà
                .firstname("John")
                .lastname("Doe")
                .email("john.doe@example.com")
                .phonenumber("123456789")
                .creationdate(Instant.now())
                .isverified(true)
                .isenabled(true)
                .role(role)
                .city(city)
                .build();

        AddressDTO addressDTO = AddressDTO.builder()
                .id(1) // Simule l'ID généré par la base de données
                .streetnumber("123")
                .streetname("Main Street")
                .adressdetails("Apt 4B")
                .city(city)
                .user(user)
                .build();

        // Mock du service pour simuler la méthode createAddress
        Mockito.when(addressService.createAddress(Mockito.any(AddressDTO.class)))
                .thenReturn(addressDTO);

        // Corps de la requête sans ID (l'ID sera généré automatiquement)
        String requestBody = """
    {
        "streetnumber": "123",
        "streetname": "Main Street",
        "adressdetails": "Apt 4B",
        "city": {
            "id": 1,
            "cityname": "Paris",
            "postcode": "75000",
            "inseecode": "75530"
        },
        "user": {
            "id": 1,
            "firstname": "John",
            "lastname": "Doe",
            "email": "john.doe@example.com",
            "phonenumber": "123456789",
            "creationdate": "2024-11-18T12:00:00Z",
            "isverified": true,
            "isenabled": true,
            "role": {
                "id": 1,
                "rolename": "Admin"
            },
            "city": {
                "id": 1,
                "cityname": "Paris",
                "postcode": "75000",
                "inseecode": "75530"
            }
        }
    }
    """;

        // Appel MockMvc
        mockMvc.perform(post("/api-user/addresses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated()) // Statut HTTP 201
                .andExpect(jsonPath("$.id").value(1)) // Vérifie que l'ID généré est retourné
                .andExpect(jsonPath("$.streetnumber").value("123"))
                .andExpect(jsonPath("$.streetname").value("Main Street"))
                .andExpect(jsonPath("$.adressdetails").value("Apt 4B"))
                .andExpect(jsonPath("$.city.cityname").value("Paris"))
                .andExpect(jsonPath("$.user.firstname").value("John"));
    }
}
