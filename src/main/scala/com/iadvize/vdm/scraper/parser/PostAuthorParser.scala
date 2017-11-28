package com.iadvize.vdm.scraper.parser

import net.ruippeixotog.scalascraper.model.{Element, TextNode}

/**
  * Created by kbejaoui on 26/11/17.
  */
trait PostAuthorParser {

  val AuthorPattern = """^.Par\s([\S]*)\s-""".r

  def extractAuthor(dateAuthorElement: Element): String = {
    val authorText = dateAuthorElement.parent.get.childNodes.take(1).toList.head

    AuthorPattern.findFirstMatchIn(authorText.asInstanceOf[TextNode].content) match {
      case Some(m) =>
        m.group(1)
      case None => throw new IllegalStateException()
    }
  }
}
