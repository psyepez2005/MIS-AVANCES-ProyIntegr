///////////////////////////////////////////////////////////////////////////////////////////////////
import com.github.tototoshi.csv.*
import java.io.File
object LectorCSV {
  def lectura(): List[Map[String, String]] = {
    implicit object MiFormato extends DefaultCSVFormat {
      override val delimiter: Char = ';'
    }
    val pathMoviesBIG: String = "D:\\yeped\\Escritorio\\pi_movies_complete.csv"
    val reader = CSVReader.open(new File(pathMoviesBIG))
    val dataMap: List[Map[String, String]] = reader.allWithHeaders()
    //dataMap.foreach(println)
    dataMap
  }

  def moda(columna: String, dataMap: List[Map[String, String]]): Map[String, Int] = {
    val lista: List[String] =
      dataMap
        .flatMap(row => row.get(columna)) //

    val moda: Map[String, Int] = lista
      .groupBy(identity) //Map[String, List[String]], es lo mismo que .groupBy(adlt=>adlt)
      .map(entrada => (entrada._1, entrada._2.length)) //Map[String,Int]
      //.maxBy(_._2) //(String,Int)

    moda //retorno

  }
}
///////////////////////////////////////////////////////////////////////////////////////////////////
