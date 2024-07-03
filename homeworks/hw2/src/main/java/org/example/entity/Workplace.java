package org.example.entity;

import lombok.*;


/**
 * Сущность рабочее место с полями ID и описание.
 */
@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
public class Workplace {

    private Integer id;
    private String description;
}
