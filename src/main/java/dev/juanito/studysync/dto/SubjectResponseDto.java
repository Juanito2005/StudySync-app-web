package dev.juanito.studysync.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubjectResponseDto {
    private Long id;
    private String name;
    private String colorHEX;
    
    // Info del usuario sin causar recursión
    private Long userId;
    private String userName;
}
