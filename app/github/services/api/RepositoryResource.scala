package github.services.api

import github.rank.api.Contribution

trait RepositoryResource {
  val name: String
  def contributors: Iterator[Contribution]
}