package org.example.entity;

import lombok.*;

import java.util.UUID;


/**
 * Сущность рабочее место с полями ID и описание.
 */
@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
public class Workplace {

    private UUID id;
    private String description;
}
