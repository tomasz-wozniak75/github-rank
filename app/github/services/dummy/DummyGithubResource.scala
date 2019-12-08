package github.services.dummy

import github.services.api.{GithubResource, OrganisationResource, RepositoryResource}

private class DummyGithubResource extends GithubResource{
  override def getOrganisation(name: String): OrganisationResource = new DummyOrganisationResource(name)
}
