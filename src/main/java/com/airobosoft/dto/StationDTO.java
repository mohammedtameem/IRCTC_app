package com.airobosoft.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StationDTO {

    private Long id;

    private String code;

    private String name;

    private String city;

    private String state;
}