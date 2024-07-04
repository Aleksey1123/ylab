package org.example.entity;

import lombok.*;

/**
 * Сущность пользователь с полями ID, описание, размер(в местах).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConferenceHall {

    private Integer id;
    private String description;
    private Integer size;
}
