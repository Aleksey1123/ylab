package org.example.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** DTO class for user interactions with the booking entity. **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingPostRequest {
    String resourceId;
    String resourceType;
    String startDateTimeString;
    String endDateTimeString;
}
