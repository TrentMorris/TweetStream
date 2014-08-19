package com.banno.interns.Trent

import akka.actor._
import akka.routing.SmallestMailboxRouter
import akka.util.Duration
import akka.util.duration._
import twitter4j._
import java.util.Date

class HashtagActor extends Actor {
  val hashtagMap = scala.collection.mutable.Map[String,Int]()
  def receive = {
    case h @ Hashtag(_) => {
      for (ht <- h.hash) {
        if (hashtagMap.contains(ht.getText)) hashtagMap(ht.getText) += 1
        else hashtagMap.put(ht.getText, 1)
      }
    }
    case PrintResults => {
      val topFiveHashtags = TweetMethods.nLargestValuesFromMap(5,hashtagMap).reverse
      println("\nTop Five Hashtags\n" + topFiveHashtags.mkString("\n"))
      sender ! "done hashtag"
    }
  }
}