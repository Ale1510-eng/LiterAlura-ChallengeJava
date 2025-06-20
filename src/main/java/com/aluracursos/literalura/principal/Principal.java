package com.aluracursos.literalura.principal;

import com.aluracursos.literalura.model.*;
import com.aluracursos.literalura.repository.AutorRepository;
import com.aluracursos.literalura.repository.LibroRepository;
import com.aluracursos.literalura.service.ConsumoAPI;
import com.aluracursos.literalura.service.ConvierteDatos;
import org.springframework.data.jpa.repository.Query;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    Scanner teclado = new Scanner(System.in);
    private LibroRepository repository;
    private AutorRepository autorRepository;

    public Principal(LibroRepository repository, AutorRepository autorRepository){
        this.repository = repository;
        this.autorRepository = autorRepository;
    }


    public Catalogo obtenerLibros(){
        var json = consumoAPI.obtenerDatos("https://gutendex.com/books/?languages=es");
        var datos = conversor.obtenerDatos(json, Catalogo.class);
        return datos;
        //System.out.println(datos);
    }

    public void mostrarMenu(){
        var opcion = 1;
        while(opcion != 0){
            var menu = """
                    1. Obtener libros por titulo
                    2. Mostrar catalogo
                    3. Buscar Libro por título
                    4. Buscar por idioma
                    5. Cantidad de libros por idioma
                    6. Listar autores
                    7. Listar autores por año en que vivieron
                    0. Salir
                    """;
            System.out.println(menu);
            try {
                opcion = teclado.nextInt();
                teclado.nextLine();
            } catch (InputMismatchException e){
                System.out.println("Opción no válida");
                teclado.nextLine();
                continue;
            }

            switch (opcion){
                case 1:
                    buscarDatosLibro();
                    break;

                case 2:
                    mostrarLibros();
                    break;

                case 3:
                    buscarLibroPorTitulo();
                    break;

                case 4:
                    buscarPorIdioma();
                    break;

                case 5:
                    listarCantidadDeLibrosPorIdioma();
                    break;

                case 6:
                    listarAutores();
                    break;

                case 7:
                    listarAutoresPorAnio();
                    break;

                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;

                default:
                    System.out.println("Opción no valida");
                    break;
            }
        }
    }

    private void buscarDatosLibro(){
        System.out.println("Titulo del libro");
        var titulo = teclado.nextLine();
        Optional<DatosLibro> libroBuscado = obtenerLibros().libros().stream()
                .filter(l->l.titulo().toLowerCase().contains(titulo.toLowerCase()))
                .findFirst();
        if (libroBuscado.isPresent()) {

            if(repository.findByTituloContainsIgnoreCase(libroBuscado.get().titulo()).isPresent()){
                System.out.println("El libro ya está en la base de datos");
            } else{
                var libro = new Libro(libroBuscado.get());
                System.out.println(libro);
                var autorBuscado = libroBuscado.get().datosAutor().get(0);
                var autorEncontrado = autorRepository.findByNombreIgnoreCase(autorBuscado.nombre());
                Autor autor;
                if(autorEncontrado.isPresent()){
                    System.out.println("El autor ya existe en la base de datos");
                    autor = autorEncontrado.get();
                } else{
                    autor = new Autor(autorBuscado);
                    autorRepository.save(autor);
                }
                libro.setAutor(autor);
                repository.save(libro);
            }
        } else{
            System.out.println("Libro no encontrado");
        }

    }


    private void mostrarLibros() {
        repository.findAll().stream().forEach(System.out::println);
    }


    private void buscarLibroPorTitulo() {
        Catalogo datos = obtenerLibros();
        System.out.println("Escribe el titulo del libro que deseas buscar: ");
        var titulo = teclado.nextLine();
        var libroBuscado = repository.findByTituloContainsIgnoreCase(titulo);

        if(libroBuscado.isPresent()){
            System.out.println("El libro buscado es: " + libroBuscado.get());
        } else {
            System.out.println("Libro no encontrado");
        }
    }

    private void buscarPorIdioma(){
        System.out.println("Escribe el idioma a buscar: ");
        var idiomaBuscado = teclado.nextLine();
        var idioma = Idioma.fromEspanol(idiomaBuscado);
        System.out.println(idioma);
        List<Libro> librosPorIdioma = repository.findByIdioma(idioma);
        System.out.println("Los libros en " + idiomaBuscado + " son: ");
        librosPorIdioma.forEach(System.out::println);

    }

    private void listarCantidadDeLibrosPorIdioma(){
        var idiomaEspanol = Idioma.fromEspanol("espanol");
        var idiomaIngles = Idioma.fromEspanol("ingles");
        var listaLibrosIngles = repository.findByIdioma(idiomaIngles);
        var listaLibrosEspaniol = repository.findByIdioma(idiomaEspanol);
        var cantidadIngles = listaLibrosIngles.stream().count();
        var cantidadEspanol = listaLibrosEspaniol.stream().count();
        System.out.println("Hay " + cantidadIngles + " Libros en inglés registrados"
        + "\nHay " + cantidadEspanol + " Libros en español registrados");
    }

    private void listarAutores(){
        autorRepository.findAll().forEach(System.out::println);
    }

    private void listarAutoresPorAnio() {
        System.out.println("Escribe el año en el que vivió el autor: ");
        var anioConsultado = teclado.nextInt();
        teclado.nextLine();
        var autores = autorRepository.listarAutoresPorAnio(anioConsultado);
        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores");
        } else {
            autores.forEach(System.out::println);
        }
    }

}
