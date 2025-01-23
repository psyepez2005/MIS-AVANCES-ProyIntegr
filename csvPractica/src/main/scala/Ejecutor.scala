import LectorCSV.lectura
import Limpiador.*

object Ejecutor {
  //LEER EL CSV ENTREGADO
  val dataMap: List[Map[String, String]] = lectura()
  @main
  def main(): Unit = {
    //println(dataMap.head.foreach(println))
    //=========LIMPIAR DATOS========
    println("Total: " + dataMap.length)

    //TOD0
    val todasColumnasNOJson: List[String] = List(
      "adult",
      "video",
      "homepage",
      "imdb_id",
      "original_language",
      "original_title",
      "overview",
      "poster_path",
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

    /*
    //==Borrar películas sin id
    val dataMapId: List[Map[String, String]] = borrarIDsVacios(dataMap)
    println("Con ID: " + dataMapId.length)
    */


    //==Borrar películas con datos vacios
    val dataMapTODO: List[Map[String, String]] = borrarTODOSVacios(dataMap, todasColumnasNOJson)
    println("Con TOD0: " + dataMapTODO.length)







  }
}
