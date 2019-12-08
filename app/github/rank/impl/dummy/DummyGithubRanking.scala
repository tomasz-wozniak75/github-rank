package github.rank.impl.dummy

import github.rank.api.{Contributor, GithubRanking, OrganisationNotFound, RateLimit, ToManyRequests}

private class DummyGithubRanking extends GithubRanking{
  var counter = 0;
  override def calculateRanking(organisationName: String): List[Contributor] = {
    if ("dummy-org" == organisationName) {
      counter += 1
      if(counter > 4) {
        throw ToManyRequests(RateLimit(3, 0, System.currentTimeMillis()))
      }
      List(Contributor("john-simth", 75), Contributor("jan-kowalski", 25))
    }else{
      throw OrganisationNotFound(organisationName)
    }
  }
}
