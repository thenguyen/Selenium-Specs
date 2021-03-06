package com.example.tests

import org.specs2._
import matcher.ThrownExpectations
import specification.Step
import Thread._
import org.openqa.selenium.WebDriverBackedSelenium

class `Google Search` extends Specification with ThrownExpectations { 

  lazy val selenium = new WebDriverBackedSelenium(new org.openqa.selenium.firefox.FirefoxDriver(), "http://www.google.ch/")

  def is = 
  sequential                                                ^
  "This specification tests Google Search"    ^ Step(() => selenium) ^ 
    "Searching google.ch for toys should return a toys r us result"             ! scala_specs2_1^
    "clicking the Bilder link should open the results for images search"        ! scala_specs2_2 ^
                                                                                Step(selenium.stop()) ^
                                                                                end


  def scala_specs2_1 = {
    import selenium._
    open("/")
    `type`("q", "toys")
    click("btnG")
    doWait(isTextPresent("Toys\"R\"Us"))
    isTextPresent("Toys\"R\"Us") must beTrue
    success
  }

  def scala_specs2_2 = {
    import selenium._
    click("css=a.q.qs")
    waitForPageToLoad("30000")
    isTextPresent("Verwandte Suchanfragen") must beTrue
    success
  }

  val TIMEOUT = 30
  private def doWait(assertion: => Boolean) = 
    (1 to TIMEOUT).view map {_=> sleep(1000)} find { _ => assertion }

}
