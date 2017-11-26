package com.iadvize


import com.iadvize.PostProtocol._
import org.joda.time.IllegalFieldValueException
import spray.http.StatusCodes._
import spray.http.{MediaTypes, StatusCodes}
import spray.routing.{ExceptionHandler, HttpService}
import spray.util.LoggingContext

//
/**
  * Endpoint api/posts handlers
  * this trait defines our service behavior independently from the service actor
  */
trait PostRessource extends HttpService with PostDao {

  /**
    * Handler pour les erreurs de parsing de date.
    * On renvoie une 400 Bad Request
    */
  implicit def exceptionHandler(implicit log: LoggingContext) =
    ExceptionHandler {
      case e: IllegalFieldValueException => {
        log.warning("Error encountered while parsing date")
        complete(BadRequest, "Error encountered while parsing date")
      }
    }

  val postRoute =
    pathPrefix("api") {
      respondWithMediaType(MediaTypes.`application/json`) {
        pathPrefix("posts") {
          pathEnd {
            get {
              parameters('from.as[String] ?, 'to.as[String] ?, 'author.as[String] ?).as(Search) { search =>
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
                  case None => complete(NotFound)
                  case _ => complete(post)
                }
              }
            }
        }
      }
    }
}
