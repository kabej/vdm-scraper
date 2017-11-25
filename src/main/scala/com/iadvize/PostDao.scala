package com.iadvize

import java.time.LocalDateTime

trait PostDao {

  case class Search(from: Option[String], to: Option[String], author: Option[String])

  def posts = List[Post](Post(1, "DDDDDDDDDDDDDDDDDD!", LocalDateTime.now(), "Ben"),
    Post(2, "CCCCCCCCCCCCCCCCCCCCCCCCCCC!", LocalDateTime.now(), "Nuts"),
    Post(3, "BBBBBBBBBBBBBBBBBBBBBBBBBBB!", LocalDateTime.now(), "Pocky"),
    Post(4, "AAAAAAAAAAAAAAAAAAAAAAAAAAA!", LocalDateTime.now(), "Rocky"))


  def insertPost(post: Post): String = {
    "New post received. " + "\n Id : " + post.id + "\n Content : " + post.content + "\n Date : " + post.date + "\n Author" + post.author + "\n"
  }

  def getPosts(search: Search): List[Post] = {
    posts
  }

  def getPost(id: Int): Post = {
    def postsFiltered = posts.filter(_.id == id)
    if (postsFiltered.nonEmpty) {
      return postsFiltered.head
    }
    null
  }
}
