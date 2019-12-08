package github.services.http.api

import github.rank.api.ToManyRequests

trait LoadBalancer {
  @throws(classOf[ToManyRequests])
  def nextExecutor(): RequestsExecutor
}
