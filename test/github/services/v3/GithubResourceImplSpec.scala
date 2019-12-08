package github.services.v3

import github.rank.api.OrganisationNotFound
import github.services.api.GithubResource
import github.services.http.api.LoadBalancer
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.test.Injecting

class GithubResourceImplSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {
  "GithubResourceImpl " should {

    "got exception for unknown org" in {
      val thrown = intercept[OrganisationNotFound] {
        val githubResource = app.injector.instanceOf[GithubResource]
        githubResource.getOrganisation("blue-veery-gmbh")
      }
      assert(thrown != null)
    }
  }
}
