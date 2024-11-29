package com.backend.user;

import com.backend.user.config.WebSecurityConfig;
import com.backend.user.controller.CityController;
import com.backend.user.model.dto.CityDTO;
import com.backend.user.service.CityService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CityController.class)
@EnableWebSecurity
@Import(WebSecurityConfig.class)
public class CityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CityService cityService; // The service managing cities

    @Test
    void testCreateCity() throws Exception {
        // Creating a CityDTO object
        CityDTO cityDTO = CityDTO.builder()
                .id(1)
                .cityname("Paris")
                .postcode("75000")
                .inseecode("75530")
                .build();

        // Mocking the service to simulate the createCity method
        Mockito.when(cityService.createCity(Mockito.any(CityDTO.class)))
                .thenReturn(cityDTO);

        // Request body without ID (ID will be generated automatically)
        String requestBody = """
        {
            "cityname": "Paris",
            "postcode": "75000",
            "inseecode": "75530"
        }
        """;

        // MockMvc call
        mockMvc.perform(post("/api-user/cities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated()) // HTTP Status 201
                .andExpect(jsonPath("$.id").value(1)) // Verifying that the generated ID is returned
                .andExpect(jsonPath("$.cityname").value("Paris"))
                .andExpect(jsonPath("$.postcode").value("75000"))
                .andExpect(jsonPath("$.inseecode").value("75530"));
    }


    @Test
    void testGetAllCities() throws Exception {
        // Creating a mock list of CityDTOs
        CityDTO cityDTO1 = new CityDTO(1, "Paris", "75530", "75000");
        CityDTO cityDTO2 = new CityDTO(2, "Lyon", "69000", "69001");
        List<CityDTO> cities = List.of(cityDTO1, cityDTO2);

        // Mocking the service to return the list of cities
        Mockito.when(cityService.getAllCities()).thenReturn(cities);

        // MockMvc call to check the response
        mockMvc.perform(get("/api-user/cities"))
                .andExpect(status().isOk()) // Verifying that the status is 200
                .andExpect(jsonPath("$[0].id").value(1)) // Verifying the first element
                .andExpect(jsonPath("$[0].cityname").value("Paris"))
                .andExpect(jsonPath("$[1].cityname").value("Lyon")); // Verifying the second element
    }


    @Test
    void testGetCityById() throws Exception {
        // Creating a mock CityDTO
        CityDTO cityDTO = new CityDTO(1, "Paris", "75530", "75000");

        // Mocking the service to return a CityDTO for a given ID
        Mockito.when(cityService.getCityById(1)).thenReturn(Optional.of(cityDTO));

        // MockMvc call to check the response
        mockMvc.perform(get("/api-user/cities/1"))
                .andExpect(status().isOk()) // Verifying that the status is 200
                .andExpect(jsonPath("$.id").value(1)) // Verifying the ID
                .andExpect(jsonPath("$.cityname").value("Paris")); // Verifying the city name
    }

    @Test
    void testDeleteCity() throws Exception {
        // Mocking the service to delete an existing city
        Mockito.when(cityService.deleteCity(1)).thenReturn(true);

        // MockMvc call to verify the deletion
        mockMvc.perform(delete("/api-user/cities/1"))
                .andExpect(status().isNoContent()); // Vérifie que le statut est 204
    }

}

