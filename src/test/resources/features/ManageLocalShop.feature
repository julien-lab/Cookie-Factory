Feature: Manage local shop

  Background:
    Given a local who wants to change opening time and closing time

  Scenario: the local manager change opening time of his shop
    When the local manager change the opening time of his shop
    Then the opening time is changed

  Scenario: the local manager change closing time of his shop
    When the local manager change the closing time of his shop
    Then the closing time is changed

  Scenario: the local manager wants to access his shop statistic
    When the local manager access his shop statistics
    Then the local manager gets his shop statistics