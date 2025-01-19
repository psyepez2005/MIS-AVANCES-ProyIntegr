import com.github.tototoshi.csv.*
import java.io.File

implicit object MiFormato extends DefaultCSVFormat{
  override val delimiter: Char = ';'
}
val pathMoviesSmall: String = "D:\\yeped\\Escritorio\\small_pi_movies.csv"
val reader = CSVReader.open(new File(pathMoviesSmall))
val dataMap : List[Map[String,String]] = reader.allWithHeaders()



//=========================MODA=========================
def moda(columna: String, dataMap: List[Map[String, String]]):Map[String,Int]= {
  val lista: List[String] =
    dataMap
      .flatMap(row => row.get(columna))

  lista
    .groupBy(identity) //Map[String, List[String]], es lo mismo que .groupBy(adlt=>adlt)
    .map(entrada => (entrada._1, entrada._2.length)) //Map[String,Int]
}
val modaAdult = moda("adult", dataMap)//  Map[String,Int]
//println(modaAdult.max)//.max evalua solo las keys,en este caso como las keys son strings, evaluaria segun la letra mas cercana al final,.min = +cercana al principio
println(modaAdult.maxBy(_._2))
//println("la moda de adult es: "+modaAdult.values.toList.max) //esto me da solo el numero de veces
//=========================MODA=========================


val budgets: List[Long] = dataMap
  .flatMap(row=>
    row.get("budget")
      .flatMap(bdgt=>
        scala.util.Try(bdgt.toLong).toOption))



val listAdult: List[String] =
  dataMap
    .flatMap(row=>row.get("adult"))
listAdult
  .distinct
  .foreach(println)
listAdult
  .groupBy(identity) //Map[String, List[String]], es lo mismo que .groupBy(adlt=>adlt)
  .map(entrada => (entrada._1, entrada._2.length))//Map[String,Int]




/*
Un Option es un tipo de dato que puede tener o NO un valor(null o no), y cuando se hace un lista.get("atributo"),
me retorna una lista de options, y al hacer un .flatMap, se eliminan los options que no tengan valor(null).
El try catch de scala se puede hacer como una funcion (scala.util.Try(lista.toDouble).toOption)
 */


