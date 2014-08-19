package com.banno.interns.Trent

import org.specs2.mutable.Specification
import akka.actor._
import akka.testkit._
import akka.util.duration._
import akka.util.Duration
import twitter4j._
import org.specs2.mutable._
import org.specs2.time.NoTimeConversions

abstract class AkkaTestkitSpecs2Support extends TestKit(ActorSystem()) with After with ImplicitSender {
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
  "An emojiActor" should {
    "print when sent PrintResults and send done msg" in new AkkaTestkitSpecs2Support {
      within(1 second) {
        system.actorOf(Props[EmojiActor]) ! PrintResults
        expectMsgType[String] must be equalTo "done emojis"
      }
    }
  }
  "A langActor" should {
    "print when sent PrintResults and send done msg" in new AkkaTestkitSpecs2Support {
      within(1 second) {
        system.actorOf(Props[LangActor]) ! PrintResults
        expectMsgType[String] must be equalTo "done lang"
      }
    }
  }
  // "Master Actor" should {
  //   "print everything on 1000th tweet" in new AkkaTestkitSpecs2Support {
  //     within (10 second) {
  //       for (x <- 0 to 1000) system.actorOf(Props[Master]) ! Tweet
  //       expectMsgType[String] must be equalTo "done hashtag"
  //     }
  //   }
  // }

}

