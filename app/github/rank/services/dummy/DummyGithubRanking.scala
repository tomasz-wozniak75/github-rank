package github.rank.services.dummy

import github.rank.api.{Contribution, GithubRanking, OrganisationNotFound, RateLimit, ToManyRequests}

private class DummyGithubRanking extends GithubRanking{
  var counter = 0;
  override def calculateRanking(organisationName: String): List[Contribution] = {
    if ("dummy-org" == organisationName) {
      counter += 1
      if(counter > 4) {
        throw ToManyRequests(RateLimit(3, 0, System.currentTimeMillis()))
      }
      List(Contribution("john-simth", 75), Contribution("jan-kowalski", 25))
    }else{
      throw OrganisationNotFound(organisationName)
    }
  }
}
