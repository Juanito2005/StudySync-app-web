package dev.juanito.studysync.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserPrincipal {

    private final String email;
    private final Long userId;

    public String getName() {
        return email;
    }
}
