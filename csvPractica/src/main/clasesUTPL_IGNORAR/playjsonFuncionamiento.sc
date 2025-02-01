import play.api.libs.json._

val jsonStringLimpio: String = """{"nombre": "Juan", "edad": 25}"""
val jsonStringLimpio2: String = """{"nombre":        "Jose",       "edad"  :    18}""" //los espacios NO dañan el json
val jsonStringSucio: String = """{"nombre" = "Juan" - "edad" = 25}"""
val jsonBueno: JsValue = Json.parse(jsonStringLimpio) // .parse recibe un string y devuelve un JsValue
val jsonBueno2: JsValue = Json.parse(jsonStringLimpio2)
val jsonMalo: JsValue = Json.parse(jsonStringSucio) //da error xq el json esta mal escrito
println(jsonBueno) // Imprime: {"nombre":"Juan","edad":25}
println(jsonMalo) // no imprime nd xq da error y ni siquiera se puede declarar jsonMalo


///////////////////////////////////////////////////////////////////////////////////////////////


val datojson = """{"nombre": "Juan", "edad": 25, "direccion": {"ciudad": "Quito", "pais": "Ecuador"}}"""
val json: JsValue = Json.parse(datojson)

// Acceder a valores simples
val nombre : JsLookupResult = (json \ "nombre") // Devuelve un JsValue con "Juan", el valor es de tipo [JsLookupResult]
val edad : JsLookupResult = (json \ "edad")     // Devuelve un JsValue con 25, el valor es de tipo [JsLookupResult]
val ciudad : JsLookupResult = (json \ "direccion" \ "ciudad") // Anidado: devuelve JsValue con "Quito", el valor es de tipo [JsLookupResult]

println(nombre)  // "Juan"
println(edad)    // 25
println(ciudad)  // "Quito"

// Transformar a tipos de dato normales
val nombreStr: String = (json \ "nombre").as[String]
val edadInt: Int = (json \ "edad").as[Int]
val ciudadStr: String = (json \ "direccion" \ "ciudad").as[String]


//////////////////////////////////////////////////////////////////////////////////////////////

val datojson2 = """{"nombres": ["Juan", "Maria", "Carlos"]}"""
val json2: JsValue = Json.parse(datojson2)

// Extraer la lista como JsArray
val nombresArray: JsArray = (json2 \ "nombres").as[JsArray] //JsArray se usa con conjuntos de datos de x tipo
println(nombresArray) // salida = ["Juan","Maria","Carlos"]

// Convertir la lista a una secuencia de Strings
val nombresList: List[String] = nombresArray.as[List[String]]

println(nombresList) // salida = List(Juan, Maria, Carlos)

