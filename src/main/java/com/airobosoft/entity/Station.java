package com.airobosoft.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="stations")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String code;
    private String name;
    private String city;
    private String state;


}
