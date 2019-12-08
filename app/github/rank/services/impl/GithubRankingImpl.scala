package github.rank.services.impl

import github.rank.api.{Contribution, GithubRanking}
import github.services.api.GithubResource
import javax.inject.Inject
import scala.collection.mutable.Map

private class GithubRankingImpl @Inject()(val githubResource: GithubResource) extends GithubRanking{
  override def calculateRanking(organisationName: String): List[Contribution] = {
    val contributorsMap: Map[String, Contribution] = Map()
    val organisation = githubResource.getOrganisation(organisationName)
    for(repository <- organisation.repositories ){
      for(contributoion <- repository.contributors){
        val previousContributionOption = contributorsMap.get(contributoion.name)
        previousContributionOption match{
          case Some(previousContribution) => contributorsMap(contributoion.name) = contributoion + previousContribution;
          case None => contributorsMap(contributoion.name) = contributoion;
        }
      }
    }

    contributorsMap.values.toList.sortWith( _.contributions > _.contributions)
  }
}
