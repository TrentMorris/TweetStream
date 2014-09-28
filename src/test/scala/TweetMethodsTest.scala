package com.banno.interns.Trent

import org.specs2.mutable.Specification
import akka.actor._
import akka.testkit._
import akka.util.duration._
import akka.util.Duration
import twitter4j._
import twitter4j.json.DataObjectFactory


object TweetStreamSpec extends Specification with TweetMethods {
  val map = scala.collection.mutable.Map[String, Int](("a", 4), ("b", 9), ("c", 1), ("d", 10), ("e", 68), ("f", 543))

  "nLargestValuesFromMap" should {
    "return List of items from map" >> {
      nLargestValuesFromMap(5, map) === List(("a", 4), ("b", 9), ("d", 10), ("e", 68), ("f", 543))
    }
    "return whole list if parameter larger than map" >> {
      nLargestValuesFromMap(7, map) === List(("c", 1), ("a", 4), ("f", 543), ("e", 68), ("d", 10), ("b", 9))
    }
  }

  "containsPictureURL" should {
   "return true if pic.twitter in list of URLs" >> {
      containsPictureURL(Array("kfldsapic.twitter.comjflewqoio","pic.twittr.com")) === true
    }
 
    "return true if instagram  in list of URLs" >> {
      containsPictureURL(Array("instagram.comfa","witter.comjflewqoio")) === true

    }
    "return true if both image URLs are in list of URLs" >> {
      containsPictureURL(Array("pic.twitter.comjflewqoio","instagram.comjfld")) === true
 
    }
    "return false if  no image url in list of URLs" >> {
      containsPictureURL(Array("kfldsapic.titter.comjflewqoioinstragram.com")) === false
    }
  }

   "formatResults" should {
    "format results" >> {
      formatResults(PrintResults(1000,1000,1000,1000,1000,1))==="\n1000 Total Tweets\n100.00% of Tweets contain a Url\n100.00% of Tweets contain an Emoji\n100.00% of Tweets contain a Hashtag\n0.10% of Tweets contain a Picture Url\n\n1000 Average Tweets per Second\n60000 Average Tweets per Minute\n3600000 Average Tweets per Hour"
    }
  }

  "getEmojis" should {
    "get emojis from tweet" >> {
      getEmojis("balls😂") === List("😂")
    }
    "return empty list if no emojis there" >>{
      getEmojis("nothing here") === List()
    }
  }
}

