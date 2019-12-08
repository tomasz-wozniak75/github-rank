package github.services.api

/**
 * Organisation represents github organisation and allows on repository iteration
 */
trait OrganisationResource {
  val name: String
  def repositories: Iterator[RepositoryResource]
}
