package models

import java.util.UUID
import play.api.libs.json._
import com.mohiva.play.silhouette.api.{ Identity, LoginInfo }

case class Game(
  name: String,
  timesPlayed: Int,
  voters: List[String]
)

object Game {
  implicit val jsonFormat = Json.format[Game]
}
