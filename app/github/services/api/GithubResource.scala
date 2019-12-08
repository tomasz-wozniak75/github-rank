package github.services.api

/**
 * representation of github main object, it allows to fetch organisation
 */
trait GithubResource {
 def getOrganisation(name: String): OrganisationResource
}
