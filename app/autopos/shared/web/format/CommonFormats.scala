package autopos.shared.web.format

import play.api.libs.functional.syntax._
import play.api.libs.json.Reads.{maxLength, minLength, verifying}
import play.api.libs.json._


object CommonReads {

  def min1AndMax50String(field: String) = (__ \ field).read(minLength[String](1) ~> maxLength[String](50))

  def max250String(field: String) = (__ \ field).readNullable(maxLength[String](250))

  def requiredAndGreaterThanZero(field: String) = (__ \ field).read(verifying[BigDecimal](_ > 0))

  def requiredInt(field: String) = (__ \ field).read[Int]

  def optionalId(field: String) = (__ \ field).readNullable[Long]

}


