Feature: Manage Shop

  Background:
    Given 3 customers orders purchased and withdrawn

  Scenario: National manager wants to add recipe
    When the manager wants to create a new recipe
    Then a new recipe is created

  Scenario: National manager wants to delete recipe
    When the manager wants to delete the recipe number 1
    Then the recipe number 1 is deleted

  Scenario: National manager wants to retrieve orders data
    When the National manager retrieve orders that has been purchased and withdrawn
    Then he gets a list of orders
