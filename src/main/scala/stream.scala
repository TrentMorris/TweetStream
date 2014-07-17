package com.banno.interns.Trent

import twitter4j._


object Util {
  val config = new twitter4j.conf.ConfigurationBuilder()
    .setOAuthConsumerKey("tFaWcmCxAAjGJJL13FJ5pIJMm")
    .setOAuthConsumerSecret("Ic13oZdeP34kwBYXLjQ5UlWNwldl7W9WEQCSoBz34pcFMevenN")
    .setOAuthAccessToken("354959878-xsF03aX4Ge1aAvlYnP0S5ilvIQMPERZfsx0pWY0L")
    .setOAuthAccessTokenSecret("bhKTosUG1K0aKnO51hNm5wX5HHTX3JqV2TI4X3ym2vejH")
    .build

  def simpleStatusListener = new StatusListener() {
  	var counter = 0
    def onStatus(status: Status) {counter += 1; println(status+"\n") } //processTweet(status) }
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
    // Thread.sleep(10000)
    // twitterStream.cleanUp
    // twitterStream.shutdown

    // val twitter = new TwitterFactory(Util.config).getInstance()
    // twitter.updateStatus("Tweet made from Scala program by papmorris7")
  }
}

/*
      def hasPicture(urls: Array[URLEntity]): Boolean = urls.toList match {
        case Nil => false
        case h :: t => println(h.getDisplayURL.toString); """(pic\.twitter\.com\/.*)""".r.findFirstIn(h.getDisplayURL.toString) match {
          case Some(_) => true
          case None => """(instagram\.com\/p\/)""".r.findFirstIn(h.getDisplayURL.toString) match {
            case Some(_) => true
            case None => hasPicture(t.toArray)
          }
        }
      }
*/
