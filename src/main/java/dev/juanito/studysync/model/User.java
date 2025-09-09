package dev.juanito.studysync.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password; //This will be gestionated by the UserService as a Hash

    @OneToMany(mappedBy = "user") //It's "user" instead "user_id" cause mappedBy references at the atribute in the class code not in the table fo the DB
    private List<Subject> subjects; //It's a List cause 1 user has a "List/Lot" of Subjects not a single Subject

    @OneToMany(mappedBy = "user")
    private List<Planning> plannings;

    @OneToMany(mappedBy = "user")
    private List<RoutineLog> routineLogs;
}