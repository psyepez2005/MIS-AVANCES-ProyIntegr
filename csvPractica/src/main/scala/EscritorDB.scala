import caseClassesJson.*
import play.api.libs.json._
import Limpiador.{cleanJsonUnico,cleanJsonLista}


object json_mal_formado extends App {
  // JSON mal estructurado como String
  val jsonSucio = """
  {'id': 108804, 'name': 'Outpost Collection', 'poster_path': '/xiAWTgzyb1TxW8kN05qiVKNJFxb.jpg', 'backdrop_path': '/7glOZjnZWYYcr67pvz3mSIYIBOC.jpg'}
  """

  val jsonSucio2 = """[{'id': 18, 'name': 'Drama'}, {'id': 28, 'name': 'Action'}]"""

  
  implicit val collectionFormat : play.api.libs.json.OFormat[belongs_to_collection] = Json.format[belongs_to_collection]
  // Limpiamos y parseamos el JSON
  val jsonLimpio = cleanJsonUnico(jsonSucio2)
  val json = Json.parse(jsonLimpio)
  println(json)
  //println(json.)
  // Extraemos la lista de crews
  val collections = (json \ "collections").as[List[belongs_to_collection]]

  // Mostramos los resultados
  println("collections procesados:")
  collections.foreach { colec =>
    println("\nCollection:")
    println(s"Nombre: ${colec.name}")
    println(s"Id: ${colec.id}")
    println(s"Poster path: ${colec.poster_path}")
    println(s"BackDropDrop: ${colec.backdrop_drop}")
  }

  // Mostramos el JSON limpio
  println("\nJSON limpio generado:")
  println(Json.prettyPrint(Json.toJson(Map("collections" -> collections))))
 
}