import com.github.tototoshi.csv.*
import java.io.File
//=======CAMBIAR EL DELIMITADOR
implicit object MiFormato extends DefaultCSVFormat{
  override val delimiter: Char = ';'
}

//=======PATHS
val pathGoleadores: String = "D:\\yeped\\Escritorio\\UTPL\\Programación Funcional\\toptengoleadores.csv"
val pathMoviesSmall: String = "D:\\yeped\\Escritorio\\small_pi_movies.csv"
val pathMoviesBIG: String ="D:\\yeped\\Escritorio\\pi_movies_complete.csv"

//=======CREAR EL READER
val reader = CSVReader.open(new File(pathMoviesSmall))

val dataHEAVY = reader.all()



/*
//=======GUARDAR EL ARCHIVO EN UNA LISTA DE LISTAS DE STRINGS
val data : List[List[String]] = reader.all()

//=======IMPRIMIR LAS FILAS
data.foreach(println)

//CONSULTAR LA PRIMERA COLUMNA DE TODAS LAS FILAS MENOS LA PRIMERA
data
    .slice(1, data.length)
    .map(row => row(0))

*/

//HAGO UNA LISTA DE MAPAS LLAVE STRING, DATO STRING
val dataMap: List[Map[String,String]] = reader.allWithHeaders()

//IMPRIMO LOS DATOS
dataMap.foreach(println)

//IMPRIMIR NOMBRE DE LOS JUGADORES
dataMap
  .foreach(goleador => println(goleador("Jugador")))

//análisis exploratorio de datos (EDA)
//EDA para datos numéricos: min, avg, max, stdDev, moda, cuartiles

val listGoles : List[Int] = dataMap.map(goleador => goleador("Goles").toInt)
val menor = listGoles.min
val mayor = listGoles.max
val datosParaPromedioOptimo: (Double,Int) =
  listGoles.foldLeft((0.0,0))((t2, currVal)=>(t2._1+currVal,t2._2+1))
val promedio = datosParaPromedioOptimo._1/datosParaPromedioOptimo._2

 