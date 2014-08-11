package com.banno.interns.Trent

import twitter4j._

object TweetMethods {
  // getEmojis
  // getHashTags
  // getUrls

  def containsPictureURL(urls: Array[URLEntity]): Boolean = {
    for (url <- urls) {
      if (url.getDisplayURL.contains("pic.twitter.com") || url.getDisplayURL.contains("instagram.com/p/")) return true
    }
    false
  }

  def formatResults(results: PrintResults): String = {
    val t = results.tweets
    val h = results.hashtags
    val u = results.urls
    val p = results.pictures
    val time = results.time

    val tweetsPerSecond = t / (time / 1000)
    val tweetsPerMinute = tweetsPerSecond * 60
    val tweetsPerHour = tweetsPerMinute * 60
    t + " Total Tweets\n" + (h * 100.0) / t + " % of Tweets contain a Hashtag\n" +
      (u * 100.0) / t + " % of Tweets contain a Url\n" + (p * 100.0) / t + " % of Tweets contain a Picture Url\n\n" +
      tweetsPerSecond + " Average Tweets per Second\n" + tweetsPerMinute + " Average Tweets per Minute\n" +
      tweetsPerHour + " Average Tweets per Hour"
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
}