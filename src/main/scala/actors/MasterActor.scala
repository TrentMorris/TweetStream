package com.banno.interns.Trent

import akka.actor._
import akka.routing.SmallestMailboxRouter
import akka.util.Duration
import akka.util.duration._
import twitter4j._
import java.util.Date

sealed trait Messages
case object PrintResults extends Messages
case class  PrintResults(tweets: Int, hashtags: Int, urls: Int,emojis: Int, time: Long, pictures: Int) extends Messages
case class  Tweet(message: String) extends Messages
case class  URLs(urls: Array[URLEntity]) extends Messages
case class  Hashtag(hash: Array[HashtagEntity]) extends Messages
case class  Emojis(emojis: List[String]) extends Messages
case class  Language(lang: String) extends Messages

class Master extends Actor {
  val start: Long = System.currentTimeMillis
  var tweetCount = 0
  var hashtagCount = 0
  var urlCount = 0
  var pictureCount = 0
  var emojiCount = 0
  val now = new Date

  // val Results = context.actorOf(
  //   Props[Results].withRouter(SmallestMailboxRouter(4)), name = "Results")
  val URLActor = context.actorOf(
    Props[URLActor].withRouter(SmallestMailboxRouter(10)), name = "URLActor")
  val HashtagActor = context.actorOf(
    Props[HashtagActor].withRouter(SmallestMailboxRouter(10)), name = "HashtagActor")
  val EmojiActor = context.actorOf(
    Props[EmojiActor].withRouter(SmallestMailboxRouter(10)), name = "EmojiActor")
  val LangActor = context.actorOf(
    Props[LangActor].withRouter(SmallestMailboxRouter(10)), name = "LangActor")

  def receive = {
    case s @ Tweet(_) => {
      val time: Long = System.currentTimeMillis - start
      tweetCount += 1
      if (tweetCount % 1000 == 0) {
        println("\n\n\t\tTwitter Stats\n\t" + now )
        println(TweetMethods.formatResults(PrintResults(tweetCount, hashtagCount, urlCount,emojiCount, time, pictureCount)))
        EmojiActor ! PrintResults
        URLActor ! PrintResults
        HashtagActor ! PrintResults
        LangActor ! PrintResults
      }
      val emojiList: List[String] = TweetMethods.getEmojis(s.message)
      if (emojiList.size != 0) emojiCount += 1
      EmojiActor ! Emojis(emojiList)
    }
    case h @ Hashtag(_) => {
      if (h.hash.size != 0) hashtagCount += 1
      HashtagActor ! Hashtag(h.hash)
    }
    case u @ URLs(_) => {
      if (TweetMethods.containsPictureURL(u.urls)) pictureCount += 1
      if (u.urls.size != 0) urlCount += 1    
      URLActor ! URLs(u.urls)
    }
    case l @ Language(_) => {
      LangActor ! l
    }
  }
}
