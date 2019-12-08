package github.services.api

import github.rank.api.Contributor

trait RepositoryResource {
  val name: String
  def contributors: Iterator[Contributor]
}
