package github.services.dummy

import github.services.api.{OrganisationResource, RepositoryResource}

private class DummyOrganisationResource(val name:String) extends OrganisationResource{
  override def repositories: Iterator[RepositoryResource] = {
    val repoList = List(new DummyRepositoryResource("a"), new DummyRepositoryResource("a"))
    repoList.iterator
  }
}
