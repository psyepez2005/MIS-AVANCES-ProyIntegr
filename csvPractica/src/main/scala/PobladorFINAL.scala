import java.io.{BufferedWriter, FileWriter}
object PobladorFINAL {

  val filePath = "D:\\yeped\\Escritorio\\sqlspracticas\\insert_peli.sql"

  def escapeSQL(valor: String): String = {
    if (valor.isEmpty) "null"
    else valor
      .replaceAll("'", "''") // Escapar comillas simples
      .replaceAll("\"", "\\\"") // Escapar comillas dobles
      .replaceAll("\b", "\\b") // Escapar backspace
      .replaceAll("\n", "\\n") // Escapar nueva línea
      .replaceAll("\r", "\\r") // Escapar retorno de carro
      .replaceAll("\t", "\\t") // Escapar tabulación
      .replaceAll("\u0000", "") // Eliminar caracteres nulos
      .replaceAll("\u001a", "\\Z") // Escapar fin de archivo
  }

  def insertarCollectionDB(listaTuplas: List[(Int, String, String, String)]): Unit = {
    def insertar(tupla: (Int, String, String, String)): String = {
      val collection_id = escapeSQL(tupla._1.toString)
      val nombre = escapeSQL(tupla._2)
      val poster_path = escapeSQL(tupla._3)
      val backdrop_path = escapeSQL(tupla._4)
      s"INSERT INTO collection (collection_id, nombre, poster_path,backdrop_path) VALUES ($collection_id,'$nombre','$poster_path','$backdrop_path');"
    }

    val file = new BufferedWriter(new FileWriter(filePath))
    listaTuplas.foreach { fila =>
      file.write(insertar(fila))
      file.newLine()
    }
    file.close()
  }

  def insertarPeliculaDB(listaTuplas:  List[(Int, String, Boolean, Long, String, String, String, String, Long, String, String, Long, Int, String, String, String, Boolean, Int, Int, Int)]): Unit = {
    def insertar(tupla: (Int, String, Boolean, Long, String, String, String, String, Long, String, String, Long, Int, String, String, String, Boolean, Int, Int, Int)): String = {
      val pelicula_id = tupla._1
      val imdb_id = escapeSQL(tupla._2)
      val adult = tupla._3
      val budget = tupla._4
      val homepage = escapeSQL(tupla._5)
      val og_lang = escapeSQL(tupla._6)
      val og_title = escapeSQL(tupla._7)
      val overview = escapeSQL(tupla._8)
      val popularity = tupla._9
      val poster_pt = escapeSQL(tupla._10)
      val release_dt = escapeSQL(tupla._11)
      val revenue = tupla._12
      val runtime = tupla._13
      val status = escapeSQL(tupla._14)
      val tagline = escapeSQL(tupla._15)
      val title = escapeSQL(tupla._16)
      val video = tupla._17
      val vote_cnt = tupla._18
      val vote_avg = tupla._19
      val coll_id = tupla._20
      s"INSERT INTO pelicula (pelicula_id, imdb_id, adult, budget, homepage, original_language, original_title, overview, popularity, poster_path, release_date, revenue, runtime, estado, tagline, title, video, vote_count, vote_average, collection_id) VALUES ($pelicula_id,'$imdb_id',$adult,$budget,'$homepage','$og_lang','$og_title','$overview',$popularity,'$poster_pt','$release_dt',$revenue,$runtime,'$status','$tagline','$title',$video,$vote_cnt,$vote_avg,$coll_id);"
    }

    val file = new BufferedWriter(new FileWriter(filePath))
    listaTuplas.foreach { fila =>
      file.write(insertar(fila))
      file.newLine()
    }
    file.close()
  }

  def insertarActorDB(listaTuplas: List[(Int, String, Int, String)]): Unit = {
    def insertar(tupla: (Int, String, Int, String)): String = {
      val actor_id = escapeSQL(tupla._1.toString)
      val nombre = escapeSQL(tupla._2)
      val gender = escapeSQL(tupla._3.toString)
      val profile_path = escapeSQL(tupla._4)
      s"INSERT INTO PERSON(actor_id, nombre, gender,profile_path) VALUES ($actor_id,'$nombre',$gender,'$profile_path');"
    }

    val file = new BufferedWriter(new FileWriter(filePath))
    listaTuplas.foreach { fila =>
      file.write(insertar(fila))
      file.newLine()
    }
    file.close()
  }

  def insertarJobDeparDB(listaTuplas: List[(Int, Int, String)]): Unit = {
    def insertar(tupla: (Int, Int, String)): String = {
      val job_id = escapeSQL(tupla._1.toString)
      val department_id = escapeSQL(tupla._2.toString)
      val job_name = escapeSQL(tupla._3)
      s"INSERT INTO PERSON(job_id, department_id, job_name) VALUES ($job_id,$department_id,'$job_name');"
    }

    val file = new BufferedWriter(new FileWriter(filePath))
    listaTuplas.foreach { fila =>
      file.write(insertar(fila))
      file.newLine()
    }
    file.close()
  }

