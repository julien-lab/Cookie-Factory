Feature: choose lastMinuteMarcel delivery

  Background:
    Given a client with an order

  Scenario: lastMinuteMarcel receives a doable delivery demand
    When lastMinuteMarcel receives a delivery demand that it can do
    Then the order's lastMinuteMarcelstatus is properly updated
    And the first rider who was initially available becomes unavailable
    And a ride is created and assigned to the rider
    And the order's price reflects the standard delivery fee

  Scenario: lastMinuteMarcel receives a doable late delivery demand
    When lastMinuteMarcel receives a late delivery demand that it can do
    Then lastMinuteMarcelstatus is properly updated
    And the first rider becomes unavailable
    And a ride is created and is assigned to the rider
    And the order's price reflects the late delivery fee

  Scenario: lastMinuteMarcel receives an undoable delivery demand
    When lastMinuteMarcel receives a delivery demand but has no available rider left
    Then the order cannot be delivered by lastMinuteMarcel




