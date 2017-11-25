package com.iadvize


import java.time.LocalDateTime
import java.util.Date

import com.iadvize.PostProtocol._
import spray.http.StatusCodes._
import spray.http.MediaTypes
import spray.routing.HttpService

//
/**
  * Endpoint api/posts handlers
  * this trait defines our service behavior independently from the service actor
  */
trait PostRessource extends HttpService with PostDao {

  val postRoute =
    pathPrefix("api") {
      respondWithMediaType(MediaTypes.`application/json`) {
        pathPrefix("posts") {
          pathEnd {
            get {
              parameters('to.as[String] ?, 'from.as[String] ?, 'author.as[String] ?).as(Search) { search =>
                complete {
                  Posts(getPosts(search))
                }
              }
            }
          } ~
            path(IntNumber) { id =>
              get {
                def post = getPost(id)
                post match {
                  case null => complete(NotFound)
                  case _ => complete(post)
                }
              }
            }
        }
      }
    }
}
