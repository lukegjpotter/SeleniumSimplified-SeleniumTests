package book.chapter06;

import com.thoughtworks.selenium.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("deprecation")
public class MyFirstSeleniumTest extends SeleneseTestCase {
	@Before
	public void setUp() throws Exception {
		selenium = new DefaultSelenium("localhost", 4444, "*chrome", "http://www.google.ie/");
		selenium.start();
	}

	@Test
	public void testUseGoogle() throws Exception {
		selenium.open("/");
		selenium.type("id=gbqfq", "Luke Potter");
		selenium.click("id=gbqfb");
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}