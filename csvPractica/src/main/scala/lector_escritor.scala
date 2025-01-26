import doobie._
import doobie.implicits._

import cats._
import cats.effect._
import cats.implicits._

import cats.effect.unsafe.implicits.global
import java.io.{BufferedWriter, FileWriter}
import com.github.tototoshi.csv.*


import java.io.File

object Escritor:
  
  //@main
  def mainEscritorGenerator(): Unit =
    println("Creando script para una base de datos...")
    val dataSample: List[Map[String, String]] = lectura()
    val filePath = "D:\\yeped\\Escritorio\\MIS-AVANCES-ProyIntegr\\insert_people.sql"
    val file = new BufferedWriter(new FileWriter(filePath))
    //crearTablePelicula()
    try
      dataSample.foreach { row =>
        file.write(insert(row))
        file.newLine()
      }
    finally
      file.close()

  def insert(row: Map[String, String]): String = {
    val original_title = escapeSql(row.get("original_title").get).trim
    val homepage = escapeSql(row.get("homepage").get).trim
    val budget = escapeSql(row.get("budget").get).toLong
    val revenue = escapeSql(row.get("revenue").get).toLong
    val runtime = escapeSql(row.get("runtime").get).toInt
    s"INSERT INTO PELICULA(original_title,homepage , budget,revenue,runtime) VALUES ('$original_title', '$homepage', $budget, $revenue, $runtime);"
  }
  def lectura(): List[Map[String, String]] = {
    implicit object MiFormato extends DefaultCSVFormat:
      override val delimiter: Char = ';'

    val pathMoviesBIG: String = "D:\\yeped\\Escritorio\\small_pi_movies.csv"
    val reader = CSVReader.open(new File(pathMoviesBIG))
    try
      reader.allWithHeaders()
    finally
      reader.close()
  }

def escapeSql(value: String): String = {
  if (value == null || value.trim.isEmpty) "NULL"
  else {
    value
      .replace("\\", "\\\\")  // Escapar barra invertida
      .replace("'", "''")     // Escapar comillas simples (necesario en SQL)
      .replace("\"", "\\\"")  // Escapar comillas dobles
      .replace("%", "\\%")    // Evitar LIKE Injection
      .replace("_", "\\_")    // Evitar LIKE Injection
      .replace("\r", "")      // Remover retorno de carro
      .replace("\n", "")      // Remover saltos de línea
  }
}

def baseDatos(): Unit = {
  val xa = Transactor.fromDriverManager[IO](
    driver = "com.mysql.cj.jdbc.Driver", // JDBC driver
    url = "jdbc:mysql://localhost:3306/practicaprografunc", // URL de conexión
    user = "root", // Nombre de la base de datos
    password = "UTPLpy", // Password
    logHandler = None // Manejo de la información de Log
  )
  crearSchema()
  crearTablePelicula()

}

def crearSchema(): ConnectionIO[Int] = {
  sql"CREATE SCHEMA practicaprografunc COLLATE = utf8_general_ci"
    .update.run
}
def crearTablePelicula(): ConnectionIO[Int] = {
  sql"Create table Pelicula (origintal_tilte varchar(100) ,homepage varchar(100) ,budget int not null,revenue int not null,runtime int not null)"
    .update
    .run
}
