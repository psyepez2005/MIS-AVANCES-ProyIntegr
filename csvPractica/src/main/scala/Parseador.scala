import caseClassesJson.*
import play.api.libs.json.{Json, OFormat, __}

object Parseador {

  def lista_collection(listaPeliculas: List[Pelicula]) = {
    implicit val formato_collection: OFormat[belongs_to_collection] = Json.format[belongs_to_collection]
    val collectionParceado = listaPeliculas.map { mov =>
      try {
        Json.parse(cleanJsonUnico(corregirJson(mov.belongs_to_collection))).as[List[belongs_to_collection]]
      } catch {
        case _ => null
      }
    }
    val dataLimpia = collectionParceado.filterNot(x => x == null).flatten
    dataLimpia
  }
  def lista_genres(listaPeliculas: List[Pelicula]) = {
    implicit val formato_genres: OFormat[genres] = Json.format[genres]
    val genresParceado = listaPeliculas.map { mov =>
      try {
        Json.parse(cleanJsonLista(corregirJson(mov.genres))).as[List[genres]]
      } catch {
        case _ => null
      }
    }
    val dataLimpia = genresParceado.filterNot(x => x == null).flatten
    dataLimpia
  }

  def lista_prod_compan(listaPeliculas: List[Pelicula]) = {
    implicit val formato_production_companies: OFormat[production_companies] = Json.format[production_companies]
    val production_companiesParceado = listaPeliculas.map { mov =>
      try {
        Json.parse(cleanJsonLista(corregirJson(mov.production_companies))).as[List[production_companies]]
      } catch {
        case _ => null
      }
    }
    val dataLimpia = production_companiesParceado.filterNot(x => x == null).flatten
    dataLimpia
  }

  def lista_prod_countr(listaPeliculas: List[Pelicula]) = {
    implicit val formato_production_countries: OFormat[production_countries] = Json.format[production_countries]
    val production_countriesParceado = listaPeliculas.map { mov =>
      try {
        Json.parse(cleanJsonLista(corregirJson(mov.production_countries))).as[List[production_countries]]
      } catch {
        case _ => null
      }
    }
    val dataLimpia = production_countriesParceado.filterNot(x => x == null).flatten
    dataLimpia
  }
  def lista_spoken_lang(listaPeliculas: List[Pelicula])={
    implicit val formato_spoken_languages: OFormat[spoken_languages] = Json.format[spoken_languages]
    val spoken_languagesParceado = listaPeliculas.map { mov =>
      try {
        Json.parse(cleanJsonLista(corregirJson(mov.spoken_languages))).as[List[spoken_languages]]
      } catch {
        case _ => null
      }
    }
    val dataLimpia =spoken_languagesParceado.filterNot(x=>x==null).flatten
    dataLimpia
  }

  def lista_keywords(listaPeliculas: List[Pelicula]) = {
    implicit val formato_keywords: OFormat[keyWords] = Json.format[keyWords]
    val keywordsParceado = listaPeliculas.map { mov =>
      try {
        Json.parse(cleanJsonLista(corregirJson(mov.keywords))).as[List[keyWords]]
      } catch {
        case _ => null
      }
    }
    val dataLimpia = keywordsParceado.filterNot(x => x == null).flatten
    dataLimpia
  }

  def lista_cast(listaPeliculas: List[Pelicula]) = {
    implicit val formato_cast: OFormat[cast] = Json.format[cast]
    val castParceado = listaPeliculas.map { mov =>
      try {
        Json.parse(cleanJsonLista(corregirJson(mov.cast))).as[List[cast]]
      } catch {
        case _ => null
      }
    }
    val dataLimpia = castParceado.filterNot(x => x == null).flatten
    dataLimpia
  }

  def lista_crew(listaPeliculas: List[Pelicula]) = {
    implicit val formato_cast: OFormat[crew] = Json.format[crew]
    val crewParceado = listaPeliculas.map { mov =>
      try {
        Json.parse(cleanJsonLista(corregirJson(mov.crew))).as[List[crew]]
      } catch {
        case _ => null
      }
    }
    val dataLimpia = crewParceado.filterNot(x => x == null).flatten
    dataLimpia
  }

  def lista_ratings(listaPeliculas: List[Pelicula]) = {
    implicit val formato_cast: OFormat[ratings] = Json.format[ratings]
    val ratingsParceado = listaPeliculas.map { mov =>
      try {
        Json.parse(cleanJsonLista(corregirJson(mov.ratings))).as[List[ratings]]
      } catch {
        case _ => null
      }
    }
    val dataLimpia = ratingsParceado.filterNot(x => x == null).flatten
    dataLimpia
  }


  def corregirJson(json: String): String = {

    json
  }
  /*
  def corregirJson(json: String): String = {
    if (json.startsWith("{") && !json.endsWith("}")) return json + "}"
    if (json.startsWith("[") && !json.endsWith("]")) return json + "]"
    if (json.endsWith("}") && !json.startsWith("{")) return "{" + json
    if (json.endsWith("]") && !json.startsWith("[")) return "[" + json
    json
  }
*/
  def cleanJsonUnico(json: String): String = {
    try {
      val cleanedJson = json
        .replaceAll("'", "\"") // Cambia comillas simples por dobles
        .replaceAll("None", "NULL") // Cambia None por null
        .replaceAll("\\\\", "") // Elimina barras invertidas dobles
        .replaceAll("\\s*:\\s*", ":") // Elimina espacios alrededor de los dos puntos
        .replaceAll("\\s*,\\s*", ",") // Elimina espacios alrededor de las comas
        .replaceAll("\\s*\\{\\s*", "{") // Elimina espacios después de llaves de apertura
        .replaceAll("\\s*\\}\\s*", "}") // Elimina espacios antes de llaves de cierre
        .replaceAll("\r?\n", "") // Elimina saltos de línea

      // Intentar parsear para validar el JSON
      val parsedJson = Json.parse(cleanedJson)
      Json.stringify(parsedJson) // Devuelve el JSON como String validado
    } catch {
      case _: Exception =>
        "{}"
    }
  }

  def cleanJsonLista(json: String): String = {
    if json.isEmpty then "[]"
    else
      try {
        val cleanedJson = json
          .replaceAll("'", "\"") // Cambia comillas simples por dobles
          .replaceAll("None", "NULL") // Cambia None por null
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
          "[]"
      }
  }
  /*
  def cleanJsonLista(json: String): String = {
    if json.isEmpty then "[]"
    else
      try{
      val cleanedJson = json
        .replaceAll("'", "\"") // Cambia comillas simples por dobles
        .replaceAll("None", "NULL") // Cambia None por null
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
      cleanedJson
    } catch {
      case _: Exception =>
        "[]"
    }
  }*/
}
