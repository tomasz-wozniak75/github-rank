package github.services.v3

import github.services.http.api.LoadBalancer
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.test.Injecting

class RepositoryResourceImplSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {
    "RepositoryResourceImplSpec" should {

      "should return iterator to contributions" in {
        val loadBalancer = app.injector.instanceOf[LoadBalancer]
        val name = "blue-veery-gmbh"
        val orgUrl = "https://api.github.com/orgs/blue-veery-gmbh"
        val reposUrl = "https://api.github.com/orgs/blue-veery-gmbh/repos"
        val organisation = new OrganisationResourceImpl(name, orgUrl, reposUrl, loadBalancer)
        for (repo <- organisation.repositories) {
          println(repo.name)
          for(contribution <- repo.contributions){
            println(contribution.name + " " + contribution.contributions)
          }
        }
      }
    }
}
