This class contains a selenium IDE format that outputs the recorded selenium script in scala and more specifically for Specs2.  

The output format uses comments to provide the output format with metadata on how to create the specs.  The current rules are:

  * If a comment starts with a - that line indicates the start of a new fragment

More rules will be added in the future for formatting etc...

An example of output is:

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
		    "Searching google.ch for toys"                          ! scala_specs2_1^
		    "should return a toys r us result"                      ! scala_specs2_2^
		    "clicking the Bilder link"                              ! scala_specs2_3^
		    "should open the results for images search"             ! scala_specs2_4 ^
		                                                            Step(selenium.stop()) ^
		                                                            end


		  def scala_specs2_1 = {
		    import selenium._
		    open("/")
		    `type`("q", "toys")
		    click("btnG")
		    doWait(isTextPresent("Toys\"R\"Us"))
		    success
		  }

		  def scala_specs2_2 = {
		    import selenium._
		    isTextPresent("Toys\"R\"Us") must beTrue
		  }

		  def scala_specs2_3 = {
		    import selenium._
		    click("css=a.q.qs")
		    waitForPageToLoad("30000")
		    success
		  }

		  def scala_specs2_4 = {
		    import selenium._
		    isTextPresent("Verwandte Suchanfragen") must beTrue
		  }

		  val TIMEOUT = 30
		  private def doWait(assertion: => Boolean) = {
		    1 to TIMEOUT find { _ =>
		      if(assertion) {
		        true
		      } else {
		        sleep(1000)
		        false
		      }
		    }
		  }
		}
		