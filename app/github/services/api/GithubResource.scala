package github.services.api

trait GithubResource {
 def getOrganisation(name: String): OrganisationResource
}
