Feature: Discounts on the most ordered cookie

  Background:
    Given many orders placed

  Scenario: The local manager wants to know the most popular cookie of his fucking damn shop
    When the local manager ask for the most popular cookie of his shop from the last 30 days
    Then he gets the most popular of his shop

  Scenario: The national manager wants to know the most popular cookie of his entire production chain
    When the national manager ask for the most popular cookie of his entire production chain
    Then he gets the most popular cookie recipe of his entire production chain

  Scenario: a Client wants to order the best cookie of the cookie factory
    When the client orders the best cookie of the cookie factory
    Then he gets a discount on his order

  Scenario: a Client wants to order the best cookie of his shop
    When the client orders the best cookie of his shop
    Then he gets a discount on his Order

