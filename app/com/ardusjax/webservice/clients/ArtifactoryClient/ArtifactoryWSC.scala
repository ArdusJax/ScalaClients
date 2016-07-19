package com.ardusjax.webservice.clients.ArtifactoryClient

import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json._
import play.api.libs.ws.WSClient
import scala.concurrent.Future
import com.ardusjax.webservice.clients.ArtifactoryClient.models._

/**
  * Created by Christopher G. O'Brian on 12/5/15.
  */

class ArtifactoryWSC(ws: WSClient, baseUrl: String) {
  def build(): Future[Builds] = {
    ws.url(baseUrl + "/api/build").get().map{ response =>
      implicit val buildLinksReads = Json.reads[BuildNumber]
      implicit val buildReads = Json.reads[Builds]

      response.json.validate[Builds] match {
        case s: JsSuccess[Builds] => s.get
        case e: JsError => e.get
      }
    }
  }
  def buildRunss(buildName: String = "Android.TripIt.Paid") = {
    ws.url(baseUrl + s"/api/build/$buildName").get().map{ response =>
      implicit val buildLinksReads = Json.reads[BuildNumber]
      implicit val buildReads = Json.reads[Builds]

      response.json.validate[Builds] match {
        case s: JsSuccess[Builds] => s.get
        case e: JsError =>  JsError.toJson(e)
      }
    }.recover{ case e: Exception => val exception = Map("error" -> Seq(e.getMessage))}
  }

  def buildRuns(buildName: String = "Android.TripIt.Paid") = {
    val f = ws.url(baseUrl + s"/api/build/$buildName").get()
    f recover {
      case e: Exception => val exceptionData = Map("error" -> Seq(e.getMessage))
    }
    f map{
      response => {
        implicit val buildLinksReads = Json.reads[BuildNumber]
        implicit val buildReads = Json.reads[Builds]

        response.json.validate[Builds] match {
          case s: JsSuccess[Builds] => s
          case e: JsError => e.errors.map{error => error}
        }
      }
    }
  }
}