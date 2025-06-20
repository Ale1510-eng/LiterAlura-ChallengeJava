package com.aluracursos.literalura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    private String datosAutor;
    @Enumerated(EnumType.STRING)
    private Idioma idioma;
    private Double descargas;
    @Column(length = 10000)
    private String sinopsis;
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Autor autor;

    public Libro(){}

    public Libro(DatosLibro datosLibro) {
        this.titulo = datosLibro.titulo();
        this.datosAutor = datosLibro.datosAutor().stream()
                .map(a->a.nombre())
                .findFirst().get();
        this.idioma = Idioma.fromString(datosLibro
                .idiomas().get(0));
        this.descargas = datosLibro.descargas();
        this.sinopsis = datosLibro.sinopsis().get(0);
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDatosAutor() {
        return datosAutor;
    }

    public void setDatosAutor(String datosAutor) {
        this.datosAutor = datosAutor;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public Double getDescargas() {
        return descargas;
    }

    public void setDescargas(Double descargas) {
        this.descargas = descargas;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "{" +
                "titulo='" + titulo  +
                ", datosAutor=" + datosAutor +
                ", idioma=" + idioma +
                ", descargas=" + descargas +
                ", sinopsis=" + sinopsis +
                '}';
    }
}
