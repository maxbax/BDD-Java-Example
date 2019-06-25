package it.maxbax.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import javax.xml.bind.DatatypeConverter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DocGenerator {

	public static class ArgumentElement implements Comparable<ArgumentElement> {

		private String imageName;
		private String argumentName;
		private String title;

		public ArgumentElement(Properties titles, String fileName) {
			imageName = fileName;
			argumentName = fileName.substring(0, fileName.indexOf("."));
			title = titles.getProperty(argumentName);
		}

		public String getImageName() {
			return imageName;
		}

		public String getArgumentName() {
			return argumentName;
		}

		public String getTitle() {
			return title;
		}

		@Override
		public int compareTo(ArgumentElement o) {
			return title.compareTo(((ArgumentElement) o).getTitle());
		}

	}

	public static void generate(String title) throws Exception {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.newDocument();

		// main
		Element htmlElement = doc.createElementNS("http://www.w3.org/1999/xhtml", "html");
		doc.appendChild(htmlElement);

		// head
		Element headElement = doc.createElement("head");
		htmlElement.appendChild(headElement);

		// css
		Element cssElement = doc.createElement("link");
		cssElement.setAttribute("rel", "stylesheet");
		cssElement.setAttribute("type", "text/css");
		cssElement.setAttribute("href", "https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css");
		headElement.appendChild(cssElement);

		// title html
		Element titleElement = doc.createElement("title");
		titleElement.setTextContent(title);
		headElement.appendChild(titleElement);

		// body
		Element bodyElement = doc.createElement("body");
		bodyElement.setAttribute("class", "container");
		htmlElement.appendChild(bodyElement);

		// title
		Element titleMain = doc.createElement("h1");
		titleMain.setTextContent(title);
		bodyElement.appendChild(titleMain);

		// index
		bodyElement.appendChild(doc.createElement("hr"));
		Element indexMain = doc.createElement("ul");
		indexMain.setAttribute("id", "index");
		bodyElement.appendChild(indexMain);
		bodyElement.appendChild(doc.createElement("hr"));

		File dir = new File(ScreenshotsDoc.SCREENSHOTSDOC_FOLDER);
		File[] files = dir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".png");
			}
		});

		Properties titles = new Properties();
		titles.load(new FileInputStream(new File(ScreenshotsDoc.SCREENSHOTSDOC_FOLDER, "titles.properties")));

		Properties bodies = new Properties();
		bodies.load(new FileInputStream(new File(ScreenshotsDoc.SCREENSHOTSDOC_FOLDER, "bodies.properties")));

		List<ArgumentElement> argumentElements = new ArrayList<DocGenerator.ArgumentElement>();
		for (File file : files)
			argumentElements.add(new ArgumentElement(titles, file.getName()));
		Collections.sort(argumentElements);

		for (ArgumentElement element : argumentElements) {
			// main div
			Element divArgument = doc.createElement("div");
			divArgument.setAttribute("class", "jumbotron row col-md-12");
			bodyElement.appendChild(divArgument);

			// link to index
			Element backToindexLink = doc.createElement("a");
			backToindexLink.setAttribute("style", "right: 10px; position: absolute");
			backToindexLink.setAttribute("href", "#index");
			backToindexLink.setTextContent("Back to index");
			divArgument.appendChild(backToindexLink);

			// title
			Element titleEl = doc.createElement("h2");
			titleEl.setAttribute("id", element.getArgumentName());
			titleEl.setTextContent(element.getTitle());
			divArgument.appendChild(titleEl);

			// index
			Element indexListEl = doc.createElement("li");
			Element indexLink = doc.createElement("a");
			indexLink.setAttribute("href", "#" + element.getArgumentName());
			indexLink.setTextContent(element.getTitle());
			indexListEl.appendChild(indexLink);
			indexMain.appendChild(indexListEl);

			// image
			Element imageEl = doc.createElement("img");
			imageEl.setAttribute("class", "thumbnail col-md-7");
			imageEl.setAttribute("align", "left");
			imageEl.setAttribute("style", "margin-right:15px;");
			imageEl.setAttribute("src",
					getImageSrc(new File(ScreenshotsDoc.SCREENSHOTSDOC_FOLDER, element.getImageName())));
			divArgument.appendChild(imageEl);

			// caption
			Element caption = doc.createElement("div");
			divArgument.appendChild(caption);

			for (String row : bodies.getProperty(element.getArgumentName()).split(ScreenshotsDoc.ROW_SEPARATOR)) {
				Element captionPar = doc.createElement("p");
				caption.appendChild(captionPar);

				Element smallText = doc.createElement("small");
				smallText.setTextContent(row);
				captionPar.appendChild(smallText);
			}
		}

		// create file output
		DOMSource source = new DOMSource(doc);
		FileWriter writer = new FileWriter(new File("index.html"));
		StreamResult result = new StreamResult(writer);

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.transform(source, result);

		System.out.println("[INFO] Manual done!");
	}

	private static String getImageSrc(File imageFile) throws Exception {
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(imageFile));
		byte[] imageBytes = new byte[0];
		for (byte[] ba = new byte[bis.available()]; bis.read(ba) != -1;) {
			byte[] baTmp = new byte[imageBytes.length + ba.length];
			System.arraycopy(imageBytes, 0, baTmp, 0, imageBytes.length);
			System.arraycopy(ba, 0, baTmp, imageBytes.length, ba.length);
			imageBytes = baTmp;
		}
		bis.close();
		return "data:image/png;base64," + DatatypeConverter.printBase64Binary(imageBytes);
	}

}
