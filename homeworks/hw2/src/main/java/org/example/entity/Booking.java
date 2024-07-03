package org.example.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * Сущность бронирование с полями ID, ID ресурса, дата начала бронирования,
 * дата конца бронирования, пользователь(забронировавший ресурс).
 */
@Getter
@Setter
@Builder
@ToString
public class Booking {

    private Integer id;
    private Integer workplaceId;
    private Integer hallId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private User user;
}
