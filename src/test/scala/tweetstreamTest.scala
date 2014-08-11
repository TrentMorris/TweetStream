package com.banno.interns.Trent

import org.specs2.mutable.Specification
import akka.actor._
import akka.testkit._
import akka.util.duration._
import akka.util.Duration
import twitter4j._

object TweetStreamSpec extends Specification {
  val map = scala.collection.mutable.Map[String, Int](("a", 4), ("b", 9), ("c", 1), ("d", 10), ("e", 68), ("f", 543))

  "nLargestValuesFromMap" should {
    "return List of items from map" >> {
      TweetMethods.nLargestValuesFromMap(5, map) === List(("a", 4), ("b", 9), ("d", 10), ("e", 68), ("f", 543))
    }
    "return whole list if parameter larger than map" >> {
      TweetMethods.nLargestValuesFromMap(7, map) === List(("c", 1), ("a", 4), ("f", 543), ("e", 68), ("d", 10), ("b", 9))
    }
  }
  "containsPictureURL" should {
    "return true if pic.twitter in list of URLs" >> {
      //TweetMethods.containsPictureURL()
      true === false
    }.pendingUntilFixed
     "return true if instagram  in list of URLs" >> {
      //TweetMethods.containsPictureURL()
      true === false
    }.pendingUntilFixed
     "return false if  pic.twitter in list of URLs" >> {
      //TweetMethods.containsPictureURL()
      true === false
    }.pendingUntilFixed
     "return true if instagram in list of URLs" >> {
      //TweetMethods.containsPictureURL()
      true === false
    }.pendingUntilFixed

  }
}
import org.specs2.mutable._
import org.specs2.time.NoTimeConversions

abstract class AkkaTestkitSpecs2Support extends TestKit(ActorSystem())
  with After
  with ImplicitSender {
  def after = system.shutdown()
}

class ActorSpec extends Specification with NoTimeConversions {
  sequential 

  "A URLActor" should {
    "print when sent PrintResults and send done msg" in new AkkaTestkitSpecs2Support {
      within(1 second) {
        system.actorOf(Props[URLActor]) ! PrintResults
        expectMsgType[String] must be equalTo "done url"
      }
    }
  }
  "A hashtagActor" should {
    "print when sent PrintResults and send done msg" in new AkkaTestkitSpecs2Support {
      within(1 second) {
        system.actorOf(Props[HashtagActor]) ! PrintResults
        expectMsgType[String] must be equalTo "done hashtag"
      }
    }
  }
}

