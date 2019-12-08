package github.services.http.impl

import com.google.inject.Inject
import github.rank.api.ToManyRequests
import github.services.http.api.{GithubRequestExecutor, RequestsExecutor, LoadBalancer}


private class GratesLimitExecutorsLoadBalancer @Inject() (executor: GithubRequestExecutor) extends LoadBalancer{
  var executors = List[GithubRequestExecutor](executor)

  override def nextExecutor(): RequestsExecutor = {
    var greatestLimit = 0
    var earlierResetTime: Long = 0
    var selectedExecutor:GithubRequestExecutor = null
    var executorWithEarlierReset:GithubRequestExecutor = null
    for(executor <- executors){
      if (executor.state.rateLimit.remainingLimit == 0) {
        if(executor.state.rateLimit.limitResetTime < earlierResetTime){
          earlierResetTime = executor.state.rateLimit.limitResetTime
          executorWithEarlierReset = executor
        }
      }else{
        if(executor.state.rateLimit.remainingLimit > greatestLimit){
          greatestLimit = executor.state.rateLimit.remainingLimit
          selectedExecutor = executor
        }
      }
    }
    if (selectedExecutor == null) {
      if(executorWithEarlierReset != null) {
        throw ToManyRequests(executorWithEarlierReset.state.rateLimit)
      }else{
        throw new IllegalStateException("http request executor is missing")
      }
    }

    selectedExecutor
  }
}
