
import LectorCSV.lectura
import Limpiador.{borrarDatosVacios, eliminarRepetidos, numerosNegativos, simplificarNumeros, trimeador}
import play.api.libs.json.*

import scala.util.*
import scala.util.matching.Regex


object CLASE__IGNORAR_lectorJsonLISTMAP {
  def corregirLLaves(json: String): String = {
    if (json.startsWith("{") && !json.endsWith("}")) return json + "}"
    if (json.startsWith("[") && !json.endsWith("]")) return json + "]"
    if (json.endsWith("}") && !json.startsWith("{")) return "{" + json
    if (json.endsWith("]") && !json.startsWith("[")) return "[" + json
    json
  }

  def corregirComas(json: String): String = {
    if (json.endsWith(",")) return json.dropRight(1)
    json
  }


  def mainJsonllamar(dataMap: List[Map[String, String]], columna: String) : List[Any] ={
    val json: List[String] = dataMap
      .flatMap(row => row.get(columna))
      .map(fila => fila.replaceAll("'", "\""))
      .map(_.trim)
      .map(corregirComas)
      .map(corregirLLaves)

    val listaDatos = columna match {
      case "belongs_to_collection" => json.map(Json.parse).flatMap(toTuplaCollection)
      case "genres" =>
      case "production_companies" =>
      case "production_countries" =>
      case "spoken_languages" =>
      case "keywords" =>
      case "cast" =>
      case "crew" =>
      case "ratings" =>
    }
    /*
    val tN = json
      .map(Json.parse)
      .flatMap(toTupla)

     */
    List(1,"",null)

  }

  def toTuplaCollection(data: JsValue): List[(Int, String, String, String)] = {

    val result = Try(data.as[JsObject])
      .toEither.left.map(error => (error, data))
    result match
      case Left(_, jsvalue) =>
        val jsObj = jsvalue.as[JsObject].value
        List(
          (jsObj("id").as[Int],
          jsObj("name").as[String],
          jsObj("poster_path").as[String],
          jsObj("backdrop_path").as[String])
        )

/*
      case Right(jsvalue) => jsvalue.value
        .map(_.as[JsObject])
        .map(jsObj =>
              (jsObj("id").as[Int],
              jsObj("name").as[String],
              jsObj("poster_path").as[String],
              jsObj("backdrop_path").as[String])
            )
        .toList
 */
  }

  def extractorJsons(dataMap: List[Map[String, String]], columna: String) ={
    val json : List[String] = dataMap
      .flatMap(row => row.get(columna))
      .map(fila => fila.replaceAll("'","\""))
      .map(_.trim)
      .map(corregirComas)
      //.map(corregirLLaves)
    /*
    columna match{
      case "belongs_to_collection" =>
    }

    val tupla: List[Try[JsArray]] = json
      .map(Json.parse)
      .map(txt => Try(txt.as[JsArray]))
    val tuplaBien = tupla.map(_.isSuccess)
    val tuplaMal = tupla.map(_.isFailure)
    val tN = json
      .map(Json.parse)
      //.flatMap(toTupla)
    tN.foreach(println)
    println("total: " + tN.length)
    */
  }

  def fixJson(rawJson: String): String = {
    // Expresión regular para detectar las claves y valores mal formateados
    val regex: Regex = "'([^']*?)'".r

    // Reemplazamos solo las comillas que encierran claves y valores
    val y = regex.replaceAllIn(rawJson, m => "\"" + m.group(1) + "\"")
    y
  }

  def eliminarIncompletos(json: String): String = {
    if (json.contains('{')) {
      val ultimo = json.lastIndexOf('}')
      val arreglado = json.substring(0, ultimo).appended('}').appended(']')
      arreglado
    }
    else
      json
  }

