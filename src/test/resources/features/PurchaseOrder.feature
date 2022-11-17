Feature: purchase an order

  Background:
    Given a client
    And an order with cookie
    And a shop
    And a Local Time and a day

  Scenario: the client purchase his order without problem
    When the client has chosen a shop and a pickup time before paying his order and has enough money
    Then Order is added to the shop
    And Ingredients of the order is consumed in the storage shop's
    And Order is added in the list of order to prepare of the OrderSupervisor
    And Order pick up time is of the time availables
    And Order is added to the client's list of orders
    And The payment of the order is marked as done

  Scenario: the client order's can't be purchased
    When the client tries to pay his order without a shop
    Then the order isn't purchased 1

    When the client tries to pay his order without a pick up time
    Then the order isn't purchased 2

    When the client tries to pay his order but doesn't have enough money
    Then the order isn't purchased 3