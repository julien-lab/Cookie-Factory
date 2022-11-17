Feature: Choose the pick-up time of an order

  Background:
    Given two orders with cookies

  Scenario: set order pick-up time
    When the client sets order pick-up time and day
    Then the order can be validated

  Scenario: set wrong order pick-up time
    When the client sets a wrong order pick-up time
    Then the order cannot be validated

  Scenario: set same pick-up time for the 2 orders
    When the clients set the same pick-up time for their orders
    Then one client won't be able to take this time slot
