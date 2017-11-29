package com.iadvize.vdm.dao

import com.mongodb.casbah.Imports._
import com.mongodb.casbah.commons.conversions.scala.RegisterJodaLocalDateTimeConversionHelpers

/**
  * Created by kbejaoui on 25/11/17.
  */
trait MongoDBConnection {

  val host = scala.util.Properties.envOrElse("HOST", "localhost" )

  // On enregister un convertisseur casbah pour joda time
  // Ça n'existe pas pour le java time de Java 8
  // Obligé d'utiliser joda time sur l'ensemble du projet
  RegisterJodaLocalDateTimeConversionHelpers()

  def initMongoDB : Option[MongoDB] = {
    val mongoClient = MongoClient(host, 27017)
    Some(mongoClient("vdm"))
  }
}
