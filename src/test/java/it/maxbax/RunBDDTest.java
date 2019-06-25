package it.maxbax;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import cucumber.api.junit.Cucumber;
import it.maxbax.utils.DocGenerator;
import it.maxbax.utils.ScreenshotsDoc;

@RunWith(Cucumber.class)
public class RunBDDTest {

	@BeforeClass
	public static void setup() throws IOException {
		// create a clean screenshots folder
		File screenshotsFolder = new File(ScreenshotsDoc.SCREENSHOTSDOC_FOLDER);
		FileUtils.deleteDirectory(screenshotsFolder);
		screenshotsFolder.mkdirs();
	}

	@AfterClass
	public static void teardown() throws Exception {
		// generate the manual document
		DocGenerator.generate("Manual");
	}

}