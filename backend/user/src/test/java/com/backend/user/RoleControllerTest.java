package com.backend.user;

import com.backend.user.security.config.WebSecurityConfig;
import com.backend.user.controller.RoleController;
import com.backend.user.model.dto.RoleDTO;
import com.backend.user.service.RoleService;
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

@WebMvcTest(RoleController.class)
@EnableWebSecurity
@Import(WebSecurityConfig.class)
public class RoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoleService roleService; // The service managing roles

    @Test
    void testCreateRole() throws Exception {
        // Creating a RoleDTO object
        RoleDTO roleDTO = RoleDTO.builder()
                .id(1)
                .rolename("ADMIN")
                .build();

        // Mocking the service to simulate the createRole method
        Mockito.when(roleService.createRole(Mockito.any(RoleDTO.class)))
                .thenReturn(roleDTO);

        // Request body without ID (ID will be generated automatically)
        String requestBody = """
        {
            "rolename": "ADMIN"
        }
        """;

        // MockMvc call
        mockMvc.perform(post("/api-user/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated()) // HTTP Status 201
                .andExpect(jsonPath("$.id").value(1)) // Verifying that the generated ID is returned
                .andExpect(jsonPath("$.rolename").value("ADMIN"));
    }

    @Test
    void testGetAllRoles() throws Exception {
        // Creating a mock list of RoleDTOs
        RoleDTO roleDTO1 = new RoleDTO(1, "ADMIN");
        RoleDTO roleDTO2 = new RoleDTO(2, "USER");
        List<RoleDTO> roles = List.of(roleDTO1, roleDTO2);

        // Mocking the service to return the list of roles
        Mockito.when(roleService.getAllRoles()).thenReturn(roles);

        // MockMvc call to check the response
        mockMvc.perform(get("/api-user/roles"))
                .andExpect(status().isOk()) // Verifying that the status is 200
                .andExpect(jsonPath("$[0].id").value(1)) // Verifying the first element
                .andExpect(jsonPath("$[0].rolename").value("ADMIN"))
                .andExpect(jsonPath("$[1].rolename").value("USER")); // Verifying the second element
    }

    @Test
    void testGetRoleById() throws Exception {
        // Creating a mock RoleDTO
        RoleDTO roleDTO = new RoleDTO(1, "ADMIN");

        // Mocking the service to return a RoleDTO for a given ID
        Mockito.when(roleService.getRoleById(1)).thenReturn(Optional.of(roleDTO));

        // MockMvc call to check the response
        mockMvc.perform(get("/api-user/roles/1"))
                .andExpect(status().isOk()) // Verifying that the status is 200
                .andExpect(jsonPath("$.id").value(1)) // Verifying the ID
                .andExpect(jsonPath("$.rolename").value("ADMIN")); // Verifying the role name
    }

    @Test
    void testDeleteRole() throws Exception {
        // Mocking the service to delete an existing role
        Mockito.when(roleService.deleteRole(1)).thenReturn(true);

        // MockMvc call to verify the deletion
        mockMvc.perform(delete("/api-user/roles/1"))
                .andExpect(status().isNoContent()); // Verifying that the status is 204 (No Content)
    }
}
