package github.services.v3

import github.services.api.{OrganisationResource, RepositoryResource}

private class OrganisationResourceImpl extends OrganisationResource{
  override def repositories: Iterator[RepositoryResource] = {
    null
  }
}
