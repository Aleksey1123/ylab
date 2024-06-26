package org.example.entity;

import lombok.*;

import java.util.UUID;

/**
 * Сущность пользователь с полями ID, описание, размер(в местах).
 */
@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ConferenceHall {

    private UUID id;
    private String description;
    private Integer size;
}
