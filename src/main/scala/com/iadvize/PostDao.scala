package com.iadvize

import java.time.LocalDateTime

import salat.global._
import com.mongodb.casbah.Imports._
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import salat.dao.SalatDAO

trait PostDao extends MongoDBConnection {

  val Formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")

  // Utilisation du SalatDAO pour le code boiler plate
  val dao = new SalatDAO[Post, Int](collection = db("posts")) {}

  case class Search(from: Option[String], to: Option[String], author: Option[String])

  def insertPost(post: Post) = {
    dao.insert(post)
  }

  def getPosts(search: Search): List[Post] = {
    val builder = MongoDBObject.newBuilder
    if (search.author.isDefined) builder += "author" -> search.author
    if (search.from.isDefined) builder += "date" -> MongoDBObject("$gt" -> Formatter.parseLocalDateTime(search.from.get))
    if (search.to.isDefined) builder += "date" -> MongoDBObject("$lt" -> Formatter.parseLocalDateTime(search.to.get))
    dao.find(builder.result).toList
  }

  def getPost(id: Int): Option[Post] = {
    dao.findOne(MongoDBObject("id" -> id))
  }
}
