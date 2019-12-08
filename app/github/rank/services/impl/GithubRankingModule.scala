package github.rank.services.impl

import com.google.inject.AbstractModule
import github.rank.api.GithubRanking

class GithubRankingModule extends AbstractModule{
  override def configure(): Unit = {
    bind(classOf[GithubRanking]).to(classOf[GithubRankingImpl])
  }
}
