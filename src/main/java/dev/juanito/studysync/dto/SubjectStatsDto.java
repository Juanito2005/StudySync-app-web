package dev.juanito.studysync.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubjectStatsDto {

    private Long subjectId;
    private String subjectName;
    private String colorHEX;

    private Double plannedHours;
    private Double actualHours;
    private Integer planningCount;
    private Integer routineLogCount;
}
