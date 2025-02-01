import java.io.{BufferedWriter, FileWriter}
import caseClassesJson.*

object PobladorDB {
  val filePath = "D:\\yeped\\Escritorio\\sqlspracticas\\insert_crew.sql"

  def escapeSQL(valor: String): String = {
    if valor.isEmpty then "null"
    else
      valor.replaceAll("'", "''") // Escapar comillas simples
      .replaceAll("\"", "\\\"") // Escapar comillas dobles
      //.replaceAll("\\", "\\\\") // Escapar barra invertida
      .replaceAll("\b", "\\b") // Escapar backspace
      .replaceAll("\n", "\\n") // Escapar nueva línea
      .replaceAll("\r", "\\r") // Escapar retorno de carro
      .replaceAll("\t", "\\t") // Escapar tabulación
      .replaceAll("\u0000", "") // Eliminar caracteres nulos
      .replaceAll("\u001a", "\\Z") // Escapar fin de archivo
  }


  def insertarCollection(lista: List[belongs_to_collection]): Unit = {
    def insertar(collection: belongs_to_collection): String = {
      val collection_id = escapeSQL(collection.id.toString)
      val name = escapeSQL(collection.name)
      val poster_path = escapeSQL(collection.poster_path)
      val backdrop_path = escapeSQL(collection.backdrop_path)
      s"INSERT INTO PERSON(collection_id, name, poster_path,backdrop_path) VALUES ($collection_id,'$name','$poster_path','$backdrop_path');"
    }

    val file = new BufferedWriter(new FileWriter(filePath))
    lista.foreach { fila =>
      file.write(insertar(fila))
      file.newLine()
    }
    file.close()
  }

  def insertarCrew(lista: List[crew]): Unit = {
    def insertar(actor: crew): String = {
      val actor_id = escapeSQL(actor.id.toString)
      val name = escapeSQL(actor.name)
      val gender = escapeSQL(actor.gender.toString)
      val profile_path = escapeSQL(actor.profile_path)
      s"INSERT INTO PERSON(collection_id, name, poster_path,backdrop_path) VALUES ($actor_id,'$name',$gender,'$profile_path');"
    }

    val file = new BufferedWriter(new FileWriter(filePath))
    lista.foreach { fila =>
      file.write(insertar(fila))
      file.newLine()
    }

  }
  /*
  def collection(lista: List[belongs_to_collection]): String = {
    val name = escapeMySQL(row.get("name").get)
    val age = row.get("age").get.toInt
    val bio = escapeMySQL(row.get("bio").get)
    s"INSERT INTO PERSON(name, age, bio) VALUES ('$name', $age, $bio);"
  }  

  var file = new BufferedWriter(new FileWriter(filePath))
  lista.foreach { row =>
    file.write(generateInsert(row))
    file.newLine()
  }

  def generateInsertmapas(row: Map[String, String]): String =
    val name = escapeMySQL(row.get("name").get)
    val age = row.get("age").get.toInt
    val bio = escapeMySQL(row.get("bio").get)
    s"INSERT INTO PERSON(name, age, bio) VALUES ('$name', $age, $bio);"

  var file = new BufferedWriter(new FileWriter(filePath))
  dataSample.foreach { row =>
    file.write(generateInsert(row))
    file.newLine()
  }

   */
}