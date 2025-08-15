package dev.juanito.studysync.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    // The mappedBy function indicates that the other entities already have a user_id column
    @OneToMany(mappedBy = "user_id")
    // This must be a List<> cause is "much" objects are going to be used in the relationship
    private List<Subject> subjects;

    @OneToMany(mappedBy = "user_id")
    private List<RoutineLog> routineLogs;

    @OneToMany(mappedBy = "user_id")
    private List<Planning> plannings;

    private String username;
    private String password;
    private String email;
}
