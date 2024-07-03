package org.example.entity;

import lombok.*;

/**
 * Сущность пользователь с полями ID, описание, размер(в местах).
 */
@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ConferenceHall {

    private Integer id;
    private String description;
    private Integer size;
}
