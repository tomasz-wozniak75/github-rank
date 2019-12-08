package github.services.dummy

import com.google.inject.AbstractModule
import github.services.api.GithubResource


class DummyGithubModule extends AbstractModule{
  override def configure(): Unit = {
    bind(classOf[GithubResource]).to(classOf[DummyGithubResource])
  }
}
