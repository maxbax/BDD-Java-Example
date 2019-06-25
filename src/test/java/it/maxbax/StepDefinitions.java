package it.maxbax;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import it.maxbax.utils.BrowserDriver;
import it.maxbax.utils.ScreenshotsDoc;

public class StepDefinitions {

	private BrowserDriver browser;
	private ScreenshotsDoc screenshotsDoc;
	
	@Before
	public void before(Scenario scenario) throws Exception {
		screenshotsDoc = new ScreenshotsDoc(scenario);
		System.out.println("\n\nScenario: " + scenario.getName() + "\n");
		browser = new BrowserDriver();
	}

	@Given("^I am on the Google search page$")
	public void I_visit_google() {
		browser.getDriver().get("https:\\www.google.com");
	}

	@When("^I search for \"(.*)\"$")
	public void search_for(String query) {
		WebElement element = browser.getDriver().findElement(By.name("q"));
		element.sendKeys(query);
		element.submit();
	}

	@Then("^the page title should start with \"(.*)\"$")
	public void checkTitle(final String titleStartsWith) {
		new WebDriverWait(browser.getDriver(), 10L).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return d.getTitle().toLowerCase().startsWith(titleStartsWith);
			}
		});
	}

	@Then("^the screenshot title is \"([^\"]*)\"$")
	public void setScreenshotTitle(String title) throws Throwable {
		screenshotsDoc.saveScreenshotsTitle(title);
	}

	@Then("^the screenshot body is the next$")
	public void the_body_is_the_next(List<String> rows) throws Throwable {
		String body = "";
		for (String row : rows)
			body += row + ScreenshotsDoc.ROW_SEPARATOR;
		screenshotsDoc.saveScreenshotsBody(body);
	}

	@After()
	public void closeBrowser() throws UnsupportedEncodingException {
		screenshotsDoc.saveScreenshots(browser.getDriver());
		browser.getDriver().quit();
	}
}