  //@main
  def mainn(): Unit = {

    val dataMap: List[Map[String, String]] = lectura()



    //TOD0
    val todasColumnas: List[String] = List(
      "adult",
      "belongs_to_collection",
      "production_companies",
      "production_countries",
      "genres",
      "video",
      "homepage",
      "imdb_id",
      "original_language",
      "original_title",
      "overview",
      "poster_path",
      "runtime",
      "keywords",
      "ratings",
      "cast",
      "crew",
      "spoken_languages",
      "release_date",
      "status",
      "tagline",
      "title",
      "budget",
      "id",
      "popularity"
    )
    //JSONs
    val columnasJson: List[String] = List(
      "belongs_to_collection",
      "genres",
      "production_companies",
      "production_countries",
      "spoken_languages",
      "keywords",
      "cast",
      "crew",
      "ratings"
    )
    //NO JSONS
    val todasColumnasNOJson: List[String] = List(
      "adult",
      "video",
      "homepage",
      "imdb_id",
      "original_language",
      "original_title",
      "overview",
      "poster_path",
      "runtime",
      "release_date",
      "status",
      "tagline",
      "title",
      "budget",
      "id",
      "popularity"
    )
    //BOOLEANS
    val columnasBooleanas: List[String] = List(
      "adult",
      "video")
    //TEXTOS
    val columnasString: List[String] = List(
      "homepage",
      "imdb_id",
      "original_language",
      "original_title",
      "overview",
      "poster_path",
      "release_date",
      "status",
      "tagline",
      "title")
    //NUMERICAS
    val columnasNumericas: List[String] = List(
      "budget",
      "id",
      "popularity",
      "revenue",
      "runtime",
      "vote_average",
      "vote_count"
    )
    //NO REPETIBLES
    val columnasNotNull: List[String] = List(
      "imdb_id",
      "id",
      "title")
    //%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@
    //                             LIMPIEZA DATOS NORMALES
    //%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@
    //---------------------------------------------------------------------------------------------
    //==Borrar espacios en los textos
    val dm1 = trimeador(dataMap)
    //---------------------------------------------------------------------------------------------
    //==Borrar peliculas sin id, imdb_id ni title
    val dm2 = borrarDatosVacios(dm1, columnasNotNull)
    //---------------------------------------------------------------------------------------------
    //==Borrar peliculas con id repetido
    val dm3 = eliminarRepetidos("id", dm2)
    //---------------------------------------------------------------------------------------------
    //==Borrar peliculas con imdb_id repetido
    val dm4 = eliminarRepetidos("imdb_id", dm3)
    //---------------------------------------------------------------------------------------------
    //==Cambiar numeros negativos por "NULL"
    val dm5 = numerosNegativos(dm4, columnasNumericas)
    //---------------------------------------------------------------------------------------------
    //==Numeros con notacion redondeados
    val dm7 = simplificarNumeros(dm5, columnasNumericas)
    //---------------------------------------------------------------------------------------------

    val companies: List[String] = dm7
      .flatMap(row => row.get("crew"))
      .filterNot(x => x.isEmpty)
      .map(fila => fila.replaceAll("'", "\""))
      .map(corregirLLaves)
      .map(eliminarIncompletos)
      //.map(x => if x.charAt(x.length - 2) == '"' then x.dropRight(2).appended('"').appended('}').appended(']') else x)



    val tup_companies = companies
      //.map(Json.parse)
      .map(x => Try(Json.parse(x)))


    println("TOTAL: " + tup_companies.length)
    println("Bien parseados: " + tup_companies.count(_.isSuccess))
    println("Mal parseados: " + tup_companies.count(_.isFailure))

    val colMAl = tup_companies.filter(_.isFailure)
    colMAl.foreach(println)



    /*
    val collections: List[String] = dm7

      .flatMap(row => row.get("belongs_to_collection"))
      .filterNot(x => x.isEmpty)
      .map(fila => fila.replaceAll("'","\""))
      .map(corregirLLaves)
      .map(_.replaceAll("None", "null"))
      .map(x => if !x.endsWith("\"}")then x.appended('"').appended('}') else x )
      .map(x=>
        if x.charAt(x.length-2) == 'l' then
          x
        else if x.charAt(x.length-2) != '"' then
          x.dropRight(1).appended('"').appended('}') else x) //muy especifico

    //collections.take(10).foreach(println)


    val tup_colection  = collections
      //.map(Json.parse)
    .map(x => Try(Json.parse(x)))


    println("TOTAL: "+tup_colection.length)
    println("Bien parseados: "+tup_colection.count(_.isSuccess))
    println("Mal parseados: "+tup_colection.count(_.isFailure))

    val colBien = tup_colection.filter(_.isSuccess).map(_.get)

    colBien.foreach(println)
*/


    /*
    val genres: List[String] = dataSample
      .flatMap(row => row.get("genres"))
      .map(fila => fila.replaceAll("'","\""))
      .map(_.trim)
      .map(corregirComas)
      .map(corregirLLaves)

    /*:List[(Int, String)] */
    val t2: List[Try[JsArray]] = genres
      .map(Json.parse)
      .map(txt=>Try(txt.as[JsArray]))

    val t2bien = t2.map(_.isSuccess)
    val t2mal = t2.map(_.isFailure)

    //t2bien.foreach(println)

    def toTupla(data: JsValue): List[(Int, String)] =
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


    val tN = genres
      .map(Json.parse)
      .flatMap(toTupla)

    tN.foreach(println)
    println("total: "+tN.length)
    */







/*
    val ratings: List[String] = dataSample
      .flatMap(row => row.get("ratings"))
      .map(fila => fila.replaceAll("'", "\""))
      .map(_.trim)
      .map(corregirComas)
      .map(corregirLLaves)

    //ratings.take(1).foreach(println)    //el .take(1) solo agarra un valor de la lista de Strings de ratings
    val rating: String = ratings(0) // ratings(0) agarra el primer valor de la lista de Strings de ratings, es lo mismo que .head



    // este puede causar errores si es que el json no es un arreglo []
    // ya que si es un solo objeto {} no se puede aplanar a .flatMap(_.as[JsArray].value)

    val t3: List[(Int, Double, Long)] =
      ratings.take(1) //List[String]
      .map(Json.parse(_)) //List[JsValue]
      .flatMap(_.as[JsArray].value) //List[JsValue]
      .map(_.as[JsObject])  //List[JsObject]
      .map(jsObj => ( //List[(Int, Double, Long)]
        jsObj("userId").as[Int],
        jsObj("rating").as[Double],
        jsObj("timestamp").as[Long]
      ))

    val t33conTry = ratings
      .map(Json.parse(_))
      .map(txt=>Try(txt.as[JsArray]))

    val t33Succes = t33conTry.filter(_.isSuccess) //filtra las que no causan error
    val t33Failure = t33conTry.filter(_.isFailure)

    println(t33Succes.length)
    println(t33Failure.length)

    val t33finalSucces = t33Succes
      .map(_.get)
      .flatMap(_.value)
      .map(_.as[JsObject])
      .map(jsObj => (
        jsObj("userId").as[Int],
        jsObj("rating").as[Double],
        jsObj("timestamp").as[Long]
      ))
    val t33finalFailure = t33Failure
      .map(_.get)
      .flatMap(_.value)
      .map(_.as[JsObject])
      .map(jsObj => (
        jsObj("userId").as[Int],
        jsObj("rating").as[Double],
        jsObj("timestamp").as[Long]
      ))

    def toTupla1(data: JsValue):List[(Int,Double,Long)]=
      val result = Try(data.as[JsArray])
        .toEither.left.map( error => (error, data))
      result match
        case Left(_, jsvalue) => {
          val jsObj = jsvalue.as[JsObject].value
          List((jsObj("userID").as[Int],
          jsObj("rating").as[Double],
          jsObj("timestamp").as[Long]))
        }
        case Right(jsvalue) => jsvalue.value
          .map(_.as[JsObject])
          .map(jsObj => (
            jsObj("userID").as[Int],
            jsObj("rating").as[Double],
            jsObj("timestamp").as[Long]))
          .toList

    val tN1 = ratings
      .map(Json.parse)
      .flatMap(toTupla)
      //.map{(u,r,l) => ()}

    tN1.take(100).foreach(println)


 */
  }



}
