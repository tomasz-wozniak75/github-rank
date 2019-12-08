package github.services.v3

import github.rank.api.Contribution
import github.services.api.RepositoryResource
import github.services.http.api.LoadBalancer
import play.api.libs.json.{JsNumber, JsString}

private class RepositoryResourceImpl(override val name: String, contributionsUrl: String, loadBalancer: LoadBalancer) extends RepositoryResource{

  override def contributions: Iterator[Contribution] = {
    new Iterator[Contribution] {
      val itemsIterator = new PageItemsIterator(new LinkedPagesIterator(contributionsUrl, loadBalancer))
      override def hasNext: Boolean = {
        itemsIterator.hasNext
      }

      override def next(): Contribution = {
        val jsObject = itemsIterator.next()
        val login = (jsObject \ "login").get.asInstanceOf[JsString]
        val contributions = (jsObject \ "contributions").get.asInstanceOf[JsNumber]
        Contribution(login.value, contributions.value.toInt)
      }
    }
  }
}
