package com.U2A5SB.CasinoRoyale.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "jugador")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Jugador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private Double saldo;

    @Column(nullable = false)
    private Boolean activo;
}