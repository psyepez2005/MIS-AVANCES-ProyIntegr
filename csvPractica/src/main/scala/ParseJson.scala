

import scala.collection.immutable.Map


import play.api.libs.json._


object ParseJson {
  def cleanJsonLista(json: String): String = {
    try {

      //REALIZAR LAS TRANSFORMACIONES NECESARIAS
      val cleanedJson = json
        .replaceAll("'", "\"") // Cambia comillas simples por dobles
        .replaceAll("None", "null") // Cambia None por null
        .replaceAll("True", "true") // Normalizar booleano
        .replaceAll("False", "false") // Normalizar booleano
        .replaceAll("\\\\", "") // Elimina barras invertidas dobles
        .replaceAll("\\s*:\\s*", ":") // Elimina espacios alrededor de los dos puntos
        .replaceAll("\\s*,\\s*", ",") // Elimina espacios alrededor de las comas
        .replaceAll("\\s*\\{\\s*", "{") // Elimina espacios después de llaves de apertura
        .replaceAll("\\s*\\}\\s*", "}") // Elimina espacios antes de llaves de cierre
        .replaceAll("\\s*\\[\\s*", "[") // Elimina espacios después de corchetes de apertura
        .replaceAll("\\s*\\]\\s*", "]") // Elimina espacios antes de corchetes de cierre
        .replaceAll("\r?\n", "") // Elimina saltos de línea

      // Intentar parsear para validar el JSON
      val parsedJson = Json.parse(cleanedJson)
      Json.stringify(parsedJson) // Devuelve el JSON como String validado
    } catch {
      case _: Exception =>
        "{}"
    }
  }

  def limpiadorJsons(dataMap: List[Map[String, String]], columna: String): List[Map[String, String]] = {
    def cleanJsonUnico(json: String): String = {
      try {
        val cleanedJson = json
          .replaceAll("'", "\"") // Cambia comillas simples por dobles
          .replaceAll("NULL", "{}") // Cambia None por null
          .replaceAll("\\\\", "") // Elimina barras invertidas dobles
          .replaceAll("\\s*:\\s*", ":") // Elimina espacios alrededor de los dos puntos
          .replaceAll("\\s*,\\s*", ",") // Elimina espacios alrededor de las comas
          .replaceAll("\\s*\\{\\s*", "{") // Elimina espacios después de llaves de apertura
          .replaceAll("\\s*\\}\\s*", "}") // Elimina espacios antes de llaves de cierre
          .replaceAll("\r?\n", "") // Elimina saltos de línea

        //Intentar parsear para validar el JSON
        val parsedJson = Json.parse(cleanedJson)
        Json.stringify(parsedJson) // Devuelve el JSON como String validado
        cleanedJson
      } catch {
        case _: Exception =>
          "{}"
      }
    }

    val dataLimpia = dataMap.map(mapa => mapa.map((llave, valor) => (llave, cleanJsonUnico(valor))))

    dataLimpia
  }

/*
  def jsonToMap(jsonString: String): Map[String, String] = {
    val parsedJson = parse(jsonString)

    parsedJson match {
      case Right(json) =>
        // Convierte el JSON a un mapa de String, String
        json.asObject match {
          case Some(jsonObject) =>
            jsonObject.toMap.collect {
              case (key, value) if value.isString => key -> value.asString.getOrElse("")
            }
          case None => Map.empty[String, String]
        }

      case Left(error) =>
        println(s"Error al parsear el JSON: $error")
        Map.empty[String, String] // Retorna un mapa vacío en caso de error
    }
*/

}