  def insertarEmpleadoDB(listaTuplas: List[(Int, String, Int, String)]): Unit = {
    def insertar(tupla: (Int, String, Int, String)): String = {
      val empleado_id = escapeSQL(tupla._1.toString)
      val nombre = escapeSQL(tupla._2)
      val gender = escapeSQL(tupla._3.toString)
      val profile_path = escapeSQL(tupla._4)
      s"INSERT INTO PERSON(empleado_id, nombre, gender,profile_path) VALUES ($empleado_id,'$nombre',$gender,'$profile_path');"
    }

    val file = new BufferedWriter(new FileWriter(filePath))
    listaTuplas.foreach { fila =>
      file.write(insertar(fila))
      file.newLine()
    }
    file.close()
  }

  def insertarPaisDB(listaTuplas: List[(String, String)]): Unit = {
    def insertar(tupla: (String, String)): String = {
      val iso_3166_1_code = escapeSQL(tupla._1)
      val nombre = escapeSQL(tupla._2)
      s"INSERT INTO PERSON(iso_3166_1_code, nombre) VALUES ('$iso_3166_1_code','$nombre');"
    }

    val file = new BufferedWriter(new FileWriter(filePath))
    listaTuplas.foreach { fila =>
      file.write(insertar(fila))
      file.newLine()
    }
    file.close()
  }

  def insertarGeneroDB(listaTuplas: List[(Int, String)]): Unit = {
    def insertar(tupla: (Int, String)): String = {
      val genre_id = escapeSQL(tupla._1.toString)
      val nombre = escapeSQL(tupla._2)
      s"INSERT INTO PERSON(genre_id, nombre) VALUES ($genre_id,'$nombre');"
    }

    val file = new BufferedWriter(new FileWriter(filePath))
    listaTuplas.foreach { fila =>
      file.write(insertar(fila))
      file.newLine()
    }
    file.close()
  }

  def insertarIdiomaDB(listaTuplas: List[(String, String)]): Unit = {
    def insertar(tupla: (String, String)): String = {
      val iso_639_1_code = escapeSQL(tupla._1)
      val nombre = escapeSQL(tupla._2)
      s"INSERT INTO PERSON(genre_id, nombre) VALUES ('$iso_639_1_code','$nombre');"
    }
    val file = new BufferedWriter(new FileWriter(filePath))
    listaTuplas.foreach { fila =>
      file.write(insertar(fila))
      file.newLine()
    }
    file.close()
  }

  def insertarProdcutoraDB(listaTuplas: List[(Int, String)]): Unit = {
    def insertar(tupla: (Int, String)): String = {
      val productora_id = escapeSQL(tupla._1.toString)
      val nombre = escapeSQL(tupla._2)
      s"INSERT INTO PERSON(genre_id, nombre) VALUES ($productora_id,'$nombre');"
    }

    val file = new BufferedWriter(new FileWriter(filePath))
    listaTuplas.foreach { fila =>
      file.write(insertar(fila))
      file.newLine()
    }
    file.close()
  }

  def insertarUsuarioDB(listaTuplas: List[(Int)]): Unit = {
    def insertar(tupla: (Int)): String = {
      val user_id = escapeSQL(tupla.toString)
      s"INSERT INTO PERSON(user_id) VALUES ($user_id);"
    }

    val file = new BufferedWriter(new FileWriter(filePath))
    listaTuplas.foreach { fila =>
      file.write(insertar(fila))
      file.newLine()
    }
    file.close()
  }

  def insertarCalificacionDB(listaTuplas: List[(Int, Int, Float, Long)]): Unit = {
    def insertar(tupla: (Int, Int, Float, Long)): String = {
      val user_id = escapeSQL(tupla._1.toString)
      val pelicula_id = escapeSQL(tupla._2.toString)
      val rating = escapeSQL(tupla._3.toString)
      val timestamps = escapeSQL(tupla._4.toString)
      s"INSERT INTO PERSON(user_id, pelicula_id, rating,timestamps) VALUES ($user_id,$pelicula_id,$rating,$timestamps);"
    }

    val file = new BufferedWriter(new FileWriter(filePath))
    listaTuplas.foreach { fila =>
      file.write(insertar(fila))
      file.newLine()
    }
    file.close()
  }
  def insertarKeyWordDB(listaTuplas: List[(Int, String)]): Unit = {
    def insertar(tupla: (Int, String)): String = {
      val id_KW = escapeSQL(tupla._1.toString)
      val nombre = escapeSQL(tupla._2)
      s"INSERT INTO PERSON(id_KW, nombre) VALUES ($id_KW,'$nombre');"
    }
    val file = new BufferedWriter(new FileWriter(filePath))
    listaTuplas.foreach { fila =>
      file.write(insertar(fila))
      file.newLine()
    }
    file.close()
  }

