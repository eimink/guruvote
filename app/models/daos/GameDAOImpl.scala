package models.daos

import java.util.UUID
import javax.inject._
import com.mohiva.play.silhouette.api.LoginInfo
import models.Game
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.libs.json._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import reactivemongo.api._
import play.modules.reactivemongo.json._
import play.modules.reactivemongo._
import reactivemongo.play.json.collection.JSONCollection

/**
 * Give access to the user object.
 */
class GameDAOImpl @Inject() (val reactiveMongoApi: ReactiveMongoApi) extends GameDAO {

  def collection: Future[JSONCollection] = reactiveMongoApi.database.map(_.collection("silhouette.game"))

  def find(name: String): Future[Option[Game]] = {
    val query = Json.obj("name" -> name)
    collection.flatMap(_.find(query).one[Game])
  }

  def all: Future[List[Game]] = {
    val query = Json.obj()
    collection.flatMap(_.find(query).cursor[Game]().collect[List]())
  }

  def save(game: Game): Future[Game] = {
    collection.flatMap(_.update(Json.obj("name" -> game.name), game, upsert = true))
    Future.successful(game)
  }

  def vote(gameName: String, voter: String): Future[Option[Game]] = {
    collection.flatMap(_.update(Json.obj("name" -> gameName), Json.obj("$addToSet" -> Json.obj("voters" -> voter)))) flatMap { x =>
      find(gameName)
    }
  }
}

