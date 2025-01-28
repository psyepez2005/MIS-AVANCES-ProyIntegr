package caseClassesJson

import caseClassesJson.*
import play.api.libs.json._


object ejercicio extends App{
  implicit val formato_Rating: OFormat[caseClassesJson.ratings] = Json.format[ratings]
  
}
