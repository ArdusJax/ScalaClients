package com.ardusjax.webservice.clients

import javassist.tools.web.BadHttpRequest
import javax.inject.Inject
import com.fasterxml.jackson.annotation.JsonAnyGetter
import org.h2.tools.Recover
import play.api.libs.json
import play.api.libs.json._
import play.api.libs.openid.Errors.BAD_RESPONSE
import play.api.libs.ws.WSClient
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import scala.concurrent.Future
import scala.util.parsing.json.JSONObject
import play.api.libs.functional.syntax._

/**
  * Created by Christopher G. O'Brian on 12/5/15.
  */
class ArtifactoryWSC(ws: WSClient, baseUrl: String) {

  def repositories(): Future[Seq[Repository]] = {
    ws.url(baseUrl + "/repositories").get().map{ response =>
      implicit val ownerReads: Reads[Owner] = (
          (JsPath \ "login").read[String] and
          (JsPath \ "id").read[Int] and
          (JsPath \ "avatar_url").read[String] and
          (JsPath \ "gravatar_id").read[String] and
          (JsPath \ "url").read[String] and
          (JsPath \ "html_url").read[String] and
          (JsPath \ "followers_url").read[String] and
          (JsPath \ "following_url").read[String] and
          (JsPath \ "gists_url").read[String] and
          (JsPath \ "starred_url").read[String] and
          (JsPath \ "subscriptions_url").read[String] and
          (JsPath \ "organizations_url").read[String] and
          (JsPath \ "repos_url").read[String] and
          (JsPath \ "events_url").read[String] and
          (JsPath \ "received_events_url").read[String] and
          (JsPath \ "type").read[String] and
          (JsPath \ "site_admin").read[Boolean])(Owner.apply _)

      implicit val repositoryRead: Reads[Repository] = (
        (JsPath \ "id").read[Int] and
          (JsPath \ "owner").read[Owner] and
          (JsPath \ "name").read[String] and
          (JsPath \ "full_name").read[String] and
          (JsPath \ "description").read[String] and
          (JsPath \ "private").read[Boolean] and
          (JsPath \ "fork").read[Boolean] and
          (JsPath \ "url").read[String] and
          (JsPath \ "html_url").read[String]
        )(Repository.apply _)
      response.json.validate[Seq[Repository]] match {
        case s: JsSuccess[Seq[Repository]] => s.get
        case e: JsError => e.get
      }
    }
  }
  def owners(): Future[Seq[Owner]] = {
    ws.url(baseUrl + "/repositories").get().map{ response =>
      implicit val res: Reads[Owner] = (
        (JsPath \ "login").read[String] and
          (JsPath \ "id").read[Int] and
          (JsPath \ "avatar_url").read[String] and
          (JsPath \ "gravatar_id").read[String] and
          (JsPath \ "url").read[String] and
          (JsPath \ "html_url").read[String] and
          (JsPath \ "followers_url").read[String] and
          (JsPath \ "following_url").read[String] and
          (JsPath \ "gists_url").read[String] and
          (JsPath \ "starred_url").read[String] and
          (JsPath \ "subscriptions_url").read[String] and
          (JsPath \ "organizations_url").read[String] and
          (JsPath \ "repos_url").read[String] and
          (JsPath \ "events_url").read[String] and
          (JsPath \ "received_events_url").read[String] and
          (JsPath \ "type").read[String] and
          (JsPath \ "site_admin").read[Boolean])(Owner.apply _)
      (response.json \\ "owner").map(_.as[Owner])
    }
  }

  case class Owner(login:String,
              id:Int,
              avatar_url:String,
              gravatar_id:String,
              url:String,
              html_url:String,
              followers_url:String,
              following_url:String,
              gists_url:String,
              starred_url:String,
              subscriptions_url:String,
              organizations_url:String,
              repos_url:String,
              events_url:String,
              received_events_url:String,
              `type`:String,
              site_admin:Boolean
             )

  case class Repository(id:Int,
                        owner: Owner,
                        name:String,
                        full_name:String,
                        description:String,
                        `private`:Boolean,
                        fork:Boolean,
                        url:String,
                        html_url:String
                       )
  case class Repositories(repositories: List[Repository])
}