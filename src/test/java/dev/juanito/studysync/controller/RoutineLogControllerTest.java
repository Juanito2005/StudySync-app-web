package dev.juanito.studysync.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.juanito.studysync.dto.RoutineLogCreationDto;
import dev.juanito.studysync.dto.RoutineLogResponseDto;
import dev.juanito.studysync.dto.RoutineLogUpdateDto;
import dev.juanito.studysync.security.AuthenticationHelper;
import dev.juanito.studysync.security.jwt.JwtService;
import dev.juanito.studysync.service.RoutineLogService;
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

@WebMvcTest(RoutineLogController.class)
@AutoConfigureMockMvc(addFilters = false)
class RoutineLogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoutineLogService routineLogService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private AuthenticationHelper authenticationHelper;

    @Autowired
    private ObjectMapper objectMapper;

    private RoutineLogResponseDto sampleResponse;
    private RoutineLogCreationDto sampleCreation;

    @BeforeEach
    void setUp() {
        sampleResponse = new RoutineLogResponseDto();
        sampleResponse.setId(1L);
        sampleResponse.setActualHours(1.5);
        sampleResponse.setDate(LocalDateTime.of(2026, 5, 21, 10, 0));
        sampleResponse.setNotes("Repasé derivadas");
        sampleResponse.setUserId(1L);
        sampleResponse.setUserName("Juanito");
        sampleResponse.setSubjectId(10L);
        sampleResponse.setSubjectName("Math");

        sampleCreation = new RoutineLogCreationDto();
        sampleCreation.setActualHours(1.5);
        sampleCreation.setDate(LocalDateTime.of(2026, 5, 21, 10, 0));
        sampleCreation.setNotes("Repasé derivadas");
        sampleCreation.setSubjectId(10L);
    }

    @Test
    void createRoutineLog_ShouldReturn201() throws Exception {
        when(routineLogService.createRoutineLog(any())).thenReturn(
            sampleResponse
        );

        mockMvc
            .perform(
                post("/api/routine-logs")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(sampleCreation))
            )
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.actualHours").value(1.5))
            .andExpect(jsonPath("$.notes").value("Repasé derivadas"));
    }

    @Test
    void createRoutineLog_WithoutActualHours_ShouldReturn400()
        throws Exception {
        sampleCreation.setActualHours(null);

        mockMvc
            .perform(
                post("/api/routine-logs")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(sampleCreation))
            )
            .andExpect(status().isBadRequest());
    }

    @Test
    void createRoutineLog_WithoutSubjectId_ShouldReturn400() throws Exception {
        sampleCreation.setSubjectId(null);

        mockMvc
            .perform(
                post("/api/routine-logs")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(sampleCreation))
            )
            .andExpect(status().isBadRequest());
    }

    @Test
    void getRoutineLogById_ShouldReturn200() throws Exception {
        when(routineLogService.findRoutineLogById(1L)).thenReturn(
            sampleResponse
        );

        mockMvc
            .perform(get("/api/routine-logs/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.actualHours").value(1.5));
    }

    @Test
    void getRoutineLogs_ShouldReturn200() throws Exception {
        when(routineLogService.findRoutineLogsByUser()).thenReturn(
            List.of(sampleResponse)
        );

        mockMvc
            .perform(get("/api/routine-logs"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void getRoutineLogs_FilterBySubject_ShouldReturn200() throws Exception {
        when(routineLogService.findRoutineLogsBySubject(10L)).thenReturn(
            List.of(sampleResponse)
        );

        mockMvc
            .perform(get("/api/routine-logs?subjectId=10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].subjectId").value(10));
    }

    @Test
    void updateRoutineLog_ShouldReturn200() throws Exception {
        RoutineLogUpdateDto updateDto = new RoutineLogUpdateDto();
        updateDto.setActualHours(2.0);
        updateDto.setNotes("Actualizado");

        RoutineLogResponseDto updated = new RoutineLogResponseDto();
        updated.setId(1L);
        updated.setActualHours(2.0);
        updated.setNotes("Actualizado");

        when(routineLogService.updateRoutineLog(eq(1L), any())).thenReturn(
            updated
        );

        mockMvc
            .perform(
                put("/api/routine-logs/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(updateDto))
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.actualHours").value(2.0))
            .andExpect(jsonPath("$.notes").value("Actualizado"));
    }

    @Test
    void deleteRoutineLog_ShouldReturn204() throws Exception {
        doNothing().when(routineLogService).deleteRoutineLog(1L);

        mockMvc
            .perform(delete("/api/routine-logs/1"))
            .andExpect(status().isNoContent());
    }
}
