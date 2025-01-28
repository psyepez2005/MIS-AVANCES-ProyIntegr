
object Limpiador {
  
  def cleanJsonLista(json: String): String = {
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
      //val parsedJson = Json.parse(cleanedJson)
      //Json.stringify(parsedJson) // Devuelve el JSON como String validado
      cleanedJson
    } catch {
      case _: Exception =>
        "[]"
    }
  }

  def cleanJsonUnico(json: String): String = {
    try {
      val cleanedJson = json
        .replaceAll("'", "\"") // Cambia comillas simples por dobles
        .replaceAll("None", "NULL") // Cambia None por null
        .replaceAll("\\\\", "") // Elimina barras invertidas dobles
        .replaceAll("\\s*:\\s*", ":") // Elimina espacios alrededor de los dos puntos
        .replaceAll("\\s*,\\s*", ",") // Elimina espacios alrededor de las comas
        .replaceAll("\\s*\\{\\s*", "{") // Elimina espacios después de llaves de apertura
        .replaceAll("\\s*\\}\\s*", "}") // Elimina espacios antes de llaves de cierre
        .replaceAll("\r?\n", "") // Elimina saltos de línea

      // Intentar parsear para validar el JSON
      //val parsedJson = Json.parse(cleanedJson)
      //Json.stringify(parsedJson) // Devuelve el JSON como String validado
      cleanedJson
    } catch {
      case _: Exception =>
        "{}"
    }
  }
  
  
  def limpiadorJsons(dataMap:List[Map[String, String]], columna: String ):List[Map[String, String]]={
    def cleanJsonUnico(json: String): String = {
      try {
        val cleanedJson = json
          .replaceAll("'", "\"") // Cambia comillas simples por dobles
          .replaceAll("NULL", "{}") // Cambia None por null
          .replaceAll("\\\\", "") // Elimina barras invertidas dobles
          .replaceAll("\\s*:\\s*", ":") // Elimina espacios alrededor de los dos puntos
          .replaceAll("\\s*,\\s*", ",") // Elimina espacios alrededor de las comas
          .replaceAll("\\s*\\{\\s*", "{") // Elimina espacios después de llaves de apertura
          .replaceAll("\\s*\\}\\s*", "}") // Elimina espacios antes de llaves de cierre
          .replaceAll("\r?\n", "") // Elimina saltos de línea

        // Intentar parsear para validar el JSON
        //val parsedJson = Json.parse(cleanedJson)
        //Json.stringify(parsedJson) // Devuelve el JSON como String validado
        cleanedJson
      } catch {
        case _: Exception =>
          "{}"
      }
    }
    val dataLimpia = dataMap.map(mapa=> mapa.map((llave,valor) => (llave,cleanJsonUnico(valor))))
    
    dataLimpia
  }
  
  def formateadorJson(dataMap: List[Map[String, String]], columnas: List[String]): List[Map[String, String]] = {
    def corregirJson(json: String): String = {
      if (json.startsWith("{") && !json.endsWith("}")) return json + "}"
      if (json.startsWith("[") && !json.endsWith("]")) return json + "]"
      if (json.endsWith("}") && !json.startsWith("{")) return "{" + json
      if (json.endsWith("]") && !json.startsWith("[")) return "[" + json
      json
    }

    val dataLimpia = dataMap.map(mapa => mapa.map((llave, valor) => (llave, corregirJson(valor))))

    dataLimpia
  }


  def validarJSONs(dataMap: List[Map[String, String]], columnas: List[String]): List[Map[String, String]] = {
    val dataLimpia = dataMap.filter(mapa => columnas.forall(col =>
      mapa.get(col) match {
        case Some(valor) =>
          valor.isEmpty ||
            (valor.startsWith("[") && valor.endsWith("]")) ||
            (valor.startsWith("{") && valor.endsWith("}"))  ||
            valor == "NULL"
      }
    ))

    dataLimpia
  }


  def borrarDatosVacios(dataMap: List[Map[String, String]], columnas: List[String]): List[Map[String, String]] = {

    val dataLimpia = dataMap.filter(fila => columnas.forall(col => fila.get(col).exists(_.nonEmpty)))

    dataLimpia
  }

  def borrarSinLlaves(dataMap: List[Map[String, String]], columnas: List[String]): List[Map[String, String]] = {
    val dataLimpia = dataMap.filter(fila => columnas.toSet.subsetOf(fila.keySet))
    dataLimpia
  }
  def simplificarNumeros(dataMap: List[Map[String, String]], columnas :List[String]): List[Map[String, String]] = {
    val dataLimpia = dataMap.map(mapa =>
      mapa.map{
        (llave, valor) => 
          if (valor.contains(".")||valor.contains("-")) && columnas.contains(llave) then{
            val num = valor.toDouble
            val numRedondeado = Math.round(num)
            (llave,""+numRedondeado)
          }
          else (llave,valor)
            

      }
    )
    dataLimpia
  }
  def numerosNegativos(dataMap: List[Map[String, String]], columnas: List[String]): List[Map[String, String]] = {
    val dataLimpia = dataMap.map { mapa =>
      mapa.map { (llave, valor) =>
        if (valor.isEmpty) (llave, "0")
        else if (columnas.contains(llave) && (valor.toDouble < 0)) (llave, "0")

        else (llave,valor)
      }
    }
    dataLimpia
  }




  def llenarDatosVacios(dataMap: List[Map[String, String]]): List[Map[String, String]] = {
    val dataLimpia = dataMap.map { mapa =>
      mapa.map (
        (key, valor) => if (valor.isEmpty) (key, "NULL") else (key, valor)
      )
    }
    dataLimpia
  }


  def trimeador(dataMap: List[Map[String, String]]): List[Map[String, String]] = {
    val dataLimpia = dataMap.map { mapa =>
      mapa.map (
        (llave, valor) => (llave, valor.trim)
      )
    }
    dataLimpia
  }


  def eliminarRepetidos( columna: String,dataMap: List[Map[String, String]]): List[Map[String, String]] = {

    val dataLimpia = dataMap.groupBy(_.get(columna)).values.map(_.head).toList


    dataLimpia
  }
  
//////////////////////////////////////////////////////////////////
  def limpiarFila(fila: Map[String, String]): Map[String, String] = {
    fila.map {
      case (key, value) if value.isEmpty => (key, "VACIO")  
      case noVacio => noVacio  
    }
  }
  
  

  def transformarBools(lista: List[String]): List[Boolean] = {
    val listaBooleanos = lista.map{
      case "True" => true
      case "False" => false
      case _ => false
    }
    listaBooleanos
  }

  def escapeMySQL(input: String): String = {
    input
      .replaceAll("'", "''") // Escape single quotes
      .replaceAll("\"", "\\\"") // Escape double quotes (if needed for specific databases)
      .replaceAll("%", "\\%") // Escape percentage (wildcard in LIKE statements)
      .replaceAll("", "\\") // Escape underscore (wildcard in LIKE statements)
  }



}