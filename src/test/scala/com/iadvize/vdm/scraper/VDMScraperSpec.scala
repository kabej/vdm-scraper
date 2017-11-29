package com.iadvize.vdm.scraper

import com.iadvize.vdm.dao.post.{PostDao, PostSearch}
import com.iadvize.vdm.domain.Post
import org.scalatest._

import scala.collection.mutable.Stack


/**
  * Created by kbejaoui on 27/11/17.
  *
  * TODO TU (un peu léger)
  */
class VDMScraperSpec extends FlatSpec {

  var postDaoMock = new PostDaoMock()
  var vdmScraper = new VDMScraper(postDaoMock)

  class PostDaoMock extends PostDao {
    val stack = new Stack[Post]

    override def insertPost(post: Post): Unit = {stack.push(post)}
    override def deleteAll(): Unit = {stack.clear()}
    override def getPosts(search: PostSearch): List[Post] = {stack.elems}
    override def getPost(id: Int): Option[Post] = ???
  }

  "VDMScraper" should "be instanciate" in {
    assert(vdmScraper != null)
  }

  it should "scrape 10 articles" in {
    vdmScraper.scrape(10)
    assert(10 == postDaoMock.getPosts(new PostSearch(None, None, None)).size)
  }
}