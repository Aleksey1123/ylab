package org.example.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Сущность бронирование с полями ID, ID ресурса, дата начала бронирования,
 * дата конца бронирования, пользователь(забронировавший ресурс).
 */
@Getter
@Setter
@Builder
@ToString
public class Booking {

    private UUID id;
    private UUID workplaceId;
    private UUID hallId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private User user;
}
