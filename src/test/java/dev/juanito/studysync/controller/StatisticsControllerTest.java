package dev.juanito.studysync.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import dev.juanito.studysync.dto.StatsResponseDto;
import dev.juanito.studysync.dto.SubjectStatsDto;
import dev.juanito.studysync.dto.WeeklyStatsDto;
import dev.juanito.studysync.security.AuthenticationHelper;
import dev.juanito.studysync.security.jwt.JwtService;
import dev.juanito.studysync.service.StatisticsService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(StatisticsController.class)
@AutoConfigureMockMvc(addFilters = false)
class StatisticsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StatisticsService statisticsService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private AuthenticationHelper authenticationHelper;

    private StatsResponseDto sampleStats;

    @BeforeEach
    void setUp() {
        SubjectStatsDto mathStats = new SubjectStatsDto();
        mathStats.setSubjectId(1L);
        mathStats.setSubjectName("Math");
        mathStats.setColorHEX("#FF0000");
        mathStats.setPlannedHours(10.0);
        mathStats.setActualHours(8.5);
        mathStats.setPlanningCount(4);
        mathStats.setRoutineLogCount(3);

        WeeklyStatsDto week = new WeeklyStatsDto();
        week.setWeekLabel("19 May - 25 May 2026");
        week.setPlannedHours(5.0);
        week.setActualHours(4.0);

        sampleStats = new StatsResponseDto();
        sampleStats.setTotalPlannedHours(15.0);
        sampleStats.setTotalActualHours(12.0);
        sampleStats.setCompletionRate(0.75);
        sampleStats.setTotalPlannings(6);
        sampleStats.setCompletedPlannings(4);
        sampleStats.setTotalRoutineLogs(5);
        sampleStats.setCurrentStreak(3);
        sampleStats.setBySubject(List.of(mathStats));
        sampleStats.setWeekly(List.of(week));
    }

    @Test
    void getStatistics_ShouldReturn200_WithAllFields() throws Exception {
        when(statisticsService.getUserStatistics()).thenReturn(sampleStats);

        mockMvc
            .perform(get("/api/stats"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.totalPlannedHours").value(15.0))
            .andExpect(jsonPath("$.totalActualHours").value(12.0))
            .andExpect(jsonPath("$.completionRate").value(0.75))
            .andExpect(jsonPath("$.totalPlannings").value(6))
            .andExpect(jsonPath("$.completedPlannings").value(4))
            .andExpect(jsonPath("$.currentStreak").value(3))
            .andExpect(jsonPath("$.bySubject[0].subjectName").value("Math"))
            .andExpect(jsonPath("$.bySubject[0].plannedHours").value(10.0))
            .andExpect(
                jsonPath("$.weekly[0].weekLabel").value("19 May - 25 May 2026")
            )
            .andExpect(jsonPath("$.weekly[0].plannedHours").value(5.0));
    }

    @Test
    void getStatistics_EmptyData_ShouldReturn200() throws Exception {
        StatsResponseDto empty = new StatsResponseDto();
        empty.setTotalPlannedHours(0.0);
        empty.setTotalActualHours(0.0);
        empty.setCompletionRate(0.0);
        empty.setTotalPlannings(0);
        empty.setCompletedPlannings(0);
        empty.setTotalRoutineLogs(0);
        empty.setCurrentStreak(0);
        empty.setBySubject(List.of());
        empty.setWeekly(List.of());

        when(statisticsService.getUserStatistics()).thenReturn(empty);

        mockMvc
            .perform(get("/api/stats"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.currentStreak").value(0))
            .andExpect(jsonPath("$.bySubject").isEmpty());
    }
}
