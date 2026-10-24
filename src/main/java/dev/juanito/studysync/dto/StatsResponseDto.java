package dev.juanito.studysync.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StatsResponseDto {

    // Resumen general
    private Double totalPlannedHours;
    private Double totalActualHours;
    private Double completionRate;      // 0.0 a 1.0
    private Integer totalPlannings;
    private Integer completedPlannings;
    private Integer totalRoutineLogs;
    private Integer currentStreak;      // días consecutivos estudiando

    // Desglose por materia
    private List<SubjectStatsDto> bySubject;

    // Desglose semanal (últimas 4 semanas)
    private List<WeeklyStatsDto> weekly;
}
