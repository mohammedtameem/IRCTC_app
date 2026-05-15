package com.airobosoft.entity;

import com.airobosoft.annotation.ValidCoach;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record Train(


        @NotBlank(message = "Train no cannot be blank must have 5 values")
        @Pattern(
                regexp = "^[0-9]{5}$",
                message = "Train number must contain exactly 5 digits")
        String trainNo,

        @NotBlank(message = "Train name cannot be empty")
        @Size(
                min = 3,
                max = 50,
                message = "Train name must be between 3 and 50 characters")
        String name,

        @ValidCoach
        int coaches
) {
}