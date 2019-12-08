package github.services.http.impl

import com.google.inject.AbstractModule
import github.services.http.api.{GithubRequestExecutor, LoadBalancer}

class ExecutorsModule extends AbstractModule{
  override def configure(): Unit = {
    bind(classOf[GithubRequestExecutor]).to(classOf[GithubRequestExecutorImpl])
    bind(classOf[LoadBalancer]).to(classOf[GratesLimitExecutorsLoadBalancer])
  }
}
