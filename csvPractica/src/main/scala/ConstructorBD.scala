import doobie._
import doobie.implicits._
import cats._
import cats.effect._
import cats.implicits._
import cats.effect.unsafe.implicits.global

object ConstructorBD {
  def construir(): Unit = {
    val xa = Transactor.fromDriverManager[IO](
      driver = "com.mysql.cj.jdbc.Driver", // JDBC driver
      url = "jdbc:mysql://localhost:3306",//      /practicaprografunc", // URL de conexión
      user = "root", // Nombre de la base de datos
      password = "UTPLpy", // Password
      logHandler = None // Manejo de la información de Log
    )
    val x = crear().transact(xa).unsafeRunSync()
    
  }
  
  def crear(): ConnectionIO[Int] =(
      sql"""CREATE SCHEMA IF NOT EXISTS prueba3
         |COLLATE = utf8_general_ci;"""
        .stripMargin.update.run *>
      sql"""USE prueba3;"""
        .stripMargin
        .update.run *>
      sql"""CREATE TABLE collection (
           |	collection_id INTEGER PRIMARY KEY,
           |	nombre VARCHAR(80),
           |	poster_path VARCHAR(100),
           |	backdrop_path VARCHAR(100)
           |)
           |COMMENT = 'Recordables de la película los cuales pueden se recomendados a los Usuarios en caso de que el rating y aceptación de ellos sea elevado ';"""
        .stripMargin.update.run *>
      sql"""CREATE TABLE pelicula (
           |	pelicula_id INTEGER PRIMARY KEY,
           |	imdb_id VARCHAR(20),
           |	adult BOOLEAN,
           |	budget BIGINT,
           |	homepage VARCHAR(100),
           |	original_language CHAR(2),
           |	original_title VARCHAR(70),
           |	overview VARCHAR(1500),
           |	popularity BIGINT,
           |	poster_path VARCHAR(70),
           |	release_date DATE,
           |	revenue BIGINT,
           |	runtime INTEGER,
           |	estado VARCHAR(20),
           |	tagline VARCHAR(200),
           |	title VARCHAR(70),
           |	video BOOLEAN,
           |	vote_count INTEGER,
           |	vote_average INTEGER,
           |	collection_id INTEGER,
           |	FOREIGN KEY (collection_id) REFERENCES collection(collection_id)
           |);"""
        .stripMargin.update.run *>

      sql"""CREATE TABLE genero (
           |    genre_id INTEGER PRIMARY KEY,
           |    nombre VARCHAR(10)
           |)
           |COMMENT = 'Tipo de película escogida para nuestra película, generando una etiqueta para que los usuarios puedan encontrarla fácilmente ';
           |"""
        .stripMargin
        .update.run *>
      sql"""CREATE TABLE pelicula_genero (
           |    genre_id INTEGER,
           |    pelicula_id INTEGER,
           |    PRIMARY KEY (genre_id, pelicula_id),
           |    FOREIGN KEY (genre_id) REFERENCES genero(genre_id),
           |    FOREIGN KEY (pelicula_id) REFERENCES pelicula(pelicula_id)
           |);"""
        .stripMargin
        .update.run *>
      sql"""CREATE TABLE idioma (
           |    iso_639_1_code CHAR(2) PRIMARY KEY,
           |    nombre VARCHAR(30)
           |)
           |COMMENT = 'Diferentes lenguas subtituladas y habladas para la diferente audiencia en todo el mundo para el cuál va dirigida la película ';
           |"""
        .stripMargin
        .update.run *>
      sql"""CREATE TABLE pelicula_idioma (
           |    iso_639_1_code CHAR(2),
           |    pelicula_id INTEGER,
           |    PRIMARY KEY (iso_639_1_code, pelicula_id),
           |    FOREIGN KEY (iso_639_1_code) REFERENCES idioma(iso_639_1_code),
           |    FOREIGN KEY (pelicula_id) REFERENCES pelicula(pelicula_id)
           |)
           |COMMENT = 'Metodo principal del cual se deriban el resto de métodos y sus relaciones. Posee características clave que hacen única a cada película como su ID, su título y su estudio.';
           |"""
        .stripMargin
        .update.run *>
      sql"""CREATE TABLE pais (
           |    iso_3166_1_code CHAR(3) PRIMARY KEY,
           |    nombre VARCHAR(30)
           |);"""
        .stripMargin
        .update.run *>
      sql"""CREATE TABLE pelicula_pais (
           |    iso_3166_1_code CHAR(3),
           |    pelicula_id INTEGER,
           |    PRIMARY KEY (iso_3166_1_code, pelicula_id),
           |    FOREIGN KEY (iso_3166_1_code) REFERENCES pais(iso_3166_1_code),
           |    FOREIGN KEY (pelicula_id) REFERENCES pelicula(pelicula_id)
           |);"""
        .stripMargin
        .update.run *>
      sql"""CREATE TABLE productora (
           |    productora_id INTEGER PRIMARY KEY,
           |    nombre VARCHAR(50)
           |)
           |COMMENT = 'Estudio encargado de rodar, editary publciar la pelicula al publico';
           |"""
        .stripMargin
        .update.run *>
      sql"""CREATE TABLE pelicula_productora (
           |    productora_id INTEGER,
           |    pelicula_id INTEGER,
           |    PRIMARY KEY (productora_id, pelicula_id),
           |    FOREIGN KEY (productora_id) REFERENCES productora(productora_id),
           |    FOREIGN KEY (pelicula_id) REFERENCES pelicula(pelicula_id)
           |);"""
        .stripMargin
        .update.run *>
      sql"""CREATE TABLE empleado (
           |    empleado_id INTEGER PRIMARY KEY,
           |    nombre VARCHAR(50),
           |    gender INTEGER,
           |    profile_path VARCHAR(100)
           |)
           | COMMENT = 'Persona que realiza labores dentro del ámbito de desarrollo de la pelicula ';
           |"""
        .stripMargin
        .update.run *>
      sql"""CREATE TABLE job_depart (
           |    job_id INTEGER PRIMARY KEY AUTO_INCREMENT,
           |    department_id VARCHAR(50),
           |    job_name VARCHAR(50)
           |);"""
        .stripMargin
        .update.run *>
      sql"""CREATE TABLE pelicula_empleado (
           |    empleado_id INTEGER,
           |    pelicula_id INTEGER,
           |    credit_id VARCHAR(50),
           |    job_id INTEGER,
           |    PRIMARY KEY (empleado_id, pelicula_id),
           |    FOREIGN KEY (empleado_id) REFERENCES empleado(empleado_id),
           |    FOREIGN KEY (pelicula_id) REFERENCES pelicula(pelicula_id),
           |    FOREIGN KEY (job_id) REFERENCES job_depart(job_id)
           |);"""
        .stripMargin
        .update.run *>
      sql"""CREATE TABLE actor (
           |    actor_id INTEGER PRIMARY KEY,
           |    nombre VARCHAR(50),
           |    gender INTEGER,
           |    profile_path VARCHAR(100)
           |)
           |COMMENT = 'Persona que interpreta papeles en la pelicula que va dependidendo de la trama, situación y carisma del actor';
           |"""
        .stripMargin
        .update.run *>
      sql"""CREATE TABLE pelicula_actor (
           |	pelicula_actor_id INTEGER PRIMARY KEY AUTO_INCREMENT,
           |    actor_id INTEGER,
           |    pelicula_id INTEGER,
           |    personaje VARCHAR(200),
           |    credit_id VARCHAR(50),
           |    cast_id INTEGER,
           |    orden   INTEGER,
           |    FOREIGN KEY (actor_id) REFERENCES actor(actor_id),
           |    FOREIGN KEY (pelicula_id) REFERENCES pelicula(pelicula_id)
           |);"""
        .stripMargin
        .update.run *>
      sql"""CREATE TABLE key_word (
           |    id_KW INTEGER PRIMARY KEY,
           |    nombre VARCHAR(50)
           |)
           |COMMENT = 'iltradores claves que pueden hacer que la película sea facil de diferenciar al resto a los ojos del usuario';
           |"""
        .stripMargin
        .update.run *>
      sql"""CREATE TABLE pelicula_keyWord (
           |    id_KW INTEGER,
           |    pelicula_id INTEGER,
           |    PRIMARY KEY (id_KW, pelicula_id),
           |    FOREIGN KEY (id_KW) REFERENCES key_word(id_KW),
           |    FOREIGN KEY (pelicula_id) REFERENCES pelicula(pelicula_id)
           |);"""
        .stripMargin
        .update.run *>
      sql"""CREATE TABLE Usuario (
           |    user_id INTEGER PRIMARY KEY
           |)
           |COMMENT = 'Consumidor de la película, seguidor de los atributos de la película como director y su estudio, acredor a reatear la película a su gusto y preferencia ';
           |"""
        .stripMargin
        .update.run *>
      sql"""CREATE TABLE calificacion (
           |    user_id INTEGER,
           |    pelicula_id INTEGER,
           |    rating FLOAT,
           |    timestamps BIGINT,
           |    PRIMARY KEY (user_id, pelicula_id),
           |    FOREIGN KEY (user_id) REFERENCES Usuario(user_id),
           |    FOREIGN KEY (pelicula_id) REFERENCES pelicula(pelicula_id)
           |);"""
        .stripMargin
        .update.run 
  )
}

/*
sql"""USE PELICULAS_PROYECTO_INTEGRADOR;"""
        .stripMargin
        .update.run
      sql"""USE PELICULAS_PROYECTO_INTEGRADOR;"""
        .stripMargin
        .update.run
      sql"""USE PELICULAS_PROYECTO_INTEGRADOR;"""
        .stripMargin
        .update.run
* */
