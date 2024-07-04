package org.example.entity;

import lombok.*;

import java.time.LocalDateTime;

/**
 * Сущность бронирование с полями ID, ID ресурса, дата начала бронирования,
 * дата конца бронирования, пользователь(забронировавший ресурс).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    private Integer id;
    private Integer workplaceId;
    private Integer hallId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private User user;
}
