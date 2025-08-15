package dev.juanito.studysync.model;

import java.util.List;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

public class Subject {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    @ManyToOne //considero que esto no debería tener un mappedBy, no?
    private Long user_id;
    @OneToMany(mappedBy = "subject_id")
    private List<Planning> plannings;
    @OneToMany(mappedBy = "subject_id")
    private List<RoutineLog> routineLogs;
    private String name;
    private String color; //Según lo que tengo entendido, el color va con un HEX, que es eso? como lo añado?
}
