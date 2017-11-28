package com.iadvize.vdm.dao.post

import com.iadvize.vdm.domain.Post

trait PostDao {

  def insertPost(post: Post)

  def deleteAll()

  def getPosts(search: PostSearch): List[Post]

  def getPost(id: Int): Option[Post]
}
