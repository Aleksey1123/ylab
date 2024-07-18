package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.User;

import java.time.LocalDateTime;

/** DTO class for user interactions with the booking entity. **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {
    private Integer id;
    private Integer workplaceId;
    private Integer hallId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private User user;
}
