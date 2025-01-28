
object transformadorAcaseclass {
  def transformar(dataMap: List[Map[String, String]]): List[Pelicula]={
    val peliculas: List[Pelicula] = dataMap.map { mapa =>
      Pelicula(
        adult = mapa("adult").toBoolean,
        belongs_to_collection = mapa.get("belongs_to_collection").toString,
        budget = mapa("budget").toLong,
        genres = mapa.get("genres").toString,
        homepage = mapa.get("homepage").toString,
        id = mapa("id").toInt,
        imdb_id = mapa.get("imdb_id").toString,
        original_language = mapa.get("original_language").toString,
        original_title = mapa.get("original_title").toString,
        overview = mapa.get("overview").toString,
        popularity = mapa("popularity").toLong,
        poster_path = mapa.get("poster_path").toString,
        production_companies = mapa.get("production_companies").toString,
        production_countries = mapa.get("production_countries").toString,
        release_date = mapa.get("release_date").toString,
        revenue = mapa("revenue").toLong,
        runtime = mapa("runtime").toInt,
        spoken_languages = mapa.get("spoken_languages").toString,
        status = mapa.get("status").toString,
        tagline = mapa.get("tagline").toString,
        title = mapa.get("title").toString,
        video = mapa("video").toBoolean,
        vote_average = mapa("vote_average").toInt,
        vote_count = mapa("vote_count").toInt,
        keywords = mapa.get("keywords").toString,
        cast = mapa.get("cast").toString,
        crew = mapa.get("crew").toString,
        ratings = mapa.get("ratings").toString
      )
    }
    peliculas
  }
}
