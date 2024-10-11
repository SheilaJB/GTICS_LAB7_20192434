package org.example.lab07_20192434.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "funciones")
public class Funciones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "funcionDate")
    private LocalDate funcionDate;
    @Column(name = "availableSeats")
    private int availableSeats;


    //Many to one
    @ManyToOne
    @JoinColumn(name = "obraId")
    private  Obras obra;
    @ManyToOne
    @JoinColumn(name = "roomId")
    private  Rooms room;


}
