package controllers

import javax.inject.Inject
import com.ardusjax.webservice.clients.ArtifactoryClient.ArtifactoryWSC
import com.ardusjax.webservice.clients.GitHubClient.GitHubWSC

import scala.concurrent.duration._
import org.joda.time.Seconds
import play.api._
import play.api.libs.ws.{WS, WSClient}
import play.api.Play.current
import play.api.mvc._

import scala.concurrent.Await

object Application  extends Controller {
  def index = Action {
    def githubclient = {
      new GitHubWSC(WS.client, "https://api.github.com")
    }
    val result = Await.result(githubclient.repositories(), 10.seconds )
    Ok(views.html.index(result))
  }
}