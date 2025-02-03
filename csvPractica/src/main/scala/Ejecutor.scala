import LectorCSV.lectura
import Limpiador.*
import CLASE__IGNORAR_lectorJsonLISTMAP.mainn
import PobladorDB.insertarCrew
import caseClassesJson.*
//import play.api.libs.json.{Json, OFormat}
import transformadorAcaseclass.transformar
//import Parseador.*
import ParseadorFINAL.*
import PobladorFINAL.*
import ParseadorJSONS.*
import ConstructorBD.*
import LectorCSV.lectura
import Limpiador.{borrarDatosVacios, eliminarRepetidos, numerosNegativos, simplificarNumeros, trimeador}
import play.api.libs.json.*
object Ejecutor {
  //LEER EL CSV ENTREGADO


  @main
  def main(): Unit = {

    val dataMap: List[Map[String, String]] = lectura()
    //println(dataMap.head.foreach(println))
    //=========LIMPIAR DATOS========
    println("Total de peliculas sin limpiar: " + dataMap.length)


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
    println("\nMAPEO = Espacios al inicio y final eliminados...")
    //---------------------------------------------------------------------------------------------
    //==Borrar peliculas sin id, imdb_id ni title
    val dm2 = borrarDatosVacios(dm1, columnasNotNull)
    println("\nFILTRO = Peliculas sin id, imdb_id, title borradas...")
    println("Total de peliculas con id, imdb_id, title: " + dm2.length)
    //---------------------------------------------------------------------------------------------
    //==Borrar peliculas con id repetido
    val dm3 = eliminarRepetidos("id", dm2)
    println("\nFILTRO =Borrando peliculas con ids repetidos...")
    println("Total de peliculas sin ids repetidos: " + dm3.length)
    //---------------------------------------------------------------------------------------------
    //==Borrar peliculas con imdb_id repetido
    val dm4 = eliminarRepetidos("imdb_id", dm3)
    println("\nFILTRO = Borrando peliculas con imdb_id repetidos...")
    println("Total de peliculas sin imdb_id repetidos: " + dm4.length)
    //---------------------------------------------------------------------------------------------
    //==Cambiar numeros negativos por "NULL"
    val dm5 = numerosNegativos(dm4, columnasNumericas)
    println("\nMAPEO = Numeros negativos reemplazados por 0...")
    //---------------------------------------------------------------------------------------------
    //==Numeros con notacion redondeados
    val dm6 = simplificarNumeros(dm5, columnasNumericas)
    println("\nMAPEO = Numeros redondeados...")
    //---------------------------------------------------------------------------------------------
    //==Espacios vacios llenados con NULL
    val dm7 = nulleador(dm6)
    println("\nMAPEO = Espacios vacios llenados con NULL...")
    //%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@
    //                             LIMPIEZA DATOS JSONs     // // // //construir()
    //%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@
    //---------------------------------------------------------------------------------------------
    //==Jsons adaptados para parseo adecuado
    val dm8 = limpia(dm7, columnasJson) //dm8 es de tipo List[Map[String,String]]
    println("\nMAPEO = Columnas Json corregidas y listas para parseo...")
    //---------------------------------------------------------------------------------------------
    println("dm8 total:  "+ dm8.length)

    val collection = tablaCollection(dm8).distinctBy(_._1)

    val pelicula = tablaPelicula2(dm8).distinctBy(_._1)

    val genero = tablaGenero(dm8).distinctBy(_._1)
    val pelicula_genero = tablaPeliculaGenero(dm8).distinctBy(x => (x._1, x._2))

    val idioma = tablaLanguages(dm8).distinctBy(_._1)
    val pelicula_idioma = tablaPeliculaIdioma(dm8).distinctBy(x => (x._1, x._2))

    val pais = tablaCountries(dm8).distinctBy(_._1)
    val pelicula_pais = tablaPeliculaPais(dm8).distinctBy(x => (x._1, x._2))

    val productora = tablaCompanies(dm8).distinctBy(_._1)
    val pelicula_productora = tablaPeliculaProductora(dm8).distinctBy(x => (x._1, x._2))

    val empleado = tablaEmpleado(dm8).distinctBy(x => x._1)
    val job_depart = tablaJobDepart(dm8).distinctBy(x => x._1)
    val pelicula_empleado = tablaPeliculaEmpleado(dm8).distinctBy(x => (x._1, x._2))

    val actor = tablaActor(dm8).distinctBy(x => x._1)
    val pelicula_actor = tablaPeliculaCast(dm8).distinctBy(x => (x._1, x._2))

    val keyword = tablaKeywords(dm8).distinctBy(_._1)
    val pelicula_keyword = tablaPeliculaKeywords(dm8).distinctBy(x => (x._1, x._2))

    val usuario = tablaUsuario(dm8).distinct
    val calificacion = tablaCalificacion(dm8).distinctBy(x => (x._1, x._2))


    pelicula.foreach(println)
    println("Longitud:  " + pelicula.length)
    //construir()
    insertarPeliculaDB(pelicula)

/*
    val tabla_Cast = tablaCast(dm8)
    val distCast = tabla_Cast.distinctBy(_._1)
    val tabla_Crew = tablaCrew(dm8)
    val distCrew = tabla_Crew.distinctBy(_._1)
    val tabla_Ratings = tablaRatings(dm8)
    val distRatings = tabla_Ratings.distinctBy(x => (x._1, x._3))

    //val tabla_Collection
    val tabla_Pelicula = tablaPelicula(dm8)
    val tabla_Collection = tablaCollection(dm8)
    val distCollection = tabla_Collection.distinctBy(_._1)
    val tabla_Genero = tablaGenero(dm8)
    val distGenero = tabla_Genero.distinctBy(_._1)
    val tabla_Companies = tablaCompanies(dm8)
    val distCompanies = tabla_Companies.distinctBy(_._1)
    val tabla_Countries = tablaCountries(dm8)
    val distCountries = tabla_Countries.distinctBy(_._1)
    val tabla_Languages = tablaLanguages(dm8)
    val distLanguages = tabla_Languages.distinctBy(_._1)
    val tabla_Keywords = tablaKeywords(dm8)
    val distKeywords = tabla_Keywords.distinctBy(_._1)
    val tabla_Cast = tablaCast(dm8)
    val distCast = tabla_Cast.distinctBy(_._1)
    val tabla_Crew = tablaCrew(dm8)
    val distCrew = tabla_Crew.distinctBy(_._1)
    val tabla_Ratings = tablaRatings(dm8)
    val distRatings = tabla_Ratings.distinctBy(x => (x._1, x._3))



     */
    /*
    println("Datos en collection: " + tabla_Collection.length)
    println("Datos distintos en collection: " + distCollection.length)
    println("Datos en genres: " + tabla_Genero.length)
    println("Datos distintos en genres: " + distGenero.length)
    println("Datos en companies: " + tabla_Companies.length)
    println("Datos distintos en companies: " + distCompanies.length)
    println("Datos en countries: " + tabla_Countries.length)
    println("Datos distintos en countries: " + distCountries.length)
    println("Datos en languages: " + tabla_Languages.length)
    println("Datos distintos en languages: " + distLanguages.length)
    println("Datos en keywords: " + tabla_Keywords.length)
    println("Datos distintos en keywords: " + distKeywords.length)
    println("Datos en cast: " + tabla_Cast.length)
    println("Datos distintos en cast: " + distCast.length)
    println("Datos en crew: " + tabla_Crew.length)
    println("Datos distintos en crew: " + distCrew.length)
    println("Datos en ratings: " + tabla_Ratings.length)
    println("Datos distintos en ratings: " + distRatings.length)
*/

    /*
    val tabla_Pelicula_Genero = tablaPeliculaGenero(dm8)

    println(tabla_Pelicula_Genero.distinctBy(x => (x._1,x._2)).length)
    println(tabla_Pelicula_Genero.length)

    val pelicula_productora = tablaPeliculaProductora(dm8)
    println(pelicula_productora.distinctBy(x => (x._1,x._2)).length)
    println(pelicula_productora.length)

    val pelicula_pais = tablaPeliculaPais(dm8)
    println(pelicula_pais.distinctBy(x => (x._1,x._2)).length)
    println(pelicula_pais.length)

    val pelicula_idioma = tablaPeliculaIdioma(dm8)
    println(pelicula_idioma.distinctBy(x => (x._1,x._2)).length)
    println(pelicula_idioma.length)

    val pelicula_keywords = tablaPeliculaKeywords(dm8)
    println(pelicula_keywords.distinctBy(x => (x._1,x._2)).length)
    println(pelicula_keywords.length)

    val actor = tablaActor(dm8)
    println(actor.distinctBy(x => x._1).length)
    println(actor.length)

    val pelicula_actor = tablaPeliculaCast(dm8)
    println(pelicula_actor.distinctBy(x => (x._1,x._2)).length)
    println(pelicula_actor.length)

    val empleado = tablaEmpleado(dm8)
    println(empleado.distinctBy(x => x._1).length)
    println(empleado.length)

    val job_depart = tablaJobDepart(dm8)
    println(job_depart.distinctBy(x => x._1).length)
    println(job_depart.length)

    val pelicula_empleado = tablaPeliculaEmpleado(dm8)
    println(pelicula_empleado.distinctBy(x => (x._1,x._2)).length)
    println(pelicula_empleado.length)

    val usuario = tablaUsuario(dm8)
    println(usuario.distinct.length)
    println(usuario.length)

    val calificacion = tablaCalificacion(dm8)
    println(calificacion.distinctBy(x => (x._1,x._2)).length)
    println(calificacion.length)
*/







/*
    val dm80 = limpiadorTRISTE(dm7,columnasJson) //dm8 es de tipo List[Map[String,String]]
    val tablagenres = dm8 //tablagenres es de tipo List[String]
      .flatMap(_.get("genres"))
      //.filterNot(x=> x=="[]")

    val tabla_peli_genr = dm8.map{mapa=>
      val genre = mapa("genres")
      val jsongrupo = Json.parse(genre).as[JsArray]
      //val json = jsongrupo.m
      (mapa("id"), jsongrupo)

    }
    val tabla_peli_genrfinal2 = tabla_peli_genr.flatMap { (id, jsArray) =>
      jsArray.value.map { jsonGenero =>
        val jsonObj=jsonGenero.as[JsObject]

        Map("id" -> id, "genre" -> (jsonObj \ "name").as[String])
      }
    }
    val tabla_peli_genrfinal = dm8.flatMap { mapa =>
      val id = mapa("id").toInt // Extraer el ID de la película
      val jsonString = mapa("genres") // Obtener el string que contiene el JSON

      // Parsear el String a JsArray
      val jsArray = Json.parse(jsonString).as[JsArray]

      // Extraer cada género y crear la tupla (id, género)
      jsArray.value.collect {
        case obj: JsObject if (obj \ "name").isDefined =>
          (id, (obj \ "name").as[String])
      }
    }
    val resultado = dm8.flatMap { mapa =>
      val id = mapa("id").toInt
      val jsArray = Json.parse(mapa("genres")).as[JsArray]

      jsArray.value.map { obj =>
        (id, obj("name").as[String])
      }
    }
    println(resultado.length)
    resultado.foreach(println)
*/







/*
    val lista_collection = collectionLista(dm7)
    //lista_collection.foreach(println)
    val distinct_collection = lista_collection.distinctBy(_._1)
    //distinct_collection.foreach(println)

    val lista_genres = genresLista(dm7)
    lista_genres.foreach(println)
    val distinct_genres = lista_genres.distinctBy(_._1)
    //distinct_genres.foreach(println)


    val lista_companies = companiesLista(dm7)
    val distinct_companies = lista_companies.distinctBy(_._2)
    //distinct_companies.foreach(println)

    val lista_countries =countriesLista(dm7)
    val distinct_countries = lista_countries.distinctBy(_._1)
    //distinct_countries.foreach(println)

    val lista_languages = languagesLista(dm7)
    val distinct_languages = lista_languages.distinctBy(_._1) // ====CORREGIR NOMBRES VACIOS!!!====
    //distinct_languages.foreach(println)

    val lista_keywords = keywordsLista(dm7)
    val distinct_keywords = lista_keywords.distinctBy(_._1)
    //distinct_keywords.foreach(println)

    val lista_cast = castLista(dm7)
    val distinct_cast = lista_cast.distinctBy(_._1)
    //distinct_cast.foreach(println)

    val lista_crew = crewLista(dm7)
    val distinct_crew = lista_crew.distinctBy(_._1)
    //distinct_crew.foreach(println)

    val lista_ratings = ratingsLista(dm7)
    val distinct_ratings = lista_ratings.distinct
    distinct_ratings.take(1000).foreach(println)


    println("Datos en collection: "+lista_collection.length)
    println("Datos distintos en collection: "+distinct_collection.length)
    println("Datos en genres: "+lista_genres.length)
    println("Datos distintos en genres: "+distinct_genres.length)
    println("Datos en companies: "+lista_companies.length)
    println("Datos distintos en companies: "+distinct_companies.length)
    println("Datos en countries: " + lista_countries.length)
    println("Datos distintos en countries: "+distinct_countries.length)
    println("Datos en languages: " + lista_languages.length)
    println("Datos distintos en languages: " + distinct_languages.length)
    println("Datos en keywords: " + lista_keywords.length)
    println("Datos distintos en keywords: " + distinct_keywords.length)
    println("Datos en cast: " + lista_cast.length)
    println("Datos distintos en cast: " + distinct_cast.length)
    println("Datos en crew: " + lista_crew.length)
    println("Datos distintos en crew: " + distinct_crew.length)
    println("Datos en ratings: " + lista_ratings.length)
    println("Datos distintos en ratings: " + distinct_ratings.length)

    //val dm8 = dm7.map()

 */
/*
    val peliculaGenero: List[(Int,Int)] = dm7.map{ x =>
      val a = x.get("id")
      val b = genresLista()

    }

*/

  }

  def moda(columna: String, dataMap: List[Map[String, String]]): (String, Int) = {
    val lista: List[String] =
      dataMap
        .flatMap(row => row.get(columna)) //

    val moda: (String, Int) = lista
      .groupBy(identity) //Map[String, List[String]], es lo mismo que .groupBy(adlt=>adlt)
      .map(entrada => (entrada._1, entrada._2.length)) //Map[String,Int]
      .maxBy(_._2) //(String,Int)

    moda //retorno
  }

  def valoresDiferentes(columna: String, dataMap: List[Map[String, String]]): Map[String, Int] = {
    val lista: List[String] =
      dataMap
        .flatMap(row => row.get(columna)) //

    val valoresDiferentes: Map[String, Int] = lista
      .groupBy(identity) //Map[String, List[String]], es lo mismo que .groupBy(adlt=>adlt)
      .map(entrada => (entrada._1, entrada._2.length)) //Map[String,Int]
    //.filter(fil => fil._2 > 1)

    valoresDiferentes //retorno
  }
}