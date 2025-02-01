
import LectorCSV.lectura
import Limpiador.{borrarDatosVacios, eliminarRepetidos, numerosNegativos, simplificarNumeros, trimeador}
import play.api.libs.json.*

import scala.util.*


object ParseadorJSONS {
  def corregirLLaves(json: String): String = {
    if (json.startsWith("[") && !json.endsWith("]")) return json + "]"
    if (json.endsWith("]") && !json.startsWith("[")) return "[" + json
    if (json.startsWith("{") && !json.endsWith("}")) return json + "}"
    if (json.endsWith("}") && !json.startsWith("{")) return "{" + json
    json
  }

  def collectionLista(dataMap: List[Map[String,String]] ):  List[(Int, String, String, String)] = {
    val collections: List[String] = dataMap
      .flatMap(row => row.get("belongs_to_collection"))
      .filterNot(x => x.isEmpty)
      .map(fila => fila.replaceAll("'", "\""))
      .map(corregirLLaves)
      .map(_.replaceAll("None", "null"))
      .map(x => if !x.endsWith("\"}") then x.appended('"').appended('}') else x)
      .map(x =>
        if x.charAt(x.length - 2) == 'l' then
          x
        else if x.charAt(x.length - 2) != '"' then
          x.dropRight(1).appended('"').appended('}') else x) //muy especifico


    val tup_colection = collections
      .map(x => Try(Json.parse(x)))

    val colBien = tup_colection.filter(_.isSuccess).map(_.get)
    
    val tupla: List[(Int, String, String, String)] = colBien
      .map(x=> 
        (x("id").as[Int],
        x("name").asOpt[String].getOrElse("null"),
        x("poster_path").asOpt[String].getOrElse("null"),
        x("backdrop_path").asOpt[String].getOrElse("null")))
    
    tupla
  }

  def genresLista(dataMap: List[Map[String, String]]): List[(Int, String)]  = {
    def toTupla(data: JsValue): List[(Int, String)] = {
      val result = Try(data.as[JsArray])
        .toEither.left.map(error => (error, data))
      result match
        case Left(_, jsvalue) =>
          val jsObj = jsvalue.as[JsObject].value
          List((jsObj("id").as[Int],
            jsObj("name").as[String]))


        case Right(jsvalue) => jsvalue.value
          .map(_.as[JsObject])
          .map(jsObj =>
            (jsObj("id").as[Int],
              jsObj("name").as[String]))
          .toList
    }
    
    val genres: List[String] = dataMap
      .flatMap(row => row.get("genres"))
      .filterNot(x => x.isEmpty)
      .map(fila => fila.replaceAll("'", "\""))
      .map(corregirLLaves)
      .map(x => if !x.endsWith("\"}]") && x.startsWith("[{") then x.dropRight(2).appended('"').appended('}').appended(']') else x)
      .map(x => if x.charAt(x.length - 2) == ',' then x.dropRight(2).appended(']') else x)
      .map(x => if x.charAt(x.length - 2) == '"' then x.dropRight(2).appended(']') else x)


    val tup_genres = genres
      .map(x => Try(Json.parse(x)))
      .filter(_.isSuccess).map(_.get)
      .flatMap(toTupla)
    
    tup_genres
  }

  def companiesLista(dataMap: List[Map[String, String]]): List[(Int, String)] = {
    def toTupla(data: JsValue): List[(Int, String)] = {
      val result = Try(data.as[JsArray])
        .toEither.left.map(error => (error, data))
      result match
        case Left(_, jsvalue) =>
          val jsObj = jsvalue.as[JsObject].value
          List((jsObj("id").as[Int],
            jsObj("name").as[String]))


        case Right(jsvalue) => jsvalue.value
          .map(_.as[JsObject])
          .map(jsObj =>
            (jsObj("id").as[Int],
              jsObj("name").as[String]))
          .toList
    }

    val genres: List[String] = dataMap
      .flatMap(row => row.get("production_companies"))
      .filterNot(x => x.isEmpty)
      .map(fila => fila.replaceAll("'", "\""))
      .map(corregirLLaves)
      .map(x => if !x.endsWith("\"}]") && x.startsWith("[{") then x.dropRight(2).appended('"').appended('}').appended(']') else x)
      .map(x => if x.charAt(x.length - 2) == ',' then x.dropRight(2).appended(']') else x)
      .map(x => if x.charAt(x.length - 2) == '"' then x.dropRight(2).appended(']') else x)


    val tup_genres = genres
      .map(x => Try(Json.parse(x)))
      .filter(_.isSuccess).map(_.get)
      .flatMap(toTupla)

    tup_genres
  }
}
