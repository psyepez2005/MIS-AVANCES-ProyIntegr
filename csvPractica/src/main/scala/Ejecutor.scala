import LectorCSV.lectura
import Limpiador.*
import CLASE__IGNORAR_lectorJsonLISTMAP.mainn
import PobladorDB.insertarCrew
import caseClassesJson.*
//import play.api.libs.json.{Json, OFormat}
import transformadorAcaseclass.transformar
import Parseador.*
import ParseadorJSONS.*
import ConstructorBD.*

object Ejecutor {
  //LEER EL CSV ENTREGADO


  //@main
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
    val dm7 = simplificarNumeros(dm5, columnasNumericas)
    println("\nMAPEO = Numeros redondeados...")
    //---------------------------------------------------------------------------------------------



    //%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@
    //                             LIMPIEZA DATOS JSONs
    //%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@

    val lista_collection = collectionLista(dm7)

    val lista_genres = genresLista(dm7)


    lista_genres.foreach(println)
    println(lista_genres.length)






























    /*
    //---------------------------------------------------------------------------------------------
    //==Encerrar bien los JSONs
    val listaPeliculas = transformar(dm7)
    println("peliculas: "+listaPeliculas.length)

    //construir()

    val listaCollection = lista_collection(listaPeliculas)
    println("collection: " + listaCollection.length)
    val listaGenres = lista_genres(listaPeliculas)
    println("genres: "+listaGenres.length)
    val listaProdCompan = lista_prod_compan(listaPeliculas)
    println("prod companies: "+listaProdCompan.length)
    val listaProdCount = lista_prod_countr(listaPeliculas)
    println("prod countries: "+listaProdCount.length)
    val listaSpokenLanguages = lista_spoken_lang(listaPeliculas)
    println("spoken languages: "+listaSpokenLanguages.length)
    val listaKeyWords = lista_keywords(listaPeliculas)
    println("keywords: "+listaKeyWords.length)
    val listaCast = lista_cast(listaPeliculas)
    println("cast: "+listaCast.length)
    val listaCrew = lista_crew(listaPeliculas)
    println("crew: "+listaCrew.length)
    insertarCrew(listaCrew)
    val listaRatings = lista_ratings(listaPeliculas)
    println("ratings: "+listaRatings.length)
    
     





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

     */
  }
}