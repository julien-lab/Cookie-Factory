Feature: Create a customized cookie Recipe

  Background:
    Given a client who wants to create a customized cookie recipe

  Scenario: create a valid customized cookie recipe
    When the client creates his customized recipe and that recipe is valid
    Then the recipe is ready to be added to an order

  Scenario: create an invalid customized cookie recipe
    When the client creates his customized recipe and that recipe is invalid
    Then the recipe is deleted