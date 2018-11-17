package models.daos

import java.util.UUID

import com.mohiva.play.silhouette.api.LoginInfo
import models.User

import scala.concurrent.Future
import models.Game

/**
 * Give access to the user object.
 */
trait GameDAO {

  /**
   * Finds a user by its login info.
   *
   * @param loginInfo The login info of the user to find.
   * @return The found user or None if no user for the given login info could be found.
   */
  def find(name: String): Future[Option[Game]]

  /**
   * Finds a user by its user ID.
   *
   * @param userID The ID of the user to find.
   * @return The found user or None if no user for the given ID could be found.
   */
  def all: Future[List[Game]]

  /**
   * Saves a user.
   *
   * @param user The user to save.
   * @return The saved user.
   */
  def save(game: Game): Future[Game]
}
