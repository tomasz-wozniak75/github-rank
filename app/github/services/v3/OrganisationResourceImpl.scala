package github.services.v3

import github.services.api.{OrganisationResource, RepositoryResource}
import github.services.http.api.LoadBalancer
import play.api.libs.json.JsString

private class OrganisationResourceImpl(name: String, url: String, repoUrl: String, loadBalancer: LoadBalancer) extends OrganisationResource{
  override def repositories: Iterator[RepositoryResource] = {
    new Iterator[RepositoryResource]{
      val itemsIterator = new PageItemsIterator(new LinkedPagesIterator(repoUrl, loadBalancer))
      override def hasNext: Boolean = {
        itemsIterator.hasNext
      }

      override def next(): RepositoryResource = {
        val jsObject = itemsIterator.next()
        val repoName = (jsObject \ "name").get.asInstanceOf[JsString]
        val contributionsUrl = (jsObject \ "contributors_url").get.asInstanceOf[JsString]
        new RepositoryResourceImpl(repoName.value, contributionsUrl.value, loadBalancer)
      }
    }
  }
}
