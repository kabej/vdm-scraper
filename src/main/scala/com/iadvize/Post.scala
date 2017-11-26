package com.iadvize


import org.joda.time.LocalDateTime
import org.joda.time.format.{DateTimeFormat, DateTimeFormatter}
import spray.httpx.SprayJsonSupport
import spray.json._

case class Post(id: Int, content: String, date: LocalDateTime, author: String)

case class Posts(posts: List[Post], count: Int)

/**
  * Constructeur pour le calcul auto de la taille de la liste
  */
object Posts {
  def apply(posts: List[Post]): Posts = Posts(posts, posts.size)
}

/**
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
      case _ => throw new DeserializationException(json + "is not ISO Format Date")
    }
  }

  implicit val postFormat = jsonFormat4(Post.apply)
  implicit val postsFormat = jsonFormat2(Posts.apply)
}
