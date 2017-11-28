package com.iadvize.vdm.api

import akka.actor.Actor
import com.iadvize.vdm.dao.post.PostDao


/**
  * Created by kbejaoui on 25/11/17.
  *
  * we don't implement our route structure directly in the service actor because
  * we want to be able to test it independently, without having to spin up an actor
  */
class PostActor(postDaoImpl: PostDao) extends Actor with PostRessource {

  override def postDao: PostDao = postDaoImpl

  // the HttpService trait defines only one abstract member, which
  // connects the services environment to the enclosing actor or test
  def actorRefFactory = context

  // this actor only runs our route, but you could add
  // other things here, like request stream processing
  // or timeout handling
  def receive = runRoute(postRoute)
}