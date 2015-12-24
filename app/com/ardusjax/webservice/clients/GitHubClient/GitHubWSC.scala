package com.ardusjax.webservice.clients.GitHubClient

import play.api.libs.json.{JsError, JsSuccess, Json}
import com.ardusjax.webservice.clients.GitHubClient.models.{Owner, Repository, Repositories}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json._
import play.api.libs.ws.WSClient
import scala.concurrent.Future

/**
  * Created by Christopher G. O'Brian on 12/22/15.
  */
class GitHubWSC(ws: WSClient, baseUrl: String) {
  def repositories(): Future[Seq[Repository]] = {
    ws.url(baseUrl + "/repositories").get().map{ response =>
      implicit val ownerReads = Json.reads[Owner]
      implicit val repositoryRead = Json.reads[Repository]

      response.json.validate[Seq[Repository]] match {
        case s: JsSuccess[Seq[Repository]] => s.get
        case e: JsError => e.get
      }
    }
  }
  def owners(): Future[Seq[Owner]] = {
    ws.url(baseUrl + "/repositories").get().map{ response =>
      implicit val res = Json.reads[Owner]
      (response.json \\ "owner").map(_.as[Owner])
    }
  }
}
