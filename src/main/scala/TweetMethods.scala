package com.banno.interns.Trent

import twitter4j._

trait TweetMethods {
  def containsPictureURL(urls: Array[String]): Boolean = {
    for (url <- urls) {
      if (url.contains("pic.twitter.com") || url.contains("instagram.com")) return true
    }
    false
  }

  def getEmojis(tweet: String): List[String] = {
    import scala.util.matching.Regex
    val regex = """[^\u0000-\uFFFF]""".r
    val emojis = regex findAllIn tweet
    val emojiIterator: Iterator[String] = if (!(emojis.isEmpty)) for (e <- emojis) yield(e)
    else Iterator[String]()
    emojiIterator.toList
  }

  def nLargestValuesFromMap(n: Int, map: scala.collection.mutable.Map[String, Int]): List[(String,Int)] = {
    def go(n: Int, map: scala.collection.mutable.Map[String, Int], accum: List[(String,Int)]): List[(String, Int)] = {
      if (n > map.size) return map.toList
      if (n == 0) return accum
      else {
        val maxItem = map.maxBy(_._2)
        val newMap = map - (maxItem._1)
        go(n - 1, newMap, maxItem :: accum)
      }
    }
    go(n, map, List())
  }

  def formatResults(results: PrintResults): String = {
    val t:Int = results.tweets
    val h:Int = results.hashtags
    val u:Int = results.urls
    val p:Int = results.pictures
    val e:Int = results.emojis
    val time:Int = results.time.toInt

    val urlPercent = ((u *100.0)/t)
    val emojiPercent = ((e *100.0) / t)
    val hashPercent = ((h * 100.0)/ t)
    val picPercent = ((p * 100.0) / t)

    val tweetsPerSecond:Int = t / (time / 1000)
    val tweetsPerMinute:Int = tweetsPerSecond * 60
    val tweetsPerHour:Int= tweetsPerMinute * 60

    val formatted =  "\n%d Total Tweets\n%.2f%% of Tweets contain a Url\n%.2f%% of Tweets contain an Emoji\n%.2f%% of Tweets contain a Hashtag\n%.2f%% of Tweets contain a Picture Url\n\n%d Average Tweets per Second\n%d Average Tweets per Minute\n%d Average Tweets per Hour".format(t, urlPercent, emojiPercent, hashPercent, picPercent, tweetsPerSecond,tweetsPerMinute,tweetsPerHour)
    formatted
  }
}