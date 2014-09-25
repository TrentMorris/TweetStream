package com.banno.interns.Trent

import twitter4j._
import akka.actor._
import twitter4j.json.DataObjectFactory


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
      val urls: Array[String] = for (url <- status.getURLEntities) yield url.getDisplayURL().takeWhile(x => x!='/')
      val hashtags: Array[String] = for (hash <- status.getHashtagEntities) yield hash.getText
      master ! Hashtag(hashtags)
      master ! URLs(urls)
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