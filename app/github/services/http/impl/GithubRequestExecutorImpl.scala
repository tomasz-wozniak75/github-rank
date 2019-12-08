package github.services.http.impl

import java.util.concurrent.TimeUnit

import github.rank.api.{RateLimit, ToManyRequests}
import github.services.http.api.{GithubRequestExecutor, RequestsExecutor, RequestsExecutorState}
import javax.inject.Inject
import play.api.Configuration
import play.api.libs.ws.{WSClient, WSResponse}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

private class GithubRequestExecutorImpl @Inject()(config: Configuration, wsClient: WSClient) extends RequestsExecutor with GithubRequestExecutor{
  override val state: RequestsExecutorState = {
    val defaultRateLimit = config.get[String]("GH_TOKEN").toInt
    val ipResponse = execute("http://checkip.amazonaws.com/")
    if (ipResponse.status == 200) {
      val ip = ipResponse.body.trim
      new RequestsExecutorState(ip, RateLimit(defaultRateLimit, defaultRateLimit, 0))
    }else {
      throw new IllegalStateException("Missing internet connection")
    }
  }

  def execute(url: String): WSResponse = {
    val response = Await.result[WSResponse](wsClient.url(url).get(), Duration(30, TimeUnit.SECONDS))
    handleRateLimits(url, response)
    response
  }

  private def handleRateLimits(url: String, response: WSResponse) = {
    if (url.startsWith("https://api.github.com")) {
      val rateLimit: Int = response.header("X-RateLimit-Limit").getOrElse[String](state.rateLimit.rateLimit + "").toInt
      val remainingLimit: Int = response.header("X-RateLimit-Remaining").getOrElse[String](state.rateLimit.remainingLimit + "").toInt
      val limitResetTime: Long = response.header("X-RateLimit-Reset").getOrElse[String](state.rateLimit.limitResetTime + "").toLong
      state.rateLimit = RateLimit(rateLimit, remainingLimit, limitResetTime)

      if (response.status == 403 && remainingLimit==0) {
          throw ToManyRequests(state.rateLimit)
      }
    }
  }
}
