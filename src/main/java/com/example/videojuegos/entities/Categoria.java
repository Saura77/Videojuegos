package com.example.videojuegos.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "categorias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private boolean activo = true;


    /*
     * Si bien SQL no entiende de relaciones bidireccionales, en POO si podemos identificarlas, por lo
     * que lo aclaramos con la etiqueta mappedBY(relacion YA mapeada) y enviamos el nombre del objeto a
     * través del cual está mapeada la relación (como mi objeto de tipo Categoria en Videojuego se llama
     * categoria, debo colocarle el mismo nombre para que identifique la relación)
     * */
    @OneToMany(mappedBy = "categoria")
    private List<Videojuego> videojuegos;
}
