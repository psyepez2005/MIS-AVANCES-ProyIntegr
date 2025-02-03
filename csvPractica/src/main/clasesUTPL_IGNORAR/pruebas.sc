import ParseadorJSONS.{corregirLLaves, eliminarIncompletos}
import play.api.libs.json.*

val lt1 = List(
  (1,2,3,"a"),
  (1,1,1,"b"),
  (2,2,2,"c"),
  (3,3,3,"a")
)
lt1.length
lt1.distinct
lt1.distinctBy(x => x._1)
lt1.distinctBy(x => (x._3,x._4))


val str1 : String = null
val int1 : Int = 0

val json = "[{\"name\": \"Polski State Film\", \"id\": 24674}, {\"name\": \"ZRF Kamera\", \"id\": 76032}]"
val json2 = "[{\"name\": \"Polski State Film\", \"id\": 24674}, {\"name\": \"ZRF Kamera\", \"id\": 76032}]"

val hola = Json.parse(json)
val hola2 = hola.as[JsObject].value

List((hola2("name").as[String],
  hola2("id").as[Int]))

hola2

println(hola)
/*
def fixJson(rawJson: String): String = {
  // Paso 1: Reemplazar todas las comillas simples por comillas dobles
  val withDoubleQuotes = rawJson.replace("'", "\"")

  // Expresión regular para encontrar las cadenas entre comillas dobles
  val regex: Regex = "\"([^\"]*?)\"".r

  // Paso 2: Restaurar las comillas simples dentro de los valores
  // Usamos replaceAllIn para procesar cada coincidencia
  val fixedJson = regex.replaceAllIn(withDoubleQuotes, m => {
    val content = m.group(1)
    // Restaurar comillas simples dentro del valor
    "\"" + content.replace("\"", "'") + "\""
  })

  fixedJson
}

val jsonmalo = "[{'name': 'Polski State Film', 'id': 24674}, {'name': 'ZRF 'Kamera', 'id': 76032}]"

val jsonbueno = fixJson(jsonmalo)

import LectorCSV.lectura
val dataMap: List[Map[String, String]] = lectura()
dataMap.foreach(println)
val x = Map(1 -> 1, "2" -> 2,3 -> 3)
x.size
*/

