package github.services.api

import github.rank.api.Contribution

/**
 * Repository represents github repository and allows on contributions iteration
 */
trait RepositoryResource {
  val name: String
  def contributions: Iterator[Contribution]
}
