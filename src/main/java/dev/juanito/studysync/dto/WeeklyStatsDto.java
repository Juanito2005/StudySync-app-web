package dev.juanito.studysync.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WeeklyStatsDto {

    private String weekLabel;   // ej: "19 May - 25 May 2026"
    private Double plannedHours;
    private Double actualHours;
}
