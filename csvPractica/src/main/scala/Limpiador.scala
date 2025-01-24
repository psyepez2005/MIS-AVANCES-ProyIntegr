
object Limpiador {
  def borrarIDsVacios(dataMap: List[Map[String, String]]):List[Map[String, String]]={

    val dataLimpia = dataMap.filter(fila =>fila.get("id").nonEmpty )//  .contains("id") no verifica si el valor esta vacio, solo verifica si la clave existe

    dataLimpia
  }

  def borrarDatosVacios(dataMap: List[Map[String, String]], columnas: List[String]): List[Map[String, String]] = {
    val columnasSinHP_TL = columnas.filterNot(x => x.equals("homepage") || x.equals("tagline"))
    
    val dataLimpia = dataMap.filter(fila => columnasSinHP_TL.forall(col => fila.get(col).exists(_.nonEmpty)))

    dataLimpia
  }

  def limpiarEspacios(dataMap: List[Map[String, String]], columnas: List[String]): List[Map[String, String]] = {
    val columnasSinHP_TL = columnas.filterNot(x => x.equals("homepage") || x.equals("tagline"))

    val dataLimpia = dataMap.map { mapa =>
      mapa.map {
        case (llave, valor) if columnasSinHP_TL.contains(llave) => (llave, valor.trim) // Transformar si la clave está en `columnas`
        case (llave, valor) => (llave, valor) // No transformar si la clave no está en `columnas`
      }
    }
    dataLimpia
  }

  def eliminarRepetidos( columna: String,dataMap: List[Map[String, String]]): List[Map[String, String]] = {

    val dataLimpia = dataMap.groupBy(_.get(columna)).values.map(_.head).toList //Map[String, List[String]], es lo mismo que .groupBy(adlt=>adlt)


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