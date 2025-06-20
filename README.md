# LiterAlura 📚

Proyecto desarrollado en **Java + Spring Boot**, que consume la API pública **Gutendex** para construir un catálogo de libros y autores, con persistencia de datos en **PostgreSQL** y consultas personalizadas.

## 🚀 Tecnologías utilizadas

- **Java JDK**: 17+
- **Maven**: 4+
- **Spring Boot**: 3.2.3
- **Spring Data JPA**
- **PostgreSQL**: 16+
- **Jackson**: 2.16
- **HttpClient (Java 11+)**

## ⚙️ Configuración del entorno

1. Instalar:
   - [Java JDK 17+](https://www.oracle.com/java/technologies/downloads/)
   - [Maven 4+](https://maven.apache.org/download.cgi)
   - [PostgreSQL 16+](https://www.postgresql.org/download/)

2. Configurar el proyecto en [Spring Initializr](https://start.spring.io/):
   - **Project:** Maven
   - **Language:** Java 17
   - **Packaging:** JAR
   - **Dependencies:** 
     - Spring Data JPA
     - PostgreSQL Driver

## 📝 Descripción del proyecto

### Funcionalidades principales

✅ **Búsqueda de libro por título**  
Consulta la API Gutendex y almacena el primer resultado en la base de datos.

✅ **Listado de libros**  
Muestra todos los libros previamente buscados.

✅ **Listado de libros por idioma**  
Consulta la base de datos para mostrar libros escritos en un idioma específico.

✅ **Listado de autores**  
Muestra los autores relacionados con los libros buscados.

✅ **Listado de autores vivos en un año dado**  
Permite consultar autores que estaban vivos en un año ingresado por el usuario.

✅ **Estadísticas**  
Exhibe la cantidad de libros almacenados por idioma.

### API utilizada

- **Gutendex API**: [https://gutendex.com/books/](https://gutendex.com/books/)
- [Repositorio oficial de la API](https://github.com/garethbjohnson/gutendex)

### Modelo de datos

- **Libro**
  - Título
  - Idioma (solo el primero recibido)
  - Número de descargas
  - Autor (relación muchos a uno)

- **Autor**
  - Nombre
  - Año de nacimiento
  - Año de fallecimiento (puede ser null)

## 🛠️ Cómo ejecutar

1️⃣ Clona el repositorio:
\`\`\`bash
git clone https://github.com/tu-usuario/literalura.git
\`\`\`

2️⃣ Configura la conexión a PostgreSQL en \`application.properties\`:
\`\`\`properties
spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
spring.datasource.username=tu_usuario
spring.datasource.password=tu_password
spring.jpa.hibernate.ddl-auto=update
\`\`\`

3️⃣ Compila y ejecuta:
\`\`\`bash
mvn clean install
mvn spring-boot:run
\`\`\`

4️⃣ Interactúa mediante el menú en consola:
- Buscar libro por título
- Listar libros
- Listar libros por idioma
- Listar autores
- Listar autores vivos en un año
- Mostrar estadísticas por idioma

## 💡 Notas técnicas

- Se usó \`HttpClient\`, \`HttpRequest\` y \`HttpResponse\` para el consumo de la API.
- Mapeo de JSON a objetos con **Jackson** (\`ObjectMapper\`, \`@JsonAlias\`, \`@JsonIgnoreProperties\`).
- Persistencia con **Spring Data JPA**, consultas con **derived queries** y uso de **Java Streams** para estadísticas.
- La relación entre libros y autores es **Muchos a Uno** (varios libros pueden compartir autor).

## 📌 Mejoras futuras

✨ Gestión de errores más robusta (por ejemplo, validaciones de entrada más avanzadas)  
✨ Consultas avanzadas (filtros combinados por idioma, autor, año)  
✨ Exponer un API REST en lugar de un menú en consola
