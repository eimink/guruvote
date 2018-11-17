package models.services

import javax.inject.Inject

import play.api.libs.concurrent.Execution.Implicits._

import scala.concurrent.Future
import models.Game
import models.daos.GameDAOImpl
import play.Logger

/**
 * Handles actions to users.
 *
 * @param userDAO The user DAO implementation.
 */
class GameServiceImpl @Inject() (gameDAO: GameDAOImpl) extends GameService {

  def retrieve(name: String) = gameDAO.find(name)

  def all: Future[List[Game]] = gameDAO.all

  /**
   * Saves a game.
   *
   * @param user The user to save.
   * @return The saved user.
   */
  def save(game: Game) = gameDAO.save(game)

  def vote(gameName: String, voter: String) = {
    Logger.debug(s"$gameName $voter")
    gameDAO.vote(gameName, voter)
  }

  def add(game: String) = {
    save(Game(game, 0, List.empty))
  }

}
