package github.services.v3

import github.services.http.api.LoadBalancer
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.test.Injecting

class LinkedPagesIteratorSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {
  "LinkedPagesIterator " should {

    "iterate over all pages" in {
        val loadBalancer = app.injector.instanceOf[LoadBalancer]
        val url = "https://api.github.com/orgs/blue-veery-gmbh/repos?per_page=1&page=1"
        val iterator = new LinkedPagesIterator(url, loadBalancer)
        for(response <- iterator){
            assert(response.status == 200)
        }
    }

    "iterate over pages when lin header is missing" in {
      val loadBalancer = app.injector.instanceOf[LoadBalancer]
      val url = "https://api.github.com/orgs/blue-veery-gmbh/repos"
      val iterator = new LinkedPagesIterator(url, loadBalancer)
      for(response <- iterator){
        assert(response.status == 200)
      }
    }
  }
}
