package github.services.dummy

import github.services.api.RepositoryResource
import github.rank.api.Contribution

private class DummyRepositoryResource(override val name: String) extends RepositoryResource{
  override def contributors: Iterator[Contribution] = {
    List(Contribution("john-simth", 75), Contribution("jan-kowalski", 25)).iterator
  }
}
