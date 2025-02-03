
import LectorCSV.lectura
import Limpiador.{borrarDatosVacios, eliminarRepetidos, numerosNegativos, simplificarNumeros, trimeador}
import play.api.libs.json.*

import scala.util.*


object ParseadorJSONS {



  def limpiadorTRISTE(dataMap: List[Map[String, String]], columnas: List[String]):  List[Map[String,String]] = {
    val dataLimpia = dataMap.map { mapa =>
      mapa.map((llave, valor) =>
        if columnas.contains(llave) then
          val json = cleanJsonLista(valor)
          //val parse = Json.parse(json)
          (llave, json)
        else
          (llave, valor)
      )
    }
    dataLimpia
  }


  def corregirLLaves(json: String): String = {
    if (json.startsWith("[") && !json.endsWith("]")) return json + "]"
    if (json.endsWith("]") && !json.startsWith("[")) return "[" + json
    if (json.startsWith("{") && !json.endsWith("}")) return json + "}"
    if (json.endsWith("}") && !json.startsWith("{")) return "{" + json
    json
  }

  def eliminarIncompletos(json: String): String = {
    if (json.contains('{')&&json.contains('[')) {
      val ultimo = json.lastIndexOf('}')
      val arreglado = json.substring(0, ultimo).appended('}').appended(']')
      arreglado
    }
    else
      json
  }

  def limpiarJsons(dataMap: List[Map[String, String]], columna: String): List[JsValue] = {
    val listaAislada: List[JsValue] = dataMap
      .flatMap(row => row.get(columna))
      .filterNot(x => x.isEmpty)
      .map(fila => fila.replaceAll("'", "\""))
      .map(corregirLLaves)
      .map(eliminarIncompletos)
      .map(_.replaceAll("None", "null"))
      .map(x => Try(Json.parse(x)))
      .filter(_.isSuccess).map(_.get)

    listaAislada
  }
  def cleanJsonLista(json: String): String = {
    if json.isEmpty then "[]"
    else
      try {
        val cleanedJson = json
          .replaceAll("'", "\"") // Cambia comillas simples por dobles
          .replaceAll("None", "null") // Cambia None por null
          .replaceAll("\\\\", "") // Elimina barras invertidas dobles
          .replaceAll("\r?\n", "") // Elimina saltos de línea
        val clean2 = eliminarIncompletos(corregirLLaves(cleanedJson))
        // Intentar parsear para validar el JSON
        val parsedJson = Json.parse(cleanedJson)
        val retornoJSon = Json.stringify(parsedJson) // Devuelve el JSON como String validado
        retornoJSon
      } catch {
        case _: Exception =>
          "[]"
      }
  }


