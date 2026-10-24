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
public class RoutineLogUpdateDto {

    private Double actualHours;
    private LocalDateTime date;
    private String notes;
    private Long subjectId;
}
