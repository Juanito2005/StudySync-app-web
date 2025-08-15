package dev.juanito.studysync.model;

import java.sql.Date;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

public class Planning {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Long subject_id;
    @ManyToOne
    private Long user_id;
    @OneToOne(mappedBy = "planning_id")
    private Long routineLog_id;
    private Double plannedHours;
    private Date date;
    private Enum status;
}
