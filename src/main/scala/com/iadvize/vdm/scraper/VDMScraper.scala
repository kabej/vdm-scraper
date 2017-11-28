package com.iadvize.vdm.scraper

import java.util.NoSuchElementException

import com.iadvize.vdm.dao.post.PostDao
import com.iadvize.vdm.domain.Post
import com.iadvize.vdm.scraper.parser.PostParser
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.model.Element

/**
  * Created by kbejaoui on 26/11/17.
  */
class VDMScraper(postDaoImpl: PostDao) extends PostParser {

  def scrape(elementsCount: Int) = {
    val browser = JsoupBrowser()
    postDaoImpl.deleteAll()

    var index: Int = 1
    var page: Int = 1
    // Double loop pour gérer les X élements
    while (index <= elementsCount) {
      val doc = browser.get(s"http://www.viedemerde.fr/?page=$page")
      // Récupération des articles du scroll central
      val articles = doc >> elementList(".jscroll-inner >  article")
      val iterator = articles.iterator
      while (iterator.hasNext && index <= elementsCount) {
        val post = extractPost(iterator.next(), index)
        if (post.isDefined) {
          postDaoImpl.insertPost(post.get)
          index += 1
        }
      }
      page += 1
    }
  }

  /**
    * Extrait un post potentiel à partir d'un article
    *
    * @param article
    * le DOM du composant article
    * @param index
    * l'index
    */
  private def extractPost(article: Element, index: Int): Option[Post] = {
    try {
      val content = (article >> element(".panel-content > p > a")).text
      // C'est un VDM ?
      if (content.endsWith("VDM") && content.contains("Aujourd")) {
        // Récupération de la div de l'author et de la date
        val dateAuthorElement = article >> element(".panel-body .text-center > i")
        val author = extractAuthor(dateAuthorElement)
        val date = extractDate(dateAuthorElement)
        // Renvoi le post
        Some(Post(index, content, date.toLocalDateTime, author))
      } else {
        throw new NoSuchElementException
      }
    } catch {
      // Pas de parsing possible
      case _: NoSuchElementException | _: IllegalStateException => None
    }
  }
}