/*
val i = ""
i.isEmpty
i.isBlank
val e = "      "
e.isEmpty
e.isBlank
val g = "      ".trim
g.isEmpty
g.isBlank
def cleanJsonLista(json: String): String = {
  if json.isEmpty then "[]xd"
  else
    try {
      val cleanedJson = json
        .replaceAll("'", "\"") // Cambia comillas simples por dobles
        .replaceAll("None", "NULL") // Cambia None por null
        .replaceAll("\\\\", "") // Elimina barras invertidas dobles
        .replaceAll("\\s*:\\s*", ":") // Elimina espacios alrededor de los dos puntos
        .replaceAll("\\s*,\\s*", ",") // Elimina espacios alrededor de las comas
        .replaceAll("\\s*\\{\\s*", "{") // Elimina espacios después de llaves de apertura
        .replaceAll("\\s*\\}\\s*", "}") // Elimina espacios antes de llaves de cierre
        .replaceAll("\\s*\\[\\s*", "[") // Elimina espacios después de corchetes de apertura
        .replaceAll("\\s*\\]\\s*", "]") // Elimina espacios antes de corchetes de cierre
        .replaceAll("\r?\n", "") // Elimina saltos de línea


      // Intentar parsear para validar el JSON
      val parsedJson = Json.parse(cleanedJson)
      val ass = Json.stringify(parsedJson) // Devuelve el JSON como String validado
      ass
      //cleanedJson
    } catch {
      case _: Exception =>
        "[]"
    }
}
val limpio = """{
              |  "titulo": "Inception",
              |  "año": 2010,
              |  "genero": ["Ciencia ficción", "Acción"],
              |  "director": {
              |    "nombre": "Christopher Nolan",
              |    "nacionalidad": "Británico"
              |  },
              |  "calificacion": 8.8
              |}
              |""".stripMargin
val limpio2 = """[
                |  {
                |    "titulo": "Inception",
                |    "año": 2010,
                |    "genero": ["Ciencia ficción", "Acción"],
                |    "director": {
                |      "nombre": "Christopher Nolan",
                |      "nacionalidad": "Británico"
                |    },
                |    "calificacion": 8.8
                |  },
                |  {
                |    "titulo": "The Matrix",
                |    "año": 1999,
                |    "genero": ["Ciencia ficción", "Acción"],
                |    "director": {
                |      "nombre": "Lana Wachowski",
                |      "nacionalidad": "Estadounidense"
                |    },
                |    "calificacion": 8.7
                |  }
                |]
                |""".stripMargin

val sucio ="""{
             |  "titulo": "Inception"
             |  "año": 2010,
             |  "genero": ["Ciencia ficción", "Acción"
             |  "director": {
             |    "nombre": "Christopher Nolan",
             |    "nacionalidad": "Británico",
             |  },
             |  "calificacion": 8.8,
             |}
             |""".stripMargin

val vacio =""

cleanJsonLista(limpio)
cleanJsonLista(limpio2)
cleanJsonLista(sucio)
cleanJsonLista(vacio)

val mapa = Map(("vacio",""),("lleno","hola"))
//val nulo : Boolean = null
//val numnulo:Int = null
val kdi = mapa.get("lleno")
val kdi2 = mapa.get("vacio")
val kdi3 = mapa.getOrElse("lleno","orelselleno")
val kdi4 = mapa.getOrElse("vacio","orelsevacio")
val kdi5 = mapa.getOrElse("noexiste","orelsevacio")




//===============================================================
val x = "False"
x.toBoolean

val o = "-314145544"
o.toDouble
o.toLong


//===============================================================
import ParseJson.jsonToMap
import Limpiador.*

val strin ="{'id': 96665, 'name': 'Dumb and Dumber Collection', 'poster_path': 'fdB86tl7SKIBWZzm0CbrYjOdE1K.jpg', 'backdrop_path': '/saUnj5K6buVafDfLfTozDH2eqdC.jpg'}    "
val string2 = cleanJsonUnico(strin)
val s3 = "{}"

val x = jsonToMap(s3)
x.foreach(println)








//===============================================================

def formateadorJson(dataMap: List[Map[String, String]], columnas: List[String]) :List[Map[String, String]]= {
  def corregirJson(json: String): String = {
    if (json.startsWith("{") && !json.endsWith("}")) return json + "}"
    if (json.startsWith("[") && !json.endsWith("]")) return json + "]"
    json
  }
  val dataLimpia = dataMap.map(mapa => mapa.map((llave, valor)=> (llave,corregirJson(valor))))

  dataLimpia
}

val listaF = List("a","b","c","d")
val f : List[Map[String,String]] =
  List(
    Map("a"->"[ .1.. )", "b"->"NULL", "c"->"", "d"->"", "e"->"", "f"->"", "g"->""),
    Map("a"->"[ .2.. ]", "b"->"NULL", "c"->"", "d"->"", "e"->"", "f"->"", "g"->"x"),
    Map("a"->"{ .3.. ]", "b"->"NULL", "c"->"", "d"->"", "e"->"", "f"->"", "g"->""),
    Map("a"->"{ .4.. }", "b"->"NULL", "c"->"", "d"->"", "e"->"", "f"->"", "g"->""),
    Map("a"->"", "b"->"", "c"->"NULL", "d"->"", "e"->"", "f"->"", "g"->"")
  )


val fr = formateadorJson(f, listaF)
fr.foreach(println)




//===============================================================

import com.fasterxml.jackson.databind.{JsonNode, ObjectMapper}
import scala.util.Try

object DatasetCleaner {
  val mapper = new ObjectMapper()

  def esJsonValido(json: String): Boolean = {
    Try(mapper.readTree(json)).isSuccess
  }

  def corregirJson(json: String): String = {
    if (json.startsWith("{") && !json.endsWith("}")) return json + "}"
    if (json.startsWith("[") && !json.endsWith("]")) return json + "]"
    json
  }

  def limpiarDataset(dataset: List[Map[String, String]], claves: List[String]): List[Map[String, String]] = {
    dataset.map { fila =>
      fila.map { case (clave, valor) =>
        if (claves.contains(clave)) {
          val valorLimpio = corregirJson(valor.trim)
          if (valorLimpio.isEmpty || valorLimpio == "null" || !esJsonValido(valorLimpio)) {
            clave -> "{}"  // Si es inválido o vacío, lo reemplazamos con un JSON vacío
          } else {
            clave -> valorLimpio
          }
        } else {
          clave -> valor
        }
      }
    }
  }
}

// Ejemplo de uso
val dataset = List(
  Map("id" -> "1", "datos" -> """{"nombre":"Pelicula"""),  // JSON mal cerrado → Se corregirá
  Map("id" -> "2", "datos" -> """["item1", "item2""" ),   // JSON mal cerrado → Se corregirá
  Map("id" -> "3", "datos" -> ""),                       // JSON vacío → Se reemplaza con "{}"
  Map("id" -> "4", "datos" -> "null"),                   // "null" → Se reemplaza con "{}"
  Map("id" -> "5", "datos" -> """{"correcto":true}""")   // JSON correcto → Se mantiene igual
)

val clavesARevisar = List("datos")

val datasetLimpio = DatasetCleaner.limpiarDataset(dataset, clavesARevisar)
println(datasetLimpio)

//===============================================================

def validarJSONs2(dataMap: List[Map[String, String]], columnas: List[String]): List[Map[String, String]] = {
  val dataLimpia = dataMap.filter(mapa => columnas.forall(col=>
    mapa.get(col) match{
      case Some(valor) =>
        valor.isEmpty ||
        (valor.startsWith("[") && valor.endsWith("]")) ||
        (valor.startsWith("{") && valor.endsWith("}")) ||
        valor == "NULL"

    }
  ))

  dataLimpia
}


val listaE = List("a","b","c","d")
val e : List[Map[String,String]] =
  List(
    Map("a"->"[ .1.. )", "b"->"", "c"->"", "d"->"", "e"->"", "f"->"", "g"->""),
    Map("a"->"[ .2.. ]", "b"->"", "c"->"", "d"->"", "e"->"", "f"->"", "g"->"x"),
    Map("a"->"{ .3.. ]", "b"->"", "c"->"", "d"->"", "e"->"", "f"->"", "g"->""),
    Map("a"->"{ .4.. }", "b"->"x", "c"->"", "d"->"", "e"->"", "f"->"", "g"->""),
    Map("a"->"", "b"->"", "c"->"", "d"->"", "e"->"", "f"->"", "g"->"")
  )


val er = validarJSONs2(e, listaE)
er.foreach(println)


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
