package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/** DTO class for user interactions with the booking entity. **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private int id;

    @NotBlank(message = "Provide the username")
    private String username;

    @NotBlank(message = "Provide a password")
    private String password;
}
