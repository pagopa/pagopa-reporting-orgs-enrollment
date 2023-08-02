Feature: All about Organizations

  Background:
    Reporting Enrollment running

  Scenario: An organization creates an enrollment
    Given a random id organization
    When the organization "creates" enrollment 
    Then the organization gets the status code 201

  Scenario: An organization deletes an enrollment
    Given a valid enrollment
    When the organization "deletes" enrollment 
    Then the organization gets the status code 200
    
  Scenario: An organization get an enrollment
  	Given a valid enrollment
  	When the organization "gets" enrollment 
  	Then the organization gets the status code 200
  
  Scenario: An organization gets all the enrollments
  	Given a valid enrollment
  	When the organization "gets all" enrollment
  	Then the organization gets the status code 200
  
# 404

  Scenario: An organization deletes an enrollment that does not exist
    Given a random id organization
    When the organization "deletes" enrollment 
    Then the organization gets the status code 404

  Scenario: An organization get an enrollment that does not exist
  	Given a random id organization
  	When the organization "gets" enrollment 
  	Then the organization gets the status code 404
  	
# 409

 Scenario: An organization creates an enrollment that already exists
    Given a valid enrollment
    When the organization "creates" enrollment 
    Then the organization gets the status code 409
