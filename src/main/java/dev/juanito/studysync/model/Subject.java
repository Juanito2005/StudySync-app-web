package dev.juanito.studysync.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "subjects")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Subject {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "color", nullable = false)
    private String colorHEX; //It's saved as a hexadecimal string

    //JoinColumn creates the foreign key at the "many" table. Therefore, it creates the relationship
    @ManyToOne
    @JoinColumn(name = "user_id") //It's "user_id" instead of "user" 'cause "JoinColumn" references the column's name at the table
    private User user; //This is the attribute that "mappedBy" in "User" is refering to

    @OneToMany(mappedBy = "subject")
    private List<Planning> plannings;

    @OneToMany(mappedBy = "subject")
    private List<RoutineLog> routineLogs;
}