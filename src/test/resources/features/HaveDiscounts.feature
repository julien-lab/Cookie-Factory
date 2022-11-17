Feature: Get discounts

  Background:
    Given a client with an account which is part of the cookie factory loyalty program

  Scenario: the client reaches 30 cookies while ordering
    When the client is ordering and has ordered at least 30 cookies from previous orders
    Then the client gets the promised 10% off on his order

  Scenario: the client wants apply loyalty discount but has ordered less than 30 cookies
    When the client is ordering and has ordered less than 30 cookies from previous orders
    Then the client doesn't get the promised 10% off on his order