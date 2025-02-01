
object Limpiador {
   
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
            valor == ""
      }
    ))

    dataLimpia
  }
  
  def borrarDatosVacios(dataMap: List[Map[String, String]], columnas: List[String]): List[Map[String, String]] = {

    val dataLimpia = dataMap.filter(fila => columnas.forall(col => fila.get(col).exists(_.nonEmpty)))

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
        if (columnas.contains(llave) && valor.isEmpty) (llave, "0")
        else if (columnas.contains(llave) && (valor.toDouble < 0)) (llave, "0")

        else (llave,valor)
      }
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


  



}