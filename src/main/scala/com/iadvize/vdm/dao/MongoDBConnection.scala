package com.iadvize.vdm.dao

import com.mongodb.casbah.Imports._
import com.mongodb.casbah.commons.conversions.scala.RegisterJodaLocalDateTimeConversionHelpers

/**
  * Created by kbejaoui on 25/11/17.
  */
trait MongoDBConnection {

  // On enregister un convertisseur casbah pour joda time
  // Ça n'existe pas pour le java time de Java 8
  // Obligé d'utiliser joda time sur l'ensemble du projet
  RegisterJodaLocalDateTimeConversionHelpers()

  def initMongoDB : Option[MongoDB] = {
    val mongoClient = MongoClient("mongo", 27017)
    Some(mongoClient("vdm"))
  }
}
