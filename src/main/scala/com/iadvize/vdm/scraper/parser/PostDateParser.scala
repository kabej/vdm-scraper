package com.iadvize.vdm.scraper.parser

import net.ruippeixotog.scalascraper.model.{Element, TextNode}
import org.joda.time.format.DateTimeFormat
import org.joda.time.{DateTime, DateTimeZone}

/**
  * Created by kbejaoui on 26/11/17.
  */
trait PostDateParser {

  val DatePattern = """^\s\/\s\S*\s(\d{2}|\d)\s(\S*)\s(\d{4})\s(\d{2}|\d):(\d{2}|\d)""".r
  val Formatter = DateTimeFormat.forPattern("dd MMM yyyy HH:mm")
  val TimeZoneFR = DateTimeZone.forID("Europe/Paris")

  def extractDate(dateAuthorElement: Element): DateTime = {
    val dateText = dateAuthorElement.parent.get.childNodes.slice(2, 3).toList.head

    DatePattern.findFirstMatchIn(dateText.asInstanceOf[TextNode].content) match {
      case Some(m) =>
        val toParse = s"${m.group(1)} ${m.group(2)} ${m.group(3)} ${m.group(4)}:${m.group(5)}"
        val dateTime = Formatter.withZone(TimeZoneFR).parseDateTime(toParse)
        dateTime.toDateTime(DateTimeZone.UTC)
      case None => throw new IllegalStateException()
    }
  }
}
