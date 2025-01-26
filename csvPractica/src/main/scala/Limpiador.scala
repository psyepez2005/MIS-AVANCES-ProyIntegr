
object Limpiador {
  def borrarIDsVacios(dataMap: List[Map[String, String]]):List[Map[String, String]]={

    val dataLimpia = dataMap.filter(fila =>fila.get("id").nonEmpty )//  .contains("id") no verifica si el valor esta vacio, solo verifica si la clave existe

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

  def numerosNegativos(dataMap: List[Map[String, String]],columnas: List[String]): List[Map[String, String]] = {
    val dataLimpia = dataMap.map(mapa =>
      mapa.map{
        (llave, valor) => {
          try{
            if (columnas.contains(llave) && valor.toLong < 0) (llave,"0")
            else (llave, valor)
          } catch{
            case _ =>
              if (columnas.contains(llave) && valor.toDouble < 0) (llave, "0")
              else (llave, valor)
          }
        }

      }
    )
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