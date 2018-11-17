package models.services

import java.util.UUID

import com.mohiva.play.silhouette.api.services.IdentityService
import com.mohiva.play.silhouette.impl.providers.CommonSocialProfile
import models.User

import scala.concurrent.Future
import models.Game

trait GameService {

  def retrieve(name: String): Future[Option[Game]]

  def all: Future[List[Game]]

  def save(game: Game): Future[Game]

  def add(game: String): Future[Game]

}
