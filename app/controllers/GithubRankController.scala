package controllers

import javax.inject.Inject
import github.rank.api._
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents, Request}


class GithubRankController @Inject()(cc: ControllerComponents, githubRanking: GithubRanking) extends AbstractController(cc) {

  def getContributorsRanking(organisationName: String) = Action { implicit request => {
      try {
        val ranking = githubRanking.calculateRanking(organisationName)
        implicit val contributorWrites = Json.writes[Contribution]
        Ok(Json.toJson(ranking))
      } catch {
        case e: OrganisationNotFound => respondOrganisationNotFound(e)
        case e: ToManyRequests => respondToManyRequests(e)
      }
    }
  }

  private def respondOrganisationNotFound(e: OrganisationNotFound)(implicit request: Request[_]) = {
    NotFound(Json.toJson(e.organisationName))
  }

  private def respondToManyRequests(e: ToManyRequests)(implicit request: Request[_]) = {
    implicit val limitsWrites = Json.writes[RateLimit]
    NotFound(Json.toJson(e.rateLimit))
  }
}
