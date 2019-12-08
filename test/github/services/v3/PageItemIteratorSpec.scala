package github.services.v3

import github.services.http.api.LoadBalancer
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.test.Injecting

class PageItemIteratorSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {
  "PageItemsIterator " should {

    "iterate over all items" in {
        val loadBalancer = app.injector.instanceOf[LoadBalancer]
        val url = "https://api.github.com/orgs/blue-veery-gmbh/repos?per_page=1&page=1"
        val iterator = new LinkedPagesIterator(url, loadBalancer)
        val itemIterator = new PageItemsIterator(iterator)

        for(item <- itemIterator){
            println(item)
        }
    }

    "iterate over all items when there is one page" in {
      val loadBalancer = app.injector.instanceOf[LoadBalancer]
      val url = "https://api.github.com/orgs/blue-veery-gmbh/repos"
      val iterator = new LinkedPagesIterator(url, loadBalancer)
      val itemIterator = new PageItemsIterator(iterator)

      for(item <- itemIterator){
        println(item)
      }
    }


  }
}
