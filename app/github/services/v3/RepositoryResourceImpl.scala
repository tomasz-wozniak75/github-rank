package github.services.v3

import github.rank.api.Contribution
import github.services.api.RepositoryResource
import github.services.http.api.LoadBalancer

private class RepositoryResourceImpl(override val name: String, repoUrl: String, loadBalancer: LoadBalancer) extends RepositoryResource{

  override def contributors: Iterator[Contribution] = {
    null
  }
}
