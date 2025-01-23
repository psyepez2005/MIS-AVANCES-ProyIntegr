///////////////////////////////////////////////////////////////////////////////////////////////////
import com.github.tototoshi.csv.*
import java.io.File
object Proyecto {
  //@main
  def main():Unit = {
    implicit object MiFormato extends DefaultCSVFormat {
      override val delimiter: Char = ';'
    }
    val pathMoviesBIG: String ="D:\\yeped\\Escritorio\\pi_movies_complete.csv"
    val reader = CSVReader.open(new File(pathMoviesBIG))
    val dataMap: List[Map[String, String]] = reader.allWithHeaders()
    //dataMap.foreach(println)
///////////////////////////////////////////////////////////////////////////////////////////////////

//  ===============MODA=========================
    val modaAdult = moda("adult", dataMap)//  Map[String,Int]
    println("moda adulto: "+modaAdult)



/*
    val budgets: List[Long] = dataMap
      .flatMap(row =>
        row.get("budget")
          .flatMap(bdgt =>
            scala.util.Try(bdgt.toLong).toOption))
*/

  }

  def eliminarNullsColumnasNumericas(columna: String, dataMap: List[Map[String, String]]):Unit = {
    val budgets: List[Long] = dataMap
      .flatMap(row =>
        row.get(columna)
          .flatMap(bdgt =>
            scala.util.Try(bdgt.toLong).toOption))
  }

  def moda(columna: String, dataMap: List[Map[String, String]]):(String,Int)= {
    val lista: List[String] =
      dataMap
        .flatMap(row => row.get(columna)) //

    val moda:(String,Int) = lista
      .groupBy(identity) //Map[String, List[String]], es lo mismo que .groupBy(adlt=>adlt)
      .map(entrada => (entrada._1, entrada._2.length)) //Map[String,Int]
      .maxBy(_._2) //(String,Int)

    moda //retorno
  }
  def promedio(values: List[Long]): Double = {
    val sumCount: (Double, Int) = values
      .filter(_>0)
      .foldLeft((0.0,0))((t2,currVal)=>(t2._1+currVal,t2._2+1))
    sumCount._1/sumCount._2
  }
}


def maximo(columna: String, datos: List[Map[String, String]]): Double = {
  val aux: Double = datos.map(x => x(columna).toDouble).max
  aux
}

def minimo(columna: String, datos: List[Map[String, String]]): Double = {
  val aux: Double = datos.map(x => x(columna).toDouble).min
  aux
}