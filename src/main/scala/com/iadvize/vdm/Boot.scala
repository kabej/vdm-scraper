package com.iadvize.vdm

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import akka.pattern.ask
import akka.util.Timeout
import com.iadvize.vdm.api.PostActor
import com.iadvize.vdm.dao.post.PostDaoImpl
import com.iadvize.vdm.scraper.VDMScraper
import spray.can.Http

import scala.concurrent.duration._

object Boot extends App {

  // Instanciation du dao pour injection
  val postDao = new PostDaoImpl
  // Scrape les 200 posts les plus récentes
  new VDMScraper(postDao).scrape(200)
  // Création d'un actor system et du service
  implicit val system = ActorSystem("iadvize-vdm")
  val service = system.actorOf(Props(new PostActor(postDao)), "quote-service")
  // Valeur implicite de timeout
  implicit val timeout = Timeout(5.seconds)
  // Démarre un nouveau serveur
  IO(Http) ? Http.Bind(service, interface = "0.0.0.0", port = 8080)
}
