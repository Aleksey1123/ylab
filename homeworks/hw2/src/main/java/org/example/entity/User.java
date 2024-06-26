package org.example.entity;

import lombok.*;

import java.util.UUID;

/**
 * Сущность пользователь с полями ID, имя, пароль.
 */
@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class User {

    private UUID id;
    private String username;
    private String password;
}
