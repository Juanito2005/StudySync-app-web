package dev.juanito.studysync.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PlanningUpdateDto {

    private Double plannedHours;
    private LocalDateTime date;
    private String status; // CREATED, COMPLETED, DELETED
    private Long subjectId;
}
