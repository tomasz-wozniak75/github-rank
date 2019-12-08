package github.services.v3

import github.rank.api.Contribution
import github.services.api.RepositoryResource

private class RepositoryResourceImpl extends RepositoryResource{
  override val name: String = ""

  override def contributors: Iterator[Contribution] = {
    null
  }
}
