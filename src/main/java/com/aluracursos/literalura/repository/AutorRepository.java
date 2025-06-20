package com.aluracursos.literalura.repository;

import com.aluracursos.literalura.model.Autor;
import com.aluracursos.literalura.model.DatosAutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository <Autor, Long> {
    Optional<Autor>findByNombreIgnoreCase(String nombre);
    Autor findAutorByNombreIgnoreCase(String nombre);

    @Query("SELECT a FROM Autor a " + "WHERE a.anioNacimiento <= :anioConsultado " +
            "AND (a.anioMuerte >= :anioConsultado OR a.anioMuerte IS NULL)")
    List<Autor> listarAutoresPorAnio(int anioConsultado);

}
