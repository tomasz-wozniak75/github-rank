package github.services.http.api

import github.rank.api.ToManyRequests

trait RequestsLoadBalancer {
  @throws(classOf[ToManyRequests])
  def nextExecutor(): RequestsExecutor
}
