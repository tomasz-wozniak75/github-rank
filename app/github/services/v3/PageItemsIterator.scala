package github.services.v3

import play.api.libs.json.{JsArray, JsObject, JsValue, Json}
import play.api.libs.ws.WSResponse

private class PageItemsIterator(pageIterator: Iterator[WSResponse]) extends Iterator[JsObject]{
  var jsValueList: IndexedSeq[JsValue] = null
  var i = -1
  override def hasNext: Boolean = {
    if (jsValueList == null && pageIterator.hasNext) {
      val array = Json.parse(pageIterator.next().body).asInstanceOf[JsArray]
      jsValueList = array.value.toIndexedSeq
      i = 0;
    }

    hasNextItem
  }

  override def next(): JsObject = {
    if(!hasNextItem){
      throw new NoSuchElementException
    }

    val value = jsValueList(i)
    i += 1
    if(i==jsValueList.size){
      jsValueList = null
    }
    value.asInstanceOf[JsObject]
  }

  private def hasNextItem = {
    jsValueList != null && jsValueList.size > i
  }
}
