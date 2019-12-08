package github.services.api

trait OrganisationResource {
  def repositories: Iterator[RepositoryResource]
}
