package org.example.entity;

import lombok.*;


/**
 * Сущность рабочее место с полями ID и описание.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Workplace {

    private Integer id;
    private String description;
}
