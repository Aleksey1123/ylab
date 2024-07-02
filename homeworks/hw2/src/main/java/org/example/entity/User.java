package org.example.entity;

import lombok.*;

/**
 * Сущность пользователь с полями ID, имя, пароль.
 */
@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class User {

    private Integer id;
    private String username;
    private String password;
}
