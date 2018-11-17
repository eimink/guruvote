package controllers

import javax.inject.Inject

import com.mohiva.play.silhouette.api.{ LogoutEvent, Silhouette }
import com.mohiva.play.silhouette.impl.providers.SocialProviderRegistry
import play.api.i18n.{ I18nSupport, MessagesApi }
import play.api.mvc.Controller
import utils.auth.DefaultEnv

import scala.concurrent.Future
import models.daos.GameDAOImpl
import scala.concurrent.ExecutionContext.Implicits.global
import models.services.GameServiceImpl

/**
 * The basic application controller.
 *
 * @param messagesApi The Play messages API.
 * @param silhouette The Silhouette stack.
 * @param socialProviderRegistry The social provider registry.
 * @param webJarAssets The webjar assets implementation.
 */
class ApplicationController @Inject() (
  val messagesApi: MessagesApi,
  silhouette: Silhouette[DefaultEnv],
  socialProviderRegistry: SocialProviderRegistry,
  implicit val webJarAssets: WebJarAssets,
  gameService: GameServiceImpl
)
  extends Controller with I18nSupport {

  /**
   * Handles the index action.
   *
   * @return The result to display.
   */
  def index = silhouette.SecuredAction.async { implicit request =>
    gameService.all flatMap { games =>
      Future.successful(Ok(views.html.home(request.identity, games)))
    }
  }

  /**
   * Handles the Sign Out action.
   *
   * @return The result to display.
   */
  def signOut = silhouette.SecuredAction.async { implicit request =>
    val result = Redirect(routes.ApplicationController.index())
    silhouette.env.eventBus.publish(LogoutEvent(request.identity, request))
    silhouette.env.authenticatorService.discard(request.authenticator, result)
  }

  def vote(user: String, game: String) = silhouette.SecuredAction.async { implicit request =>
    gameService.vote(game, user) flatMap { game =>
      gameService.all flatMap { games =>
        Future.successful(Ok(views.html.home(request.identity, games)))
      }
    }
  }

  def add(game: String) = silhouette.SecuredAction.async { implicit request =>
    gameService.add(game) flatMap { game =>
      gameService.all flatMap { games =>
        Future.successful(Ok(views.html.home(request.identity, games)))
      }
    }
  }
}
