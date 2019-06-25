package it.maxbax.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.openqa.selenium.WebDriver;

import com.assertthat.selenium_shutterbug.core.Shutterbug;
import com.assertthat.selenium_shutterbug.utils.web.ScrollStrategy;

import cucumber.api.Scenario;

public class ScreenshotsDoc {

	public final static String SCREENSHOTSDOC_FOLDER = "screenshots";
	private final static String SCREENSHOT_SCENARIO = "SCREENSHOT";
	private final static String SCREENSHOT_SCENARIO_SHORT = "SHORT";
	public final static String TITLE_FILE = "titles.properties";
	public final static String BODY_FILE = "bodies.properties";
	public final static String ROW_SEPARATOR = "<cr>";

	private Scenario scenario;

	public ScreenshotsDoc(Scenario scenario) {
		this.scenario = scenario;		
	}

	private String getScreenshotName() throws UnsupportedEncodingException {
		return URLEncoder.encode(scenario.getName(), "UTF-8");
	}

	public void saveScreenshots(WebDriver webDriver) throws UnsupportedEncodingException {
		if (!scenario.getName().startsWith(SCREENSHOT_SCENARIO))
			return;
		boolean screenshotShort = scenario.getName().endsWith(SCREENSHOT_SCENARIO_SHORT);
		if (screenshotShort)
			Shutterbug.shootPage(webDriver).withName(getScreenshotName()).save(SCREENSHOTSDOC_FOLDER);
		else
			Shutterbug.shootPage(webDriver, ScrollStrategy.WHOLE_PAGE, 1000).withName(getScreenshotName())
					.save(SCREENSHOTSDOC_FOLDER);
	}

	private void appendProperty(String nameFile, String key, String value) {
		try {
			FileWriter fileWritter = new FileWriter(new File(SCREENSHOTSDOC_FOLDER, nameFile), true);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.append("\n" + key + "=" + value);
			bufferWritter.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	public void saveScreenshotsTitle(String title) throws UnsupportedEncodingException {
		appendProperty(TITLE_FILE, getScreenshotName(), title);
	}

	public void saveScreenshotsBody(String body) throws UnsupportedEncodingException {
		appendProperty(BODY_FILE, getScreenshotName(), body);
	}

}