  def Parse1Json(dataMap: List[Map[String, String]], columna: String): List[JsValue] = {

    val listaAislada: List[JsValue] = dataMap
      .flatMap(row => row.get(columna))
      .filterNot(x => x.isEmpty)
      .map(fila => fila.replaceAll("'", "\""))
      .map(corregirLLaves)
      .map(eliminarIncompletos)
      .map(_.replaceAll("None", "null"))
      .map(x => Try(Json.parse(x)))
      .filter(_.isSuccess).map(_.get)

    listaAislada
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

  def genresLista(dataMap: List[Map[String, String]], columna: String): List[(Int, String)]  ={


    def toTupla(data: JsValue): List[(Int, String)] = {
      val result = Try(data.as[JsArray])
        .toEither.left.map(error => (error, data))
      result match
        case Left(_, jsvalue) =>
          val jsObj = jsvalue.as[JsObject].value
          List((jsObj("id").as[Int],
            jsObj("name").asOpt[String].getOrElse("null")))


        case Right(jsvalue) => jsvalue.value
          .map(_.as[JsObject])
          .map(jsObj =>
            (jsObj("id").as[Int],
              jsObj("name").asOpt[String].getOrElse("null")))
          .toList
    }

    val genres = limpiarJsons(dataMap, "genres").flatMap(toTupla)
 
    genres
  }

  def companiesLista(dataMap: List[Map[String, String]]): List[(String, Int)] = {
    def toTupla(data: JsValue): List[(String, Int)] = {
      val result = Try(data.as[JsArray])
        .toEither.left.map(error => (error, data))
      result match
        case Left(_, jsvalue) =>
          val jsObj = jsvalue.as[JsObject].value
          List((jsObj("name").asOpt[String].getOrElse("null"),
              jsObj("id").as[Int]))


        case Right(jsvalue) => jsvalue.value
          .map(_.as[JsObject])
          .map(jsObj =>
            (jsObj("name").asOpt[String].getOrElse("null"),
              jsObj("id").as[Int]))
          .toList
    }
    val companies = limpiarJsons(dataMap, "production_companies").flatMap(toTupla)

    companies
  }

  def countriesLista(dataMap: List[Map[String, String]]): List[(String, String)] = {
    def toTupla(data: JsValue): List[(String, String)] = {
      val result = Try(data.as[JsArray])
        .toEither.left.map(error => (error, data))
      result match
        case Left(_, jsvalue) =>
          val jsObj = jsvalue.as[JsObject].value
          List((jsObj("iso_3166_1").asOpt[String].getOrElse("null"),
            jsObj("name").asOpt[String].getOrElse("null")))


        case Right(jsvalue) => jsvalue.value
          .map(_.as[JsObject])
          .map(jsObj =>
            (jsObj("iso_3166_1").asOpt[String].getOrElse("null"),
              jsObj("name").asOpt[String].getOrElse("null")))
          .toList
    }
    val countries = limpiarJsons(dataMap, "production_countries").flatMap(toTupla)

    countries
  }

  def languagesLista(dataMap: List[Map[String, String]]): List[(String, String)] = {
    def toTupla(data: JsValue): List[(String, String)] = {
      val result = Try(data.as[JsArray])
        .toEither.left.map(error => (error, data))
      result match
        case Left(_, jsvalue) =>
          val jsObj = jsvalue.as[JsObject].value
          List((jsObj("iso_639_1").asOpt[String].getOrElse("null"),
            jsObj("name").asOpt[String].getOrElse("null")))


        case Right(jsvalue) => jsvalue.value
          .map(_.as[JsObject])
          .map(jsObj =>
            (jsObj("iso_639_1").asOpt[String].getOrElse("null"),
              jsObj("name").asOpt[String].getOrElse("null")))
          .toList
    }

    val languages = limpiarJsons(dataMap, "spoken_languages").flatMap(toTupla)

    languages
  }

  def keywordsLista(dataMap: List[Map[String, String]]): List[(Int, String)] = {
    def toTupla(data: JsValue): List[(Int, String)] = {
      val result = Try(data.as[JsArray])
        .toEither.left.map(error => (error, data))
      result match
        case Left(_, jsvalue) =>
          val jsObj = jsvalue.as[JsObject].value
          List((jsObj("id").as[Int],
            jsObj("name").asOpt[String].getOrElse("null")))


        case Right(jsvalue) => jsvalue.value
          .map(_.as[JsObject])
          .map(jsObj =>
            (jsObj("id").as[Int],
              jsObj("name").asOpt[String].getOrElse("null")))
          .toList
    }

    val keywords = limpiarJsons(dataMap, "keywords").flatMap(toTupla)

    keywords
  }

  def castLista(dataMap: List[Map[String, String]]): List[(Int, String, String, Int, Int, String, Int, String)] = {
    def toTupla(data: JsValue): List[(Int, String, String, Int, Int, String, Int, String)] = {
      val result = Try(data.as[JsArray])
        .toEither.left.map(error => (error, data))
      result match
        case Left(_, jsvalue) =>
          val jsObj = jsvalue.as[JsObject].value
          List((jsObj("cast_id").as[Int],
            jsObj("character").asOpt[String].getOrElse("null"),
            jsObj("credit_id").asOpt[String].getOrElse("null"),
            jsObj("gender").asOpt[Int].getOrElse(0),
            jsObj("id").as[Int],
            jsObj("name").asOpt[String].getOrElse("null"),
            jsObj("order").as[Int],
            jsObj("profile_path").asOpt[String].getOrElse("null")))


        case Right(jsvalue) => jsvalue.value
          .map(_.as[JsObject])
          .map(jsObj =>
            (jsObj("cast_id").as[Int],
              jsObj("character").asOpt[String].getOrElse("null"),
              jsObj("credit_id").asOpt[String].getOrElse("null"),
              jsObj("gender").asOpt[Int].getOrElse(0),
              jsObj("id").asOpt[Int].getOrElse(0),
              jsObj("name").asOpt[String].getOrElse("null"),
              jsObj("order").as[Int],
              jsObj("profile_path").asOpt[String].getOrElse("null")))
          .toList
    }
    val cast = limpiarJsons(dataMap, "cast").flatMap(toTupla)

    cast
  }

  def crewLista(dataMap: List[Map[String, String]]): List[(String, String, Int, Int, String, String, String)] = {
    def toTupla(data: JsValue): List[(String, String, Int, Int, String, String, String)] = {
      val result = Try(data.as[JsArray])
        .toEither.left.map(error => (error, data))
      result match
        case Left(_, jsvalue) =>
          val jsObj = jsvalue.as[JsObject].value
          List((jsObj("credit_id").as[String],
            jsObj("department").asOpt[String].getOrElse("null"),
            jsObj("gender").asOpt[Int].getOrElse(0),
            jsObj("id").as[Int],
            jsObj("job").asOpt[String].getOrElse("null"),
            jsObj("name").asOpt[String].getOrElse("null"),
            jsObj("profile_path").asOpt[String].getOrElse("null")))


        case Right(jsvalue) => jsvalue.value
          .map(_.as[JsObject])
          .map(jsObj =>
            (jsObj("credit_id").as[String],
              jsObj("department").asOpt[String].getOrElse("null"),
              jsObj("gender").asOpt[Int].getOrElse(0),
              jsObj("id").as[Int],
              jsObj("job").asOpt[String].getOrElse("null"),
              jsObj("name").asOpt[String].getOrElse("null"),
              jsObj("profile_path").asOpt[String].getOrElse("null")))
          .toList
    }
    val crew = limpiarJsons(dataMap, "crew").flatMap(toTupla)


    crew
  }

  def ratingsLista(dataMap: List[Map[String, String]]): List[(Int,Double, Long)] = {
    def toTupla(data: JsValue): List[(Int,Double, Long)] = {
      val result = Try(data.as[JsArray])
        .toEither.left.map(error => (error, data))
      result match
        case Left(_, jsvalue) =>
          val jsObj = jsvalue.as[JsObject].value
          List((jsObj("userId").asOpt[Int].getOrElse(0),
            jsObj("rating").asOpt[Double].getOrElse(0.0),
            jsObj("timestamp").asOpt[Long].getOrElse(0L)))


        case Right(jsvalue) => jsvalue.value
          .map(_.as[JsObject])
          .map(jsObj =>
            (jsObj("userId").asOpt[Int].getOrElse(0),
              jsObj("rating").asOpt[Double].getOrElse(0.0),
              jsObj("timestamp").asOpt[Long].getOrElse(0L)))
          .toList
    }
    val ratings = limpiarJsons(dataMap, "ratings").flatMap(toTupla)

    ratings
  }
}
