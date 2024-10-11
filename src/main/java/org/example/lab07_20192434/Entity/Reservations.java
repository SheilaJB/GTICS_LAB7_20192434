package org.example.lab07_20192434.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "reservations")
public class Reservations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "startDatetime")
    private String startDatetime;

    @Column(name = "endDatetime")
    private String endDatetime;

    @ManyToOne
    @JoinColumn(name = "userId")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "funcionId")
    private  Funciones funcion;

    @ManyToOne
    @JoinColumn(name = "roomSeatId")
    private  Roomseats roomSeat;

}
