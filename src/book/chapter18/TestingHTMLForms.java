package book.chapter18;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

/**
 * @author lukepotter
 *
 */

public class TestingHTMLForms {
	
	//Declare XPath Constants
	final static String SUBMIT_FORM = "//form[@id='HTMLFormElements']";
	final static String FORM_USERNAME_TEXT_FIELD = "//form[@id='HTMLFormElements']//input[@name='username']";
	final static String FORM_PASSWORD_FIELD = "//form[@id='HTMLFormElements']//input[@name='password']";
	final static String FORM_COMMENTS_MULTI_LINE_TEXT_BOX = "//form[@id='HTMLFormElements']//textarea[@name='comments']";
	final static String FORM_FILE_UPLOAD = "//form[@id='HTMLFormElements']//input[@name='filename']";
	final static String FORM_CHECKBOX_1 = "//form[@id='HTMLFormElements']//input[@name='checkboxes[]' and @value='cb1']";
	final static String FORM_CHECKBOX_2 = "//form[@id='HTMLFormElements']//input[@name='checkboxes[]' and @value='cb2']";
	final static String FORM_CHECKBOX_3 = "//form[@id='HTMLFormElements']//input[@name='checkboxes[]' and @value='cb3']";
	final static String FORM_RADIOBUTTON_1 = "//form[@id='HTMLFormElements']//input[@name='radioval' and @value='rd1']";
	final static String FORM_RADIOBUTTON_2 = "//form[@id='HTMLFormElements']//input[@name='radioval' and @value='rd2']";
	final static String FORM_RADIOBUTTON_3 = "//form[@id='HTMLFormElements']//input[@name='radioval' and @value='rd3']";
	final static String FORM_MULTIPLE_SELECT_BOX = "//form[@id='HTMLFormElements']//select[@name='multipleselect[]']";
	final static String FORM_DROPDOWN_BOX = "//form[@id='HTMLFormElements']//select[@name='dropdown']";
	final static String FORM_SUBMIT_BUTTON = "//input[@name='submitbutton' and @value='submit']";
	
	
	//Selenium Variable
	private static Selenium selenium;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		selenium = new DefaultSelenium("localhost", 4444, "*firefox", "http://www.compendiumdev.co.uk");
		selenium.start();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		
		selenium.close();
		selenium.stop();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		selenium.open("/selenium/basic_html_form.html");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	
	
	
	@Test
	public void testSubmitFormWithDefaultValues() {
		
		clickSubmitAndWaitForPageToLoad();
	}
	
	@Test
	public void testSubmitFormWithoutClickingSubmit() {
		
		selenium.submit(SUBMIT_FORM);
		selenium.waitForPageToLoad("30000");
	}
	
	@Test
	public void testSubmitFormWithNewUsername() {
		
		selenium.type(FORM_USERNAME_TEXT_FIELD, "The Lukester");
		clickSubmitAndWaitForPageToLoad();
	}

	@Test
	public void testSubmitFormWithNewPassword() {
		
		selenium.type(FORM_PASSWORD_FIELD, "My Password");
		clickSubmitAndWaitForPageToLoad();
	}
	
	@Test
	public void testSubmitFormWithTextbox() {
		
		selenium.type(FORM_COMMENTS_MULTI_LINE_TEXT_BOX, "a multiline\ncomment\nin the text area");
		clickSubmitAndWaitForPageToLoad();
	}
	
	@Test
	public void testSubmitFormWithClickCheckboxesAndRadioButtons() {
		
		selenium.click(FORM_CHECKBOX_2);
		selenium.click(FORM_CHECKBOX_3);
		selenium.click(FORM_RADIOBUTTON_3);
		clickSubmitAndWaitForPageToLoad();
	}
	
	@Test
	public void testSubmitFormWithMultipleSelectBox() {
		
		selenium.removeAllSelections(FORM_MULTIPLE_SELECT_BOX);
		selenium.addSelection(FORM_MULTIPLE_SELECT_BOX, "label=Selection Item 3");
		clickSubmitAndWaitForPageToLoad();
	}
	
	@Test
	public void testSubmitFormWithDropDown() {
		
		selenium.select(FORM_DROPDOWN_BOX, "label=Drop Down Item 1");
		clickSubmitAndWaitForPageToLoad();
	}
	
	@Test
	public void testSubmitFormWithHiddenField() {
		
		selenium.getEval("this.browserbot.findElement(\"name=hiddenField\").value=\"ammended value\"");
		clickSubmitAndWaitForPageToLoad();
	}
	
