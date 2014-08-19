package com.banno.interns.Trent

import twitter4j._
import akka.actor._

object Util {
  val config = new twitter4j.conf.ConfigurationBuilder()
    .setOAuthConsumerKey("tFaWcmCxAAjGJJL13FJ5pIJMm")
    .setOAuthConsumerSecret("Ic13oZdeP34kwBYXLjQ5UlWNwldl7W9WEQCSoBz34pcFMevenN")
    .setOAuthAccessToken("354959878-xsF03aX4Ge1aAvlYnP0S5ilvIQMPERZfsx0pWY0L")
    .setOAuthAccessTokenSecret("bhKTosUG1K0aKnO51hNm5wX5HHTX3JqV2TI4X3ym2vejH")
    .build

  val system = ActorSystem("TweetSystem")
  val master = system.actorOf(Props(new Master()), name = "master")
  
  def simpleStatusListener = new StatusListener() {
    def onStatus(status: Status) {
      master ! Hashtag(status.getHashtagEntities)
      master ! URLs(status.getURLEntities)
      master ! Tweet(status.getText())
      master ! Language(status.getUser().getLang())
    } 
    def onDeletionNotice(statusDeletionNotice: StatusDeletionNotice) {}
    def onTrackLimitationNotice(numberOfLimitedStatuses: Int) {}
    def onException(ex: Exception) { ex.printStackTrace }
    def onScrubGeo(arg0: Long, arg1: Long) {}
    def onStallWarning(warning: StallWarning) {println(warning)}
  }
}

object StatusStreamer {
  def main(args: Array[String]) {
    val twitterStream = new TwitterStreamFactory(Util.config).getInstance
    twitterStream.addListener(Util.simpleStatusListener)
    twitterStream.sample

    // val twitter = new TwitterFactory(Util.config).getInstance()
    // twitter.updateStatus("Tweet made from Scala program by papamorris7")
  }
}


