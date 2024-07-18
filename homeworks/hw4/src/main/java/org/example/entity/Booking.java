package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/** Entity class for storing and creating new bookings. **/
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
