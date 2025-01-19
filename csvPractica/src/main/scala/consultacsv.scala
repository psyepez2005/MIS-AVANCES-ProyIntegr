class consultacsv

import com.github.tototoshi.csv.*

import java.io.File

object Reader {
  def main(args: Array[String]): Unit = {
    implicit object CSVFormatter extends DefaultCSVFormat {
      override val delimiter: Char = ';'
    }

    def maximo(columna: String, datos: List[Map[String, String]]): Double = {
      val aux: Double = datos.map(x => x(columna).toDouble).max
      aux
    }

    def promedio(values: List[Long]): Double = {
      val sumCount: (Double, Int) = values
        .filter(_>0)
        .foldLeft((0.0,0))((t2,currVal)=>(t2._1+currVal,t2._2+1))
      sumCount._1/sumCount._2
    }

    def minimo(columna: String, datos: List[Map[String, String]]): Double = {
      val aux: Double = datos.map(x => x(columna).toDouble).min
      aux
    }


    //val path: String = "D:\\yeped\\Escritorio\\pi_movies_complete.csv"
    val path = "D:\\yeped\\Escritorio\\small_pi_movies.csv"
    val reader = CSVReader.open(new File(path))

    val valores: List[Map[String, String]] = reader.allWithHeaders()
    println(maximo("budget",valores))
    println(minimo("budget",valores))



    /*
    val booleanosFalsos = valores.map(x => x("adult")).filter(_=="FALSO")
    val booleanosVerdaderos = valores.map(x => x("adult")).filter(_=="VERDADERO")
    */

    /*
    println("Falses: "+booleanosFalsos.size)
    println("Trues: "+booleanosVerdaderos.size)
    println("Totales: "+valores.size)
    //valores.foreach(println)
    //booleanos.foreach(println)
    */


    


  }
}