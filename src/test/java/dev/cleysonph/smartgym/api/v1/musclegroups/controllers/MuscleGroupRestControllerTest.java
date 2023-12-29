package dev.cleysonph.smartgym.api.v1.musclegroups.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.test.web.servlet.MockMvc;

import dev.cleysonph.smartgym.api.v1.musclegroups.dtos.MuscleGroupResponse;
import dev.cleysonph.smartgym.api.v1.musclegroups.services.MuscleGroupService;
import dev.cleysonph.smartgym.config.SecurityConfig;
import dev.cleysonph.smartgym.core.repositories.UserRepository;
import dev.cleysonph.smartgym.core.services.datetime.DateTimeService;
import dev.cleysonph.smartgym.core.services.token.TokenService;

@Import(SecurityConfig.class)
@WebMvcTest(MuscleGroupRestController.class)
class MuscleGroupRestControllerTest {

    @MockBean
    private MuscleGroupService muscleGroupService;

    @MockBean
    private DateTimeService dateTimeService;

    @MockBean
    private AuthenticationEntryPoint authenticationEntryPoint;

    @MockBean
    private TokenService tokenService;

    @MockBean UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    private static final String MUSCLE_GROUP_ENDPOINT = "/api/v1/muscle-groups";

    @Test
    void whenGETMuscleGroups_thenReturnListOfMuscleGroupResponse_200() throws Exception {
        var body = List.of(
            new MuscleGroupResponse("CHEST"),
            new MuscleGroupResponse("BACK"),
            new MuscleGroupResponse("SHOULDER")
        );

        when(muscleGroupService.findAll()).thenReturn(body);

        mockMvc.perform(get(MUSCLE_GROUP_ENDPOINT))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(body.size()))
            .andExpect(jsonPath("$[0].name").value(body.get(0).getName()))
            .andExpect(jsonPath("$[1].name").value(body.get(1).getName()))
            .andExpect(jsonPath("$[2].name").value(body.get(2).getName()));
    }
    
}
