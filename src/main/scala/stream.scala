package com.banno.interns.Trent

import twitter4j._
import akka.actor._
import twitter4j.json.DataObjectFactory

object StatusStreamer {
  def main(args: Array[String]) {
    val twitterStream = new TwitterStreamFactory(Util.config).getInstance
    twitterStream.addListener(Util.simpleStatusListener)
    twitterStream.sample

    // val twitter = new TwitterFactory(Util.config).getInstance()
    // twitter.updateStatus("Tweet made from Scala program by papamorris7")
  }
}


