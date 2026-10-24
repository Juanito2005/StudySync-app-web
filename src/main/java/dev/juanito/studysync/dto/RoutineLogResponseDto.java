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
public class RoutineLogResponseDto {

    private Long id;
    private Double actualHours;
    private LocalDateTime date;
    private String notes;

    // Info del usuario sin causar recursión
    private Long userId;
    private String userName;

    // Info de la materia
    private Long subjectId;
    private String subjectName;

    // Si está asociado a un Planning, mostramos su ID
    private Long planningId;
}
