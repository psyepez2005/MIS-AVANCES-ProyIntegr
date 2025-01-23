
object Limpiador {
  def borrarIDsVacios(dataMap: List[Map[String, String]]):List[Map[String, String]]={

    val dataLimpia = dataMap.filter(fila =>fila.get("id").nonEmpty )//  .contains("id") no verifica si el valor esta vacio, solo verifica si la clave existe

    dataLimpia
  }

  def borrarTODOSVacios(dataMap: List[Map[String, String]], columnas: List[String]): List[Map[String, String]] = {

    val dataLimpia = dataMap.filter(fila => columnas.forall(col => fila.get(col).exists(_.nonEmpty)))

    dataLimpia
  }

  def limpiarEspacios(dataMap: List[Map[String, String]], columnas: List[String]): List[Map[String, String]] = {

    val resultado = dataMap.map { mapa =>
      mapa.map {
        case (llave, valor) if columnas.contains(llave) => (llave, valor.trim) // Transformar si la clave está en `columnas`
        case (llave, valor) => (llave, valor) // No transformar si la clave no está en `columnas`
      }
    }

    resultado
  }

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






}

