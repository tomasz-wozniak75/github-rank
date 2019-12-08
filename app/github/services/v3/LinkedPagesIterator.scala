package github.services.v3

import java.util.NoSuchElementException

import github.services.http.api.LoadBalancer
import play.api.libs.ws.WSResponse

private class LinkedPagesIterator(var nextUrl: String, loadBalacer: LoadBalancer) extends Iterator[WSResponse]{
  var response: WSResponse = null

  private def parseLinkHeader() {
    def parseUrl(urlEntries: Array[String]) = {
      val trimedUrl = urlEntries(0).trim
      trimedUrl.substring(1, trimedUrl.length - 1)
    }
    def parse(link: String): Unit ={
      nextUrl = null
      val linkEntries = link.split(",")
      for(entry <- linkEntries){
        val urlEntries = entry.split(";")
        if (urlEntries.length > 1 && urlEntries(1).trim == """rel="next"""") {
          nextUrl = parseUrl(urlEntries)
        }
      }
    }

    val linkHeader = response.header("Link")
    linkHeader match {
      case Some(link) => parse(link)
      case None => nextUrl = null
    }
  }

  private def loadPage() = {
    if (response == null && nextUrl != null) {
      response = loadBalacer.nextExecutor().execute(nextUrl)
      parseLinkHeader()
    }
  }

  override def hasNext: Boolean = {
    loadPage()
    response != null
  }

  override def next(): WSResponse = {
    loadPage()
    if(response==null){
      throw new NoSuchElementException
    }
    val responseRefCopy = response
    response = null
    responseRefCopy
  }
}
