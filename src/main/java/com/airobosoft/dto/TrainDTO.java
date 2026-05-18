package com.airobosoft.dto;

import com.airobosoft.annotation.ValidCoach;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrainDTO {

    private Long id;

    @NotBlank(message = "Train number cannot be blank")
    @Pattern(
            regexp = "^[0-9]{5}$",
            message = "Train number must contain exactly 5 digits"
    )
    private String trainNo;

    @NotBlank(message = "Train name cannot be empty")
    @Size(
            min = 3,
            max = 50,
            message = "Train name must be between 3 and 50 characters"
    )
    private String name;

    @ValidCoach
    private Integer coaches;
}