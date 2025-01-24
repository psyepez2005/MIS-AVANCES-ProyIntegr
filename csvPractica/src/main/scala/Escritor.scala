import java.io.{BufferedWriter, FileWriter}
import Limpiador.escapeMySQL
object Scripter

//@main 
def mainScriptGenerator(): Unit =
  printf("Creando script para una base de datos")
  val dataSample: List[Map[String, String]] = List(
    Map("name" -> "O'Reilly", "age" -> "25", "bio" -> "Líder del mercado 100%.\nNueva línea aquí."),
    Map("name" -> "Alice \"La Genial\"", "age" -> "30", "bio" -> "Dice: \"¡Hola mundo!\""),
    Map("name" -> "Bob \\ El Hacker", "age" -> "35", "bio" -> "Ruta: C:\\Usuarios\\Bob"),
    Map("name" -> "Charlie & Co.", "age" -> "40", "bio" -> "Us @,a símbolos: %, _, $, #, &"),
    Map("name" -> "Dana", "age" -> "28", "bio" -> "Texto con tabulación:\tY un EOF\u001A")
  )



  val filePath = "D:\\yeped\\Escritorio\\MIS-AVANCES-ProyIntegr\\insert_people.sql"

  def generateInsert(row: Map[String, String]): String =
    val name = escapeMySQL(row.get("name").get)
    val age = row.get("age").get.toInt
    val bio = escapeMySQL(row.get("bio").get)
    s"INSERT INTO PERSON(name, age, bio) VALUES ('$name', $age, $bio);"

  var file = new BufferedWriter(new FileWriter(filePath))
  dataSample.foreach { row =>
    file.write(generateInsert(row))
    file.newLine()
  }
  println(s"Archivo $filePath generado correctamente.")
  file.close()

