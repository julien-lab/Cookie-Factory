Feature: Create an account in the cookie factory

  Background:
    Given a client who wants to create an account in the cookie factory

  Scenario: create a simple account
    When the client create his simple account to his name
    Then the client now has an account to his name

  Scenario: join loyalty program
    When the client create his account to his name and join loyalty program
    Then the client now has an account to his name with a loyalty program