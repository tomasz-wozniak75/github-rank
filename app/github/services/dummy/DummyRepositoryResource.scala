package github.services.dummy

import github.services.api.RepositoryResource
import github.rank.api.Contributor

private class DummyRepositoryResource(override val name: String) extends RepositoryResource{
  override def contributors: Iterator[Contributor] = {
    List(Contributor("john-simth", 75), Contributor("jan-kowalski", 25)).iterator
  }
}
