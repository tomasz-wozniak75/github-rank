package github.rank.services.dummy

import com.google.inject.AbstractModule
import github.rank.api.GithubRanking

class DummyModule extends AbstractModule{
  override def configure(): Unit = {
    bind(classOf[GithubRanking]).to(classOf[DummyGithubRanking])
  }
}
