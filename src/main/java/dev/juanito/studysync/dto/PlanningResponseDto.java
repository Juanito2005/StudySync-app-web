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
public class PlanningResponseDto {

    private Long id;
    private Double plannedHours;
    private LocalDateTime date;
    private String status;

    // Info del usuario sin causar recursión
    private Long userId;
    private String userName;

    // Info de la materia
    private Long subjectId;
    private String subjectName;

    // Si tiene un RoutineLog asociado, mostramos su ID
    private Long routineLogId;
}
