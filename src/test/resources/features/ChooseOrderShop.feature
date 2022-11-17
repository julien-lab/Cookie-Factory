Feature: Client choose a shop for his order

  Background:
    Given an Order with cookies
    And 2 shops with different stocks of ingredients

  Scenario: validate the shop choosen
    When the client choose a shop which can do his order
    Then the shop is added to the order

  Scenario: invalidate the shop choosen
    When the client choose a shop which can't do his order
    Then the shop isn't added to the order