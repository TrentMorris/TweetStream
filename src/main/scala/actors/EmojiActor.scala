package com.banno.interns.Trent

import akka.actor._
import akka.routing.SmallestMailboxRouter
import akka.util.Duration
import akka.util.duration._
import twitter4j._
import java.util.Date

class EmojiActor extends Actor {
  val emojiMap = scala.collection.mutable.Map[String,Int]()
  def receive = {
    case e @ Emojis(_) => {
      for (er <- e.emojis) {
        if (emojiMap.contains(er)) emojiMap(er) += 1
        else emojiMap.put(er, 1)
      }
    }
    case PrintResults => {
      val topFiveEmojis = TweetMethods.nLargestValuesFromMap(5,emojiMap).reverse
      println("\nTop Five Emojis\n" + topFiveEmojis.mkString("\n"))
      sender ! "done emojis"
    }
  }
}