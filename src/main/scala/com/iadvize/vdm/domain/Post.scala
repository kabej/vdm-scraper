package com.iadvize.vdm.domain

import org.joda.time.LocalDateTime


case class Post(id: Int, content: String, date: LocalDateTime, author: String)

case class Posts(posts: List[Post], count: Int)

/**
  * Constructeur pour le calcul auto de la taille de la liste
  */
object Posts {
  def apply(posts: List[Post]): Posts = Posts(posts, posts.size)
}


