/**
  * Created by Christopher G. O'Brian on 12/24/15.
  */

import com.ardusjax.webservice.clients.GitHubClient.GitHubWSC
import com.ardusjax.webservice.clients.GitHubClient.models.Repository
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import play.core.server.Server
import play.api.Play
import play.api.routing.sird._
import play.api.mvc._
import play.api.libs.json._
import play.api.test._

import scala.concurrent.Await
import scala.concurrent.duration._

import org.specs2.mutable.Specification

@RunWith(classOf[JUnitRunner])
class GitHubClientSpec extends Specification{

  "GitHubClient" should {
    "get all repositories and check the first repo's full name" in {
      withGitHubClient{ client =>
        val result = Await.result(client.repositories(), 10.seconds)
        result.seq.head.full_name must_== "octocat/Hello-World"
      }
    }
  }

  "GitHubClient" should {
    "get all repositories and check the first repo's name" in {
      withGitHubClient{ client =>
        val result = Await.result(client.repositories(), 10.seconds)
        result.seq.head.name must_== "Hello-World"
      }
    }
  }

  def withGitHubClient[T](block: GitHubWSC => T): T = {
    Server.withRouter() {
      case GET(p"/repositories") => Action {
        Results.Ok.sendResource("github/repositories.json")
      }
    } { implicit port =>
      implicit val materializer = Play.current
      WsTestClient.withClient { client =>
        block(new GitHubWSC(client, ""))
      }
    }
  }
}
