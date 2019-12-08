package github.rank.api

trait HttpError{
  val code: Int
}
final case class OrganisationNotFound(organisationName: String, code:Int = 404) extends RuntimeException with HttpError
final case class ToManyRequests(rateLimit: RateLimit, code:Int = 429) extends RuntimeException with HttpError
