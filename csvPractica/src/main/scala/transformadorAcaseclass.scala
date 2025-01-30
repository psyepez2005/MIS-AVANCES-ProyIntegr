
object transformadorAcaseclass {
  def transformar(dataMap: List[Map[String, String]]): List[Pelicula] = {
    val peliculas: List[Pelicula] = dataMap.map { mapa =>

      Pelicula(
        adult = try {
          mapa.getOrElse("adult", "false").toBoolean
        } catch case _ => false,
        belongs_to_collection = try {
          mapa.getOrElse("belongs_to_collection", "")
        } catch case _ => "",
        budget = try {
          mapa.getOrElse("budget", "0").toLong
        } catch case _ => 0,
        genres = try {
          mapa.getOrElse("genres", "")
        } catch case _ => "",
        homepage = try {
          mapa.getOrElse("homepage", "")
        } catch case _ => "",
        id = try {
          mapa.getOrElse("id", "0").toInt
        } catch case _ => 0,
        imdb_id = try {
          mapa.getOrElse("imdb_id", "")
        } catch case _ => "",
        original_language = try {
          mapa.getOrElse("original_language", "")
        } catch case _ => "",
        original_title = try {
          mapa.getOrElse("original_title", "")
        } catch case _ => "",
        overview = try {
          mapa.getOrElse("overview", "")
        } catch case _ => "",
        popularity = try {
          mapa.getOrElse("popularity", "0").toLong
        } catch case _ => 0,
        poster_path = try {
          mapa.getOrElse("poster_path", "")
        } catch case _ => "",
        production_companies = try {
          mapa.getOrElse("production_companies", "")
        } catch case _ => "",
        production_countries = try {
          mapa.getOrElse("production_countries", "")
        } catch case _ => "",
        release_date = try {
          mapa.getOrElse("release_date", "")
        } catch case _ => "",
        revenue = try {
          mapa.getOrElse("revenue", "0").toLong
        } catch case _ => 0,
        runtime = try {
          mapa.getOrElse("runtime", "0").toInt
        } catch case _ => 0,
        spoken_languages = try {
          mapa.getOrElse("spoken_languages", "")
        } catch case _ => "",
        status = try {
          mapa.getOrElse("status", "")
        } catch case _ => "",
        tagline = try {
          mapa.getOrElse("tagline", "")
        } catch case _ => "",
        title = try {
          mapa.getOrElse("title", "")
        } catch case _ => "",
        video = try {
          mapa.getOrElse("video", "false").toBoolean
        } catch case _ => false,
        vote_average = try {
          mapa.getOrElse("vote_average", "0").toInt
        } catch case _ => 0,
        vote_count = try {
          mapa.getOrElse("vote_count", "0").toInt
        } catch case _ => 0,
        keywords = try {
          mapa.getOrElse("keywords", "")
        } catch case _ => "",
        cast = try {
          mapa.getOrElse("cast", "")
        } catch case _ => "",
        crew = try {
          mapa.getOrElse("crew", "")
        } catch case _ => "",
        ratings = try {
          mapa.getOrElse("ratings", "")
        } catch case _ => ""
      )
    }
    peliculas
  }
}
