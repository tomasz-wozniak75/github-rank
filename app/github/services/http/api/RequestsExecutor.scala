package github.services.http.api

import github.rank.api.{RateLimit, ToManyRequests}
import play.api.libs.json.JsObject

case class RequestsExecutorState(id: String,  rateLimit: RateLimit)

trait RequestsExecutor {
  val state: RequestsExecutorState
  @throws(classOf[ToManyRequests])
  def execute(url: String): (Map[String, scala.collection.Seq[String]], JsObject)
}
