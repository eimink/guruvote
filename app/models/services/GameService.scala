package models.services

import java.util.UUID

import com.mohiva.play.silhouette.api.services.IdentityService
import com.mohiva.play.silhouette.impl.providers.CommonSocialProfile
import models.User

import scala.concurrent.Future
import models.Game

/**
 * Handles actions to users.
 */
trait GameService {

  /**
   * Retrieves a user that matches the specified ID.
   *
   * @param id The ID to retrieve a user.
   * @return The retrieved user or None if no user could be retrieved for the given ID.
   */
  def retrieve(name: String): Future[Option[Game]]

  def all: Future[List[Game]]

  /**
   * Saves a user.
   *
   * @param user The user to save.
   * @return The saved user.
   */
  def save(game: Game): Future[Game]

}
