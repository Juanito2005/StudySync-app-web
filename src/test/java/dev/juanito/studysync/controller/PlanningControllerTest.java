package dev.juanito.studysync.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.juanito.studysync.dto.PlanningCreationDto;
import dev.juanito.studysync.dto.PlanningResponseDto;
import dev.juanito.studysync.dto.PlanningUpdateDto;
import dev.juanito.studysync.security.AuthenticationHelper;
import dev.juanito.studysync.security.jwt.JwtService;
import dev.juanito.studysync.service.PlanningService;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PlanningController.class)
@AutoConfigureMockMvc(addFilters = false)
class PlanningControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlanningService planningService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private AuthenticationHelper authenticationHelper;

    @Autowired
    private ObjectMapper objectMapper;

    private PlanningResponseDto sampleResponse;
    private PlanningCreationDto sampleCreation;

    @BeforeEach
    void setUp() {
        sampleResponse = new PlanningResponseDto();
        sampleResponse.setId(1L);
        sampleResponse.setPlannedHours(2.5);
        sampleResponse.setDate(LocalDateTime.of(2026, 5, 20, 14, 0));
        sampleResponse.setStatus("CREATED");
        sampleResponse.setUserId(1L);
        sampleResponse.setUserName("Juanito");
        sampleResponse.setSubjectId(10L);
        sampleResponse.setSubjectName("Math");

        sampleCreation = new PlanningCreationDto();
        sampleCreation.setPlannedHours(2.5);
        sampleCreation.setDate(LocalDateTime.of(2026, 5, 20, 14, 0));
        sampleCreation.setSubjectId(10L);
    }

    @Test
    void createPlanning_ShouldReturn201() throws Exception {
        when(planningService.createPlanning(any())).thenReturn(sampleResponse);

        mockMvc
            .perform(
                post("/api/plannings")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(sampleCreation))
            )
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.plannedHours").value(2.5))
            .andExpect(jsonPath("$.status").value("CREATED"))
            .andExpect(jsonPath("$.subjectId").value(10));
    }

    @Test
    void createPlanning_WithoutPlannedHours_ShouldReturn400() throws Exception {
        sampleCreation.setPlannedHours(null);

        mockMvc
            .perform(
                post("/api/plannings")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(sampleCreation))
            )
            .andExpect(status().isBadRequest());
    }

    @Test
    void createPlanning_WithoutSubjectId_ShouldReturn400() throws Exception {
        sampleCreation.setSubjectId(null);

        mockMvc
            .perform(
                post("/api/plannings")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(sampleCreation))
            )
            .andExpect(status().isBadRequest());
    }

    @Test
    void getPlanningById_ShouldReturn200() throws Exception {
        when(planningService.findPlanningById(1L)).thenReturn(sampleResponse);

        mockMvc
            .perform(get("/api/plannings/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.subjectName").value("Math"));
    }

    @Test
    void getPlannings_ListAll_ShouldReturn200() throws Exception {
        when(planningService.findPlanningsByUser()).thenReturn(
            List.of(sampleResponse)
        );

        mockMvc
            .perform(get("/api/plannings"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void getPlannings_FilterBySubject_ShouldReturn200() throws Exception {
        when(planningService.findPlanningsBySubject(10L)).thenReturn(
            List.of(sampleResponse)
        );

        mockMvc
            .perform(get("/api/plannings?subjectId=10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].subjectId").value(10));
    }

    @Test
    void updatePlanning_ShouldReturn200() throws Exception {
        PlanningUpdateDto updateDto = new PlanningUpdateDto();
        updateDto.setPlannedHours(3.0);
        updateDto.setStatus("COMPLETED");

        PlanningResponseDto updated = new PlanningResponseDto();
        updated.setId(1L);
        updated.setPlannedHours(3.0);
        updated.setStatus("COMPLETED");

        when(planningService.updatePlanning(eq(1L), any())).thenReturn(updated);

        mockMvc
            .perform(
                put("/api/plannings/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(updateDto))
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("COMPLETED"))
            .andExpect(jsonPath("$.plannedHours").value(3.0));
    }

    @Test
    void deletePlanning_ShouldReturn204() throws Exception {
        doNothing().when(planningService).deletePlanning(1L);

        mockMvc
            .perform(delete("/api/plannings/1"))
            .andExpect(status().isNoContent());
    }
}
