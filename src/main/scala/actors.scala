package com.banno.interns.Trent

import akka.actor._
import akka.routing.SmallestMailboxRouter
import akka.util.Duration
import akka.util.duration._
import twitter4j._
import java.util.Date

sealed trait Messages
case class PrintResults(tweets: Int, hashtags: Int, urls: Int, time: Long, pictures: Int) extends Messages
case class Tweet(message: String) extends Messages
case class URLs(urls: Array[URLEntity]) extends Messages
case class Hashtag(hashtags: Array[HashtagEntity]) extends Messages
case class URLResults(urls: Array[URLEntity]) extends Messages
case class HashtagResults(hash: Array[HashtagEntity]) extends Messages
case object PrintResults extends Messages

class Master extends Actor {
  val start: Long = System.currentTimeMillis
  var tweetCount = 0
  var hashtagCount = 0
  var urlCount = 0
  var pictureCount = 0
  val now = new Date

  val Results = context.actorOf(
    Props[Results].withRouter(SmallestMailboxRouter(4)), name = "Results")
  val URLActor = context.actorOf(
    Props[URLActor].withRouter(SmallestMailboxRouter(10)), name = "URLActor")
  val HashtagActor = context.actorOf(
    Props[HashtagActor].withRouter(SmallestMailboxRouter(10)), name = "HashtagActor")

  def receive = {
    case s @ Tweet(_) => {
      val time: Long = System.currentTimeMillis - start
      Results ! s; tweetCount += 1
      if (tweetCount % 1000 == 0) {
        println("\n\n\t\tTwitter Stats\n\t" + now + "\n")
        Results ! PrintResults(tweetCount, hashtagCount, urlCount, time, pictureCount)
        HashtagActor ! PrintResults
        URLActor ! PrintResults
      }
    }
    case h @ Hashtag(_) => {
      if (h.hashtags.size != 0) hashtagCount += 1
      HashtagActor ! HashtagResults(h.hashtags)
    }
    case u @ URLs(_) => {
      if (TweetMethods.containsPictureURL(u.urls)) pictureCount += 1
      if (u.urls.size != 0) urlCount += 1    
      URLActor ! URLResults(u.urls)
    }
  }
}
class Results extends Actor {
  def receive = {
    case p @ PrintResults(_, _, _, _, _) => {
      println(TweetMethods.formatResults(p))
    }
  }
}

class URLActor extends Actor {
  val urlMap = scala.collection.mutable.Map[String,Int]()
  def receive = {
    case u @ URLResults(_) => {
      for (url <- u.urls) {
        val domain = url.getDisplayURL.takeWhile(x => x != '/')
        if (urlMap.contains(domain)) urlMap(domain) += 1
        else urlMap.put(domain, 1)
      }
    }
    case PrintResults => {
      val topFiveURLS = TweetMethods.nLargestValuesFromMap(5,urlMap).reverse
      println("\nTop Five URLS")
      for (u <- topFiveURLS) println(u)
      sender ! "done url"
    }
  }

}

class HashtagActor extends Actor {
  val hashtagMap = scala.collection.mutable.Map[String,Int]()
  def receive = {
    case h @ HashtagResults(_) => {
      for (ht <- h.hash) {
        if (hashtagMap.contains(ht.getText)) hashtagMap(ht.getText) += 1
        else hashtagMap.put(ht.getText, 1)
      }
    }
    case PrintResults => {
      val topFiveHashtags = TweetMethods.nLargestValuesFromMap(5,hashtagMap).reverse
      println("\nTop Five Hashtags")
      for (ht <- topFiveHashtags) println(ht)
      sender ! "done hashtag"
    }
  }
}