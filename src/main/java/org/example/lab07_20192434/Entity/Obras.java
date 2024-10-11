package org.example.lab07_20192434.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "obras")
public class Obras {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "El título no puede estar vacío")
    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @NotNull(message = "La duración no puede ser nula")
    @Column(name = "duration")
    private int duration;

    @Column(name = "releaseDate")
    private LocalDate releaseDate;


}
