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
    "have a url in the dictionary after it's sent" in new AkkaTestkitSpecs2Support {
      within(1 second) {
        val urlAct = TestActorRef[URLActor]
        urlAct ! URLs(Array("http://bomb.com"))
        val urlRef = urlAct.underlyingActor
        urlRef.urlMap("http://bomb.com") === 1
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
    "have a hashtag in the dictionary after it's sent" in new AkkaTestkitSpecs2Support {
      within(1 second) {
        val hashAct = TestActorRef[HashtagActor]
        hashAct ! Hashtag(Array("Trent"))
        val hashRef = hashAct.underlyingActor
        hashRef.hashtagMap("Trent") === 1
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
    "have a emoji in the dictionary after it's sent" in new AkkaTestkitSpecs2Support {
      within(1 second) {
        val emAct = TestActorRef[EmojiActor]
        emAct ! Emojis(List("😂"))
        val emRef = emAct.underlyingActor
        emRef.emojiMap("😂") === 1
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
    "have a language in the dictionary after it's sent" in new AkkaTestkitSpecs2Support {
      within(1 second) {
        val langAct = TestActorRef[LangActor]
        langAct ! Language("En")
        val langRef = langAct.underlyingActor
        langRef.langMap("En") === 1
      }
    }

  }
  // "Master actor" should {
  //   "print after 1000 tweets" in new AkkaTestkitSpecs2Support {
  //     within(1 second) {
  //     val master = system.actorOf(Props(new Master()), name = "master")
  //     for (x <- 0 to 1000) master ! Tweet("test")
  //     expectNoMsg
  //     }
  //   }
  // }

}

