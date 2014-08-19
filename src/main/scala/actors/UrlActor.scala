package com.banno.interns.Trent

import akka.actor._
import akka.routing.SmallestMailboxRouter
import akka.util.Duration
import akka.util.duration._
import twitter4j._
import java.util.Date

class URLActor extends Actor {
  val urlMap = scala.collection.mutable.Map[String,Int]()
  def receive = {
    case u @ URLs(_) => {
      for (url <- u.urls) {
        val domain = url.getDisplayURL.takeWhile(x => x != '/')
        if (urlMap.contains(domain)) urlMap(domain) += 1
        else urlMap.put(domain, 1)
      }
    }
    case PrintResults => {
      val topFiveURLS = TweetMethods.nLargestValuesFromMap(5,urlMap).reverse
      println("\nTop Five URLS\n" + topFiveURLS.mkString("\n"))
      sender ! "done url"
    }
  }
}
