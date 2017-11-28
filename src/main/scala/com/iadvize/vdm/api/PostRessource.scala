package com.iadvize.vdm.api

import com.iadvize.vdm.api.PostProtocol._
import com.iadvize.vdm.dao.post.{PostDao, PostSearch}
import com.iadvize.vdm.domain.Posts
import org.joda.time.IllegalFieldValueException
import spray.http.MediaTypes
import spray.http.StatusCodes._
import spray.routing.{ExceptionHandler, HttpService}
import spray.util.LoggingContext

/**
  * Created by kbejaoui on 25/11/17.
  *
  * Endpoint api/posts handlers
  * this trait defines our service behavior independently from the service actor
  */
trait PostRessource extends HttpService{

  def postDao: PostDao

  /**
    * Handler pour les erreurs de parsing de date.
    * On renvoie une 400 Bad Request
    */
  implicit def exceptionHandler(implicit log: LoggingContext) =
  ExceptionHandler {
    case _: IllegalFieldValueException | _: IllegalArgumentException =>
      log.warning("Error encountered while parsing date")
      complete(BadRequest, "Error encountered while parsing date")
  }

  val postRoute =
    pathPrefix("api") {
      respondWithMediaType(MediaTypes.`application/json`) {
        pathPrefix("posts") {
          pathEnd {
            get {
              parameters('from.as[String] ?, 'to.as[String] ?, 'author.as[String] ?).as(PostSearch) { search =>
                complete {
                  Posts(postDao.getPosts(search))
                }
              }
            }
          } ~
            path(IntNumber) { id =>
              get {
                def post = postDao.getPost(id)
                post match {
                  case None => complete(NotFound)
                  case _ => complete(post)
                }
              }
            }
        }
      }
    }
}
