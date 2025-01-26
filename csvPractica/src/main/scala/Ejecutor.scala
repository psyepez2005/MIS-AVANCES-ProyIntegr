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
    val dm3 = borrarDatosVacios(dm1, columnasNotNull)
    println("\nFILTRO = Peliculas sin id, imdb_id, title   borradas...")
    println("Total de peliculas con id, imdb_id, title: " + dm3.length)
    //---------------------------------------------------------------------------------------------
    //==Borrar peliculas con id repetido
    val dm4 = eliminarRepetidos("id", dm3)
    println("\nFILTRO =Borrando peliculas con ids repetidos...")
    println("Total de peliculas sin ids repetidos: " + dm4.length)
    //---------------------------------------------------------------------------------------------
    //==Borrar peliculas con imdb_id repetido
    val dm5 = eliminarRepetidos("imdb_id", dm4)
    println("\nFILTRO = Borrando peliculas con imdb_id repetidos...")
    println("Total de peliculas sin imdb_id repetidos: " + dm5.length)
    //---------------------------------------------------------------------------------------------
    //==Cambiar numeros negativos por "NULL"
    val dm6 = numerosNegativos(dm5, columnasNumericas)
    println("\nMAPEO = Numeros negativos reemplazados por 0...")
    //---------------------------------------------------------------------------------------------
    //==Valores vacios reemplazados por "NULL"
    val dm7 = llenarDatosVacios(dm6)
    println("\nMAPEO = Valores vacios reemplazados por NULL...")
    //---------------------------------------------------------------------------------------------

    //%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@
    //                             LIMPIEZA DATOS JSONs
    //%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@%%@@


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
