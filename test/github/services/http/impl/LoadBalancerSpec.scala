package github.services.http.impl

import github.services.http.api.LoadBalancer
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.test.Injecting

class LoadBalancerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting  {
  "LoadBalancer " should {

    "return executor" in {
      val loadBalancer = app.injector.instanceOf[LoadBalancer]
      loadBalancer.nextExecutor()
    }

    "executor should make http request" in {
      val loadBalancer = app.injector.instanceOf[LoadBalancer]
      val response = loadBalancer.nextExecutor().execute("https://api.github.com")
      response.status

    }
  }
}
