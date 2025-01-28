import doobie._
import doobie.implicits._
import cats._
import cats.effect._
import cats.implicits._
import cats.effect.unsafe.implicits.global


case class Actor(id: Int, name: String, lastname: String)
case class Film(id: Int, title: String, releaseYear: Int, actorList: String)
case class FilmData(id: Int, title: String, releaseYear: Int, actorList: List[Actor], language: String)

object BaseDatos {

  //@main
  def main(): Unit = {
    val xa = Transactor.fromDriverManager[IO](
      driver = "com.mysql.cj.jdbc.Driver", // JDBC driver
      url = "jdbc:mysql://localhost:3306/practicaprografunc", // URL de conexión
      user = "root", // Nombre de la base de datos
      password = "UTPLpy", // Password
      logHandler = None // Manejo de la información de Log
    )
    /*
    val result: Option[Actor] = find(2)
      .transact(xa)
      .unsafeRunSync()
    println("consulta de 1 y solo 1" + result.get)
    println("consulta de varuis" + result.get)
    */

    //CUALQUIER METODO QUE DEVUELVA UN ConnectionIO TIENE QUE TENER .transact(xa).unsafeRunSync()
    // UPDATE============================================

    //crearSchema().transact(xa).unsafeRunSync()
    //usarSchema().transact(xa).unsafeRunSync()
    /*
    crear().transact(xa).unsafeRunSync()
    insertar().transact(xa).unsafeRunSync()
    insertar().transact(xa).unsafeRunSync()
    insertar().transact(xa).unsafeRunSync()
    insertar().transact(xa).unsafeRunSync()

     */

    modificar().transact(xa).unsafeRunSync()
    //eliminarSchema().transact(xa).unsafeRunSync()

    println("EDAD MENOR " + edadMenor().transact(xa).unsafeRunSync())
    println("EDAD MAYOR " + edadMayor().transact(xa).unsafeRunSync())
    println("EDAD PROMEDIO " + edadPromedio().transact(xa).unsafeRunSync())

  }

  def edadPromedio(): ConnectionIO[Option[Double]] =
      sql"SELECT AVG(edad) FROM usuarios"
      .query[Double]
      .option

  def edadMenor(): ConnectionIO[Option[Int]] =
    sql"SELECT MIN(edad) FROM usuarios"
      .query[Int]
      .option

  def edadMayor(): ConnectionIO[Option[Int]] =
    sql"SELECT MAX(edad) FROM usuarios"
      .query[Int]
      .option

  def crearSchema(): ConnectionIO[Int] =
    sql"CREATE SCHEMA practicaprografunc COLLATE = utf8_general_ci"
      .update.run

  def usarSchema(): ConnectionIO[Int] =
    sql"USE practicaprografunc"
      .update.run

  def modificar(): ConnectionIO[Int] =
    sql"""
         UPDATE usuarios SET nombre = 'JOSE', edad = 25 WHERE id = 2;

      """
      .update.run

  def eliminarSchema(): ConnectionIO[Int] =
    sql"DROP SCHEMA IF EXISTS practicaprografunc"
      .update.run

  def insertar(): ConnectionIO[Int] =
    sql"INSERT INTO usuarios(nombre, edad) VALUES('PEPE',19)"
      .update.run

  def crear(): ConnectionIO[Int] =
    sql"CREATE TABLE usuarios (id INT AUTO_INCREMENT PRIMARY KEY,nombre VARCHAR(100) NOT NULL,edad INT NOT NULL)"
      .update.run



  def find(id: Int): ConnectionIO[Option[Actor]] =
    sql"SELECT a.actor_id, a.first_name, a.last_name FROM actor a where a.actor_id = $id"
      .query[Actor]
      .option

  def listAllActors(): ConnectionIO[List[Actor]] =
    sql"SELECT a.actor_id, a.first_name, a.last_name FROM actor a"
      .query[Actor]
      .to[List]

}
