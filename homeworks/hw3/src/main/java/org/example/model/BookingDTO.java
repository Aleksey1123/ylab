package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.User;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {

    private Integer id;
    private Integer workplaceId;
    private Integer hallId;

    @NotBlank(message = "Provide a startTime")
    private LocalDateTime startTime;

    @NotBlank(message = "Provide an endTime")
    private LocalDateTime endTime;

    private User user;
}