	@Test
	public void checkTextEnteredValues() {
		
		selenium.type(FORM_USERNAME_TEXT_FIELD, "The Lukester");
		selenium.type(FORM_PASSWORD_FIELD, "My Password");
		selenium.type(FORM_COMMENTS_MULTI_LINE_TEXT_BOX, "comments");
		
		assertEquals(" [ Error ] Username not as entered", "The Lukester", selenium.getValue(FORM_USERNAME_TEXT_FIELD));
		assertEquals(" [ Error ] Password not as entered", "My Password", selenium.getValue(FORM_PASSWORD_FIELD));
		assertEquals(" [ Error ] Comments not as expected", "comments", selenium.getValue(FORM_COMMENTS_MULTI_LINE_TEXT_BOX));
	}
	
	@Test
	public void checkCheckboxValues() {
		
		assertEquals("By default checkbox 1 is not selected", "off", selenium.getValue(FORM_CHECKBOX_1));
		assertEquals("By default checkbox 2 is not selected", "off", selenium.getValue(FORM_CHECKBOX_2));
		assertEquals("By default checkbox 3 is selected", "on", selenium.getValue(FORM_CHECKBOX_3));
		
		selenium.check(FORM_CHECKBOX_2);
		selenium.uncheck(FORM_CHECKBOX_3);
		
		assertEquals("Checkbox 1 is still not selected", "off", selenium.getValue(FORM_CHECKBOX_1));
		assertEquals("Checkbox 2 is now selected", "on", selenium.getValue(FORM_CHECKBOX_2));
		assertEquals("Checkbox 3 is no longer selected", "off", selenium.getValue(FORM_CHECKBOX_3));
	}
	
	@Test
	public void checkRadioButtonValues() {
		
		assertEquals("By default radiobutton 1 is not selected", "off", selenium.getValue(FORM_RADIOBUTTON_1));
		assertEquals("By default radiobutton 2 is selected", "on", selenium.getValue(FORM_RADIOBUTTON_2));
		assertEquals("By default radiobutton 3 is not selected", "off", selenium.getValue(FORM_RADIOBUTTON_3));
		
		selenium.check(FORM_RADIOBUTTON_1);
		
		assertEquals("Radiobutton 1 is now selected", "on", selenium.getValue(FORM_RADIOBUTTON_1));
		assertEquals("Radiobutton 2 is no longer selected", "off", selenium.getValue(FORM_RADIOBUTTON_2));
		assertEquals("Radiobutton 3 is not selected", "off", selenium.getValue(FORM_RADIOBUTTON_3));
	}
	
	@Test
	public void testRandomlySelectValueFromDropdown() {
		
		//Get all options in the dropdown
		String dropDownOptions[] = selenium.getSelectOptions(FORM_DROPDOWN_BOX);
		
		//Get the index of the currently selected item in th dropdown
		String currentDropDownItem = selenium.getSelectedIndex(FORM_DROPDOWN_BOX);
		int previouslySelectedIndex = Integer.valueOf(currentDropDownItem);
		
		//Randomly choose and item in the dropdown
		Random generator = new Random();
		int dropDownIndex = generator.nextInt(dropDownOptions.length);
		
		//Make sure it isn't the same as the current one
		if( dropDownIndex == previouslySelectedIndex ) {
			
			//It is the same, so add one
			dropDownIndex++;
			
			//But this might push it out of bounds so use the modulus operator on it
			dropDownIndex = dropDownIndex % dropDownOptions.length;
		}
		
		//Select this new one
		selenium.select(FORM_DROPDOWN_BOX, "label=" + dropDownOptions[dropDownIndex]);
		
		//Check that something is selected
		assertTrue(selenium.isSomethingSelected(FORM_DROPDOWN_BOX));
		
		//Check that what we wanted is selected
		assertEquals(dropDownIndex, Integer.valueOf(selenium.getSelectedIndex(FORM_DROPDOWN_BOX)).intValue());
		
		//Double check we didn't select the same thing
		assertTrue(dropDownIndex != previouslySelectedIndex);
	}
	
	
	
	
	//------------------ Private Methods ------------------//
 	private static void clickSubmitAndWaitForPageToLoad() {
		
		selenium.click(FORM_SUBMIT_BUTTON);
		selenium.waitForPageToLoad("30000");
	}
	
}
