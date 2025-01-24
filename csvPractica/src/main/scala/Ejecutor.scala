import LectorCSV.lectura
import Limpiador.*

object Ejecutor {
  //LEER EL CSV ENTREGADO
  val dataMap: List[Map[String, String]] = lectura()
  @main
  def main(): Unit = {
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
      "popularity")
    //NO REPETIBLES
    val columnasUnicas: List[String] = List(
      "imdb_id",
      "id",
      "title")
    //---------------------------------------------------------------------------------------------
    //==Borrar espacios en los textos
    val dmTrim = limpiarEspacios(dataMap,todasColumnasNOJson)
    println("Espacios al inicio y final eliminados...")
    //---------------------------------------------------------------------------------------------
    //==Borrar películas con datos vacios
    val dmtCompleto: List[Map[String, String]] = borrarDatosVacios(dmTrim, todasColumnasNOJson)
    println("Borrando peliculas con algun atributo vacio menos homepage y tagline...")
    println("Total de peliculas con todos los atributos menos homepage y tagline: " + dmtCompleto.length)
    //---------------------------------------------------------------------------------------------
    //==Borrar peliculas con id repetido
    val dmtcSinIDRepetido = eliminarRepetidos("id", dmtCompleto)
    println("Borrando peliculas con ids repetidos...")
    println("Total de peliculas sin ids repetidos: "+ dmtcSinIDRepetido.length)
    //---------------------------------------------------------------------------------------------

    /*
    val valoresDiferentesVAL = valoresDiferentes("id", dmtcSinIDRepetido) //  Map[String,Int]
    println("valores diferentes id: " )
    valoresDiferentesVAL.foreach(println)
    println(valoresDiferentesVAL.size)

    val modaVAL = moda("id", dataMapSinRepetidos) //  Map[String,Int]
    println("moda id: ")
    println(modaVAL)
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

  def valoresDiferentes(columna: String, dataMap: List[Map[String, String]]):Map[String,Int] = {
    val lista: List[String] =
      dataMap
        .flatMap(row => row.get(columna)) //

    val valoresDiferentes: Map[String,Int] = lista
      .groupBy(identity) //Map[String, List[String]], es lo mismo que .groupBy(adlt=>adlt)
      .map(entrada => (entrada._1, entrada._2.length)) //Map[String,Int]
      .filter(fil => fil._2 > 1)

    valoresDiferentes //retorno
  }
}
