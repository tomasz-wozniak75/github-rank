package github.services.http.api

import github.rank.api.RateLimit

class RequestsExecutorState(val id: String, var rateLimit: RateLimit)

trait GithubRequestExecutor extends RequestsExecutor {
  val state: RequestsExecutorState
}
