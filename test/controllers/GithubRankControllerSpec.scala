package controllers

import github.rank.api.GithubRanking
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test.Helpers._
import play.api.test._

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 *
 * For more information, see https://www.playframework.com/documentation/latest/ScalaTestingWithScalaTest
 */
class GithubRankControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {



  "GithubRankController GET" should {

    "return list of sorted contributors" in {
      val githubRanking = app.injector.instanceOf[GithubRanking]
      val controller = new GithubRankController(stubControllerComponents(), githubRanking)
      val organisationName = "dummy-org"
      val endpointTemplate = s"/org/${organisationName}/contributors"
      val contributorsResponse = controller.getContributorsRanking(organisationName).apply(FakeRequest(GET, endpointTemplate))

      status(contributorsResponse) mustBe OK
      contentType(contributorsResponse) mustBe Some("application/json")
    }

    "return 404 in case of unknown organisation" in {
      val githubRanking = app.injector.instanceOf[GithubRanking]
      val controller = new GithubRankController(stubControllerComponents(), githubRanking)
      val organisationName = "unknown-dummy-org"
      val endpointTemplate = s"/org/${organisationName}/contributors"
      val contributorsResponse = controller.getContributorsRanking(organisationName).apply(FakeRequest(GET, endpointTemplate))

      status(contributorsResponse) mustBe NOT_FOUND
      contentType(contributorsResponse) mustBe Some("application/json")
    }

    "return 429 in case of too many request" in {
      val githubRanking = app.injector.instanceOf[GithubRanking]
      val controller = new GithubRankController(stubControllerComponents(), githubRanking)
      val organisationName = "dummy-org"
      val endpointTemplate = s"/org/${organisationName}/contributors"
      for(i<- Range(1,4))       {
        val contributorsResponse = controller.getContributorsRanking(organisationName).apply(FakeRequest(GET, endpointTemplate))
        contentType(contributorsResponse) mustBe Some("application/json")
        if(i<4) {
          status(contributorsResponse) mustBe OK
        }else{
          status(contributorsResponse) mustBe TOO_MANY_REQUESTS
        }
      }
    }
  }
}
