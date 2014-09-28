package com.banno.interns.Trent

import akka.actor._
import akka.routing.SmallestMailboxRouter
import akka.util.Duration
import akka.util.duration._
import twitter4j._
import java.util.Date

class LangActor extends Actor with TweetMethods{
  val langMap = scala.collection.mutable.Map[String,Int]()
  def receive = {
    case l @ Language(_) => {
      if (langMap.contains(l.lang)) langMap(l.lang) += 1
      else langMap.put(l.lang, 1)
    }
    case PrintResults => {
      val originalSender = sender
      val topTwoLangs = nLargestValuesFromMap(5,langMap).reverse
      println("\nTop Five Languages\n" + topTwoLangs.mkString("\n"))
      originalSender ! "done lang"
    }
  }
}