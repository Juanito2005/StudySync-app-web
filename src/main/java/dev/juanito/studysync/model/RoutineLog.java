package dev.juanito.studysync.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

public class RoutineLog {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Long user_id;
    @OneToOne(mappedBy = "routineLog_id")
    private Long planning_id;
    @ManyToOne
    private Long subcjet_id;
    private double actualHours;
    private java.sql.Date date;
    private String observation;
}
