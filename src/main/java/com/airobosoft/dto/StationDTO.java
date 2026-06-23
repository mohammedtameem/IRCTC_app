package com.airobosoft.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StationDTO {

    private Long id;

    @NotBlank(message ="station code is required")
    @Size(min = 3, max = 10, message = "size must be from 3 to 10 characters")
    private String code;

    private String name;

    private String city;

    private String state;
}