  def insertarPeliculaActorDB(listaTuplas: List[(Int, Int, String, Int,String,Int)]): Unit = {
    def insertar(tupla: (Int, Int, String, Int,String,Int)): String = {
      val actor_id = escapeSQL(tupla._1.toString)
      val pelicula_id = escapeSQL(tupla._2.toString)
      val personaje = escapeSQL(tupla._3)
      val order = escapeSQL(tupla._4.toString)
      val credit_id = escapeSQL(tupla._5)
      val cast_id = escapeSQL(tupla._6.toString)
      s"INSERT INTO PERSON(actor_id, pelicula_id, personaje,order,credit_id,cast_id) VALUES ($actor_id,$pelicula_id,'$personaje',$order,'$credit_id',$cast_id);"
    }
    val file = new BufferedWriter(new FileWriter(filePath))
    listaTuplas.foreach { fila =>
      file.write(insertar(fila))
      file.newLine()
    }
    file.close()
  }
  def insertarPeliculaEmpleadoDB(listaTuplas: List[(Int, Int, String, Int)]): Unit = {
    def insertar(tupla: (Int, Int, String, Int)): String = {
      val empleado_id = escapeSQL(tupla._1.toString)
      val pelicula_id = escapeSQL(tupla._2.toString)
      val credit_id = escapeSQL(tupla._3)
      val job_id = escapeSQL(tupla._4.toString)
      s"INSERT INTO PERSON(empleado_id, pelicula_id, credit_id,job_id) VALUES ($empleado_id,$pelicula_id,'$credit_id',$job_id);"
    }
    val file = new BufferedWriter(new FileWriter(filePath))
    listaTuplas.foreach { fila =>
      file.write(insertar(fila))
      file.newLine()
    }
    file.close()
  }
  def insertarPeliculaPaisDB(listaTuplas: List[(String, Int)]): Unit = {
    def insertar(tupla: (String, Int)): String = {
      val iso_3166_1_code  = escapeSQL(tupla._1)
      val pelicula_id = escapeSQL(tupla._2.toString)
      s"INSERT INTO PERSON(iso_3166_1_code, pelicula_id) VALUES ('$iso_3166_1_code',$pelicula_id);"
    }
    val file = new BufferedWriter(new FileWriter(filePath))
    listaTuplas.foreach { fila =>
      file.write(insertar(fila))
      file.newLine()
    }
    file.close()
  }
  def insertarPeliculaGeneroDB(listaTuplas: List[(Int, Int)]): Unit = {
    def insertar(tupla: (Int, Int)): String = {
      val genre_id  = escapeSQL(tupla._1.toString)
      val pelicula_id = escapeSQL(tupla._2.toString)
      s"INSERT INTO PERSON(genre_id, pelicula_id) VALUES ($genre_id,$pelicula_id);"
    }
    val file = new BufferedWriter(new FileWriter(filePath))
    listaTuplas.foreach { fila =>
      file.write(insertar(fila))
      file.newLine()
    }
    file.close()
  }
  def insertarPeliculaIdiomaDB(listaTuplas: List[(String, Int)]): Unit = {
    def insertar(tupla: (String, Int)): String = {
      val iso_639_1_code  = escapeSQL(tupla._1)
      val pelicula_id = escapeSQL(tupla._2.toString)
      s"INSERT INTO PERSON(iso_639_1_code, pelicula_id) VALUES ('$iso_639_1_code',$pelicula_id);"
    }
    val file = new BufferedWriter(new FileWriter(filePath))
    listaTuplas.foreach { fila =>
      file.write(insertar(fila))
      file.newLine()
    }
    file.close()
  }
  def insertarPeliculaProductoraDB(listaTuplas: List[(Int, Int)]): Unit = {
    def insertar(tupla: (Int, Int)): String = {
      val productora_id = escapeSQL(tupla._1.toString)
      val pelicula_id = escapeSQL(tupla._2.toString)
      s"INSERT INTO PERSON(productora_id, pelicula_id) VALUES ($productora_id,$pelicula_id);"
    }
    val file = new BufferedWriter(new FileWriter(filePath))
    listaTuplas.foreach { fila =>
      file.write(insertar(fila))
      file.newLine()
    }
    file.close()
  }
  def insertarPeliculaKeyWordDB(listaTuplas: List[(Int, Int)]): Unit = {
    def insertar(tupla: (Int, Int)): String = {
      val id_KW  = escapeSQL(tupla._1.toString)
      val pelicula_id = escapeSQL(tupla._2.toString)
      s"INSERT INTO PERSON(id_KW, pelicula_id) VALUES ($id_KW,$pelicula_id);"
    }
    val file = new BufferedWriter(new FileWriter(filePath))
    listaTuplas.foreach { fila =>
      file.write(insertar(fila))
      file.newLine()
    }
    file.close()
  }


}