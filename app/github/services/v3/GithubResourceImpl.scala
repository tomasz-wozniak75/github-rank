package github.services.v3

import github.rank.api.OrganisationNotFound
import github.services.api.{GithubResource, OrganisationResource}
import github.services.http.api.LoadBalancer
import javax.inject.Inject
import play.api.Configuration
import play.api.libs.json.{JsString, Json}

private class GithubResourceImpl @Inject()(config: Configuration, loadBalancer: LoadBalancer) extends GithubResource{
  val gitHubUrl = config.get[String]("GH_URL")
  val organisationsUrlTemplate:String ={
    val response = loadBalancer.nextExecutor().execute(gitHubUrl)
    if (response.status != 200) {
        throw new IllegalStateException("github root object is not available")
    }
    val json = Json.parse(response.body)
    val orgTemplate:JsString = (json \ "organization_url").get.asInstanceOf[JsString]
    orgTemplate.value
  }


  override def getOrganisation(name: String): OrganisationResource = {
    val url = organisationsUrlTemplate.replace("{org}", name)
    val response = loadBalancer.nextExecutor().execute(url)
    if (response.status == 404) {
      throw OrganisationNotFound(name)
    }

    if(response.status >= 400){
      throw new IllegalStateException("github request failed :" +response.body)
    }

    val json = Json.parse(response.body)
    val repoTemplate:JsString = (json \ "repos_url").get.asInstanceOf[JsString]
    new OrganisationResourceImpl(name, url, repoTemplate.value)
  }
}
