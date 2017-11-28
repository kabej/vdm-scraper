package com.iadvize.vdm.dao.post

import com.iadvize.vdm.dao.MongoDBConnection
import com.iadvize.vdm.domain.Post
import com.mongodb.casbah.Imports._
import org.joda.time.format.DateTimeFormat
import salat.dao.SalatDAO
import salat.global._

class PostDaoImpl extends PostDao with MongoDBConnection {

  val db = initMongoDB.get

  // Utilisation du SalatDAO pour le code boiler plate
  val dao = new SalatDAO[Post, Int](collection = db("posts")) {}

  override def insertPost(post: Post) = {
    dao.insert(post)
  }

  override def deleteAll() = {
    dao.remove(MongoDBObject.empty)
  }

  override def getPosts(search: PostSearch): List[Post] = {

    val ISOFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")

    val builder = MongoDBObject.newBuilder
    if (search.author.isDefined) builder += "author" -> search.author
    if (search.from.isDefined) builder += "date" -> MongoDBObject("$gt" -> ISOFormatter.parseLocalDateTime(search.from.get))
    if (search.to.isDefined) builder += "date" -> MongoDBObject("$lt" -> ISOFormatter.parseLocalDateTime(search.to.get))
    dao.find(builder.result).toList
  }

  override def getPost(id: Int): Option[Post] = {
    dao.findOne(MongoDBObject("id" -> id))
  }
}
