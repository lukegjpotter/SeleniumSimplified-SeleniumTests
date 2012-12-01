package book.chapter11;

import org.junit.*;
import com.thoughtworks.selenium.*;

@SuppressWarnings("deprecation")
public class MySecondSeleniumTest extends SeleneseTestCase {

	@Before
	public void setUp() throws Exception {
		
		selenium = new DefaultSelenium("localhost", 4444, "*firefox", "http://www.compendiumdev.co.uk/");
		selenium.start();
		performInitialActions();
	}
	
	private void performInitialActions() {
		
		//Open Page
		selenium.open("http://www.compendiumdev.co.uk/selenium/search.php");
		
		//Type in search query, press submit ad wait 10 seconds for the page to load
		selenium.type("xpath=//input[@name='q']", "Selenium-RC");
		selenium.click("xpath=//input[@name='btnG' and @type='submit']");
		selenium.waitForPageToLoad("10000");
	}
	

	@Test
	public void testResultIsPresent() throws Exception {
		
		//	1. Check that Relevant results are returned.
		int matchCount = selenium.getXpathCount("//a[@href='http://seleniumhq.org/projects/remote-control/']").intValue();
		assertTrue("No Homepage URL found in results", matchCount > 0);
	}
	
	@Test
	public void testPageTitleContainsSearchQuery() throws Exception {
		
		//	2. Check that page title has search query.
		String pageTitle = selenium.getTitle();
		assertTrue("Page title does not contain search term : " + pageTitle, pageTitle.contains("Selenium-RC"));
	}
	
	@Test
	public void testTextInSearchBoxMatchesSearchQuery() throws Exception {
		
		//	3. Check that search query is in the text box.
		String searchTerm = selenium.getValue("xpath=//input[@name='q' and @title='Search']");
		assertTrue("Search box does not contain search term: " + searchTerm, searchTerm.equals("Selenium-RC"));
	}
	
	@After
	public void tearDown() throws Exception {
		
		selenium.close();
		selenium.stop();
	}
}