package org.example.lab07_20192434.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "roomseats")
public class Roomseats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "seatNumber")
    private String seatNumber;

    @Column(name = "rowNumber")
    private int rowNumber;

    @Column(name = "isAvailable")
    private boolean isAvailable;

    @ManyToOne
    @JoinColumn(name = "roomId")
    private Rooms room;
}
