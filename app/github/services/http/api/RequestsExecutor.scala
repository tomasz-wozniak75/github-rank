package github.services.http.api

import github.rank.api.{ToManyRequests}
import play.api.libs.ws.WSResponse


trait RequestsExecutor {
  @throws(classOf[ToManyRequests])
  def execute(url: String): WSResponse
}
