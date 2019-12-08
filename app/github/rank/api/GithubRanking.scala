package github.rank.api


trait GithubRanking {
  @throws(classOf[ToManyRequests])
  @throws(classOf[OrganisationNotFound])
  def calculateRanking(organisationName: String): List[Contribution]
}
