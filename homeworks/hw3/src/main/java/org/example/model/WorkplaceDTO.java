package org.example.model;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkplaceDTO {

    @Setter
    @Getter
    private Integer id;
    
    @NotBlank(message = "Provide a description")
    private String description;

    public @NotBlank(message = "Provide a description") String getDescription() {
        return description;
    }

    public void setDescription(@NotBlank(message = "Provide a description") String description) {
        this.description = description;
    }
}
