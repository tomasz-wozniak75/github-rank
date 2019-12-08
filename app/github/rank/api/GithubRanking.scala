package github.rank.api


/**
 * Service API which executes contributions aggregation algorithm for given organisation
 */
trait GithubRanking {
  @throws(classOf[ToManyRequests])
  @throws(classOf[OrganisationNotFound])
  def calculateRanking(organisationName: String): List[Contribution]
}
