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
    "get all repositories and check the first repo's Owner object attributes" in {
      withGitHubClient{ client =>
        val result = Await.result(client.repositories(), 10.seconds)
        val owner = result.seq.head.owner
        owner.login must_== "octocat"
        owner.id must_== 1
        owner.avatar_url must_== "https://github.com/images/error/octocat_happy.gif"
        owner.gravatar_id must_== ""
        owner.url must_== "https://api.github.com/users/octocat"
        owner.html_url must_== "https://github.com/octocat"
        owner.followers_url must_== "https://api.github.com/users/octocat/followers"
        owner.following_url must_== "https://api.github.com/users/octocat/following{/other_user}"
        owner.gists_url must_== "https://api.github.com/users/octocat/gists{/gist_id}"
        owner.starred_url must_== "https://api.github.com/users/octocat/starred{/owner}{/repo}"
        owner.subscriptions_url must_== "https://api.github.com/users/octocat/subscriptions"
        owner.organizations_url must_== "https://api.github.com/users/octocat/orgs"
        owner.repos_url must_== "https://api.github.com/users/octocat/repos"
        owner.events_url must_== "https://api.github.com/users/octocat/events{/privacy}"
        owner.received_events_url must_== "https://api.github.com/users/octocat/received_events"
        owner.`type` must_== "User"
        owner.site_admin must_== false
      }
    }
  }

  "GitHubClient" should {
    "get all repositories and check the first repo's attributes." in {
      withGitHubClient{ client =>
        val result = Await.result(client.repositories(), 10.seconds)
        val repo = result.seq.head
        repo.id must_== 1296269
        repo.name must_== "Hello-World"
        repo.full_name must_== "octocat/Hello-World"
        repo.description must_== "This your first repo!"
        repo.`private` must_== false
        repo.fork must_== false
        repo.url must_== "https://api.github.com/repos/octocat/Hello-World"
        repo.html_url must_== "https://github.com/octocat/Hello-World"
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
