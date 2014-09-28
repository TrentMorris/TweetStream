package com.banno.interns.Trent

import akka.actor._
import akka.routing.SmallestMailboxRouter
import akka.util.Duration
import akka.util.duration._
import twitter4j._
import java.util.Date

class URLActor extends Actor with TweetMethods{
  val urlMap = scala.collection.mutable.Map[String,Int]()
  def receive = {
    case u @ URLs(_) => {
      for (url <- u.urls) {
        if (urlMap.contains(url)) urlMap(url) += 1
        else urlMap.put(url, 1)
      }
    }
    case PrintResults => {
      val originalSender = sender
      val topFiveURLS = nLargestValuesFromMap(5,urlMap).reverse
      println("\nTop Five URLS\n" + topFiveURLS.mkString("\n"))
      originalSender ! "done url"
    }
  }
}
