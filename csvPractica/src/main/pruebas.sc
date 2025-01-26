/*
import LectorCSV.lectura
val dataMap: List[Map[String, String]] = lectura()
dataMap.foreach(println)
*/

val x = Map(1 -> 1, "2" -> 2,3 -> 3)
x.size


//62
/*
//===============================================================
def borrarSinLlaves(dataMap: List[Map[String, String]], columnas: List[String]): List[Map[String, String]] = {

  val dataLimpia = dataMap.filter(fila => fila.forall((llave,valor)=> columnas.contains(llave)))

  dataLimpia
}
def borrarSinLlaves2(dataMap: List[Map[String, String]], columnas: List[String]): List[Map[String, String]] = {
  val dataLimpia = dataMap.filter(fila => columnas.toSet.subsetOf(fila.keySet))
  dataLimpia
}



val listaD = List("a","b","c","d")
val d : List[Map[String,String]] =
  List(
    Map("a"->"", "b"->"", "c"->"", "d"->""),
    Map("a"->"A", "b"->"B", "c"->"C", "d"->"D"),
    Map("a"->"A", "b"->"B", "c"->"C"),
    Map("a"->"A", "b"->"B", "x"->"C"),
  )



val dr = borrarSinLlaves(d, listaD)
dr.foreach(println)

//===============================================================
def eliminarRepetidos( columna: String,dataMap: List[Map[String, String]]): List[Map[String, String]] = {

  val dataLimpia = dataMap.groupBy(_.get(columna)).values.map(_.head).toList


  dataLimpia
}


val listaD = List("a","b","c","d")
val d : List[Map[String,String]] =
  List(
    Map("a"->"A", "b"->"B", "c"->" ", "d"->"x"),
    Map("a"->"A", "b"->"B", "c"->"", "d"->"y"),
    Map("a"->"a", "b"->"b", "c"->"", "d"->"z"),
  )

val dr = eliminarRepetidos("c",d)
dr.foreach(println)

//===============================================================
def llenarDatosVacios(dataMap: List[Map[String, String]]): List[Map[String, String]] = {
  val dataLimpia = dataMap.map { mapa =>
    mapa.map (
      (key, valor) => if (valor.isEmpty) (key, "NULL") else (key, valor)
    )
  }
  dataLimpia
}



val c : List[Map[String,String]] =
  List(
    Map("a"->"  A", "b"->"B  ", "c"->"  C  ", "d"->"    "),
    Map("a"->"A", "b"->"", "c"->"C", "d"->""),
    Map("a"->"", "b"->"", "c"->"", "d"->""),
  )

val cr = llenarDatosVacios(c)
cr.foreach(println)

//===============================================================
def borrarDatosVacios(dataMap: List[Map[String, String]], columnas: List[String]): List[Map[String, String]] = {

  val dataLimpia = dataMap.filter(fila => columnas.forall(col => fila.get(col).exists(_.nonEmpty)))

  dataLimpia
}

val listaB = List("a","b","c","d")

val b : List[Map[String,String]] =
  List(
    Map("a"->"  A", "b"->"B  ", "c"->"  C  ", "d"->"   "),
    Map("a"->"Z", "b"->"B", "c"->"C", "d"->""),
    Map("a"->"A", "b"->"    ", "c"->"  ", "d"->"  "),
  )

val br = borrarDatosVacios(b, listaB)
br.foreach(println)

//===============================================================
def trimeador(dataMap: List[Map[String, String]]): List[Map[String, String]] = {
  val dataLimpia = dataMap.map { mapa =>
    mapa.map (
      (llave, valor) => (llave, valor.trim)
    )
  }
  dataLimpia
}

val a : List[Map[String,String]] =
  List(
    Map("a"->"", "b"->"     xd", "c"->"xd       ", "d"->"xd"),
    Map("a"->"xd", "b"->"x d", "c"->"xd", "d"->"xd"),
    Map("a"->"     ", "b"->"    ", "c"->"", "d"->""),
  )

val ar = trimeador(a)
ar.foreach(println)


//===============================================================
def numerosNegativos(dataMap: List[Map[String, String]],columnas: List[String]): List[Map[String, String]] = {
  val dataLimpia = dataMap.map(mapa =>
    mapa.map{
      (llave, valor) => if (columnas.contains(llave) && valor.toLong < 0) (llave,"0") else (llave, valor)

    }
  )
  dataLimpia
}

val listaY = List("a","b","c","d")

val y : List[Map[String,String]] =
  List(
    Map("a"->"-2", "b"->"-1", "c"->"0", "d"->"1"),
    Map("a"->"-5", "b"->"-4", "c"->"-3", "d"->"-2"),
    Map("a"->"1", "b"->"2", "c"->"3", "d"->"4"),
  )

val yr = numerosNegativos(y, listaY)
yr.foreach(println)
*/
