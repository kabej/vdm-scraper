package com.iadvize

import com.mongodb.casbah.commons.conversions.scala.RegisterJodaLocalDateTimeConversionHelpers
import org.joda.time.LocalDateTime

/**
  * Created by kbejaoui on 25/11/17.
  */
trait MongoDBConnection {

  import com.mongodb.casbah.Imports._

  // On enregister un convertisseur casbah pour joda time
  // Ça n'existe pas pour le java time de Java 8
  // Obligé d'utiliser joda time sur l'ensemble du projet
  RegisterJodaLocalDateTimeConversionHelpers()


  val mongoClient = MongoClient("localhost", 27017)
  val db = mongoClient("vdm")
  val postsCollection = db("posts")

  postsCollection.remove(MongoDBObject.empty)

  val a = MongoDBObject("id" -> 1, "content" -> "AAAAAA", "date" -> LocalDateTime.now(), "author" -> "Ben")
  val b = MongoDBObject("id" -> 2, "content" -> "BBBBBB", "date" -> LocalDateTime.now(), "author" -> "Nuts")
  val c = MongoDBObject("id" -> 3, "content" -> "CCCCCC", "date" -> LocalDateTime.now(), "author" -> "Pocky")

  postsCollection.insert(a)
  postsCollection.insert(b)
  postsCollection.insert(c)

  val allDocs = postsCollection.find()
  println(allDocs)
  for (doc <- allDocs) println(doc)

}
