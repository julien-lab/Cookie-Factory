Feature: Withdrawn Orders

  Background:
    Given Clients of orders
    And Cashiers of different shops
    And Orders ready to be withdrawn

  Scenario: a Client pick up his order in the good shop
    When the client asks his order in the good shop
    Then the cashier gives him and the order is remove from the list of orders ready

  Scenario: a Client pick up his order in the wrong shop
    When the client asks his order in the wrong shop
    Then the cashier does not give him and the order is still in the list of orders ready of the good shop
