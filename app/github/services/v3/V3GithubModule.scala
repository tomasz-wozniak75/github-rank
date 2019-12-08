package github.services.v3

import com.google.inject.AbstractModule
import github.services.api.GithubResource


class V3GithubModule extends AbstractModule{
  override def configure(): Unit = {
    bind(classOf[GithubResource]).to(classOf[GithubResourceImpl])
  }
}
