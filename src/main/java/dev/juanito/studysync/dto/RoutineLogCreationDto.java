package dev.juanito.studysync.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoutineLogCreationDto {

    @NotNull(message = "Las horas reales son obligatorias, ponme un número")
    @Positive(message = "Las horas reales deben ser un número positivo, obvio")
    private Double actualHours;

    @NotNull(message = "La fecha es obligatoria, no me hagas buscar en el calendario")
    private LocalDateTime date;

    private String notes;

    @NotNull(message = "La materia es obligatoria, ¿pa' qué materia estudiaste?")
    private Long subjectId;
}
