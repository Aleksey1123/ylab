package org.example.entity;

import lombok.*;

/**
 * Сущность пользователь с полями ID, имя, пароль.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Integer id;
    private String username;
    private String password;
}
