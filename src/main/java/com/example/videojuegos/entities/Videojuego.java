package com.example.videojuegos.entities;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;


@Entity
@Table(name = "videojuegos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Videojuego {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Se traduce en SQL como autoincremental y no nulo
    private long id;

    @NotEmpty(message = "{NotEmpty.Videojuego.titulo}")
    private String titulo;

    @Size(min = 5, max = 100, message = "Descripcion debe ser entre 5 y 100 caracteres")
    private String descripcion;

    private String imagen;

    @Min(value = 5, message = "El precio debe ser minimo 5")
    @Max(value = 1000, message = "El precio debe ser menor a 1000")
    private float precio;

    @Min(value = 1, message = "El stock debe ser minimo 5")
    @Max(value = 10000, message = "El stock debe ser menor a 1000")
    private short stock;

    @PastOrPresent(message = "Debe ser igual o menor a la fecha de hoy")
    @NotNull(message = "No puede ser nulo la fecha")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaLanzamiento;

    private boolean activo = true; //REALIZAR BAJA LÓGICA, MANTENER HISTÓRICO

    @NotNull(message = "Es requerido el estudio")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_estudio", nullable = false) //No permite que sea nulo el valor
    private Estudio estudio;

    @NotNull(message = "Es requerida la categoria")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_categoria", nullable = false)
    private Categoria categoria;
}
