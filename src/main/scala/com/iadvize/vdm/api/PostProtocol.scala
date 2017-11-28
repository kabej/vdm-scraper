package com.iadvize.vdm.api

import com.iadvize.vdm.domain.{Post, Posts}
import org.joda.time.LocalDateTime
import org.joda.time.format.{DateTimeFormat, DateTimeFormatter}
import spray.httpx.SprayJsonSupport
import spray.json.{DefaultJsonProtocol, DeserializationException, JsString, JsValue, RootJsonFormat}

/**
  * Created by kbejaoui on 27/11/17.
  *
  * Marshlling/Unmarshalling des objects Post et Posts
  */
object PostProtocol extends DefaultJsonProtocol with SprayJsonSupport {

  implicit object DateJsonFormat extends RootJsonFormat[LocalDateTime] {
    private val parserISO: DateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")

    override def write(obj: LocalDateTime) = {
      JsString(parserISO.print(obj))
    }

    override def read(json: JsValue): LocalDateTime = json match {
      case JsString(s) => LocalDateTime.parse(json.toString())
      case _ => throw DeserializationException(s"Date $json is not ISO Format Date")
    }
  }

  implicit val postFormat = jsonFormat4(Post.apply)
  implicit val postsFormat = jsonFormat2(Posts.apply)

}
