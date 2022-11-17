Feature: balancing orders in the different shops

  Background:
    Given some shops and an order

  Scenario: there is a technical issue in the shop the client choose in his order
    When the client try to choose a pick up date
    Then the order can't be validated
    And an other open shop close to this one is proposed
    And the client can chose this new shop and validate his order

  Scenario: there is and ingredient issue in the shop chosen
    When the client try to validate his order
    Then he can't
    And a close shop with the ingredient is proposed
    And the client can validate his order with this shop
