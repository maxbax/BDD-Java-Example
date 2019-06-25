@google
Feature: Google search

  Scenario: Finding some GitHub
   Given I am on the Google search page
   When I search for "GitHub"
   Then the page title should start with "github"
   
  @screenshot    
  Scenario: SCREENSHOT Google search - SHORT
    Given I am on the Google search page
    When I search for "GitHub"
  	Then the screenshot title is "Search page with GitHub (short)" 
  	And the screenshot body is the next
  		| This is a search page (short). |
  		| Enjoy! |  
  	
  @screenshot    
  Scenario: SCREENSHOT Google search
    Given I am on the Google search page
    When I search for "GitHub"
  	Then the screenshot title is "Search page with GitHub (complete)" 
  	And the screenshot body is the next
  		| This is a search page (complete). |
  		| Enjoy! |  
  	
  	