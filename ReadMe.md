#Java BDD Test

An example for parsing and testing a site with BDD (Behavior-Driven Development) using [Cucumber](https://cucumber.io/) and [Selenium](https://www.seleniumhq.org/).

This project open a browser (with a driver to configure on *pom.xml* file) and navigate on google search page. Following the feature instructions it search a word and tests the results.

Inside the same feature the project take some **screenshots** (short and complete) and at the end creates an html page with these pages.

This example can help  you to **test a web application** and simultanesly **create an html manual** with the pages screenshots.

## Features

BDD uses the natural language to describe tests:

	    Scenario: Finding some GitHub
    		Given I am on the Google search page
    		When I search for "GitHub"
    		Then the page title should start with "github"


## Running test

    mvn test


On the *pom.xml* file you need to set the path of driver to launch. For more informations see [selenium download page](https://www.seleniumhq.org/download/)