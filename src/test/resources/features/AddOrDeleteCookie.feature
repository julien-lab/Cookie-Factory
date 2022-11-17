Feature: Add/delete cookie from an order

  Background:
    Given a client with no account
    And a new order

  Scenario Outline: add a certain number of cookies
    When the client adds "<number>" of "<name>" cookies
    Then there is a new orderLine of "<number>" "<name>" in the order
    Examples:
      |   number    |           name                   |
      |      1      |     Chocolate / Chili            |
      |      2      |     Oatmeal / Cinnamon           |
      |      3      |     Plain / Vanilla              |
      |      4      |     Oatmeal / Vanilla            |
      |      7      |     Peanut Butter / Cinnamon     |
      |     50      |     Plain / Chili                |
      |     35      |     Chocolate                    |


  Scenario Outline: delete a cookie
    When there are/is "<initial-number>" "<name>" cookies and the client deletes "<number-to-delete>" "<name>"
    Then there are/is "<final-number>" "<name>" in the orderLine of "<name>"
    Examples:
      |   initial-number   |             name                      |     number-to-delete     |     final-number    |
      |         4          |        Chocolate / Chili              |            3             |          1          |
      |         7          |        Oatmeal / Cinnamon             |            3             |          4          |
      |        10          |        Plain / Vanilla                |            7             |          3          |
      |        33          |        Oatmeal / Vanilla              |           10             |         23          |
      |         6          |        Peanut Butter / Cinnamon       |            3             |          3          |
      |        15          |        Plain / Chili                  |            3             |         12          |
      |         9          |        Chocolate                      |            3             |          6          |


  Scenario Outline: delete cookies to the point where there is no left
    When there are/is "<number>" "<name>" cookies and the client deletes all them
    Then the orderline for that cookie recipe is removed
    Examples:
      |       number       |             name                      |
      |         4          |        Chocolate / Chili              |
      |         7          |        Oatmeal / Cinnamon             |
      |        10          |        Plain / Vanilla                |
      |        33          |        Oatmeal / Vanilla              |
      |         6          |        Peanut Butter / Cinnamon       |
      |        15          |        Plain / Chili                  |
      |         9          |        Chocolate                      |

  Scenario Outline: delete more cookies than there is in the orderline
    When there are/is "<initial-number>" "<name>" and the client deletes "<number-to-delete>"
    Then the orderline fot that cookie recipe is removed
    Examples:
      |  initial-number    |             name                      |        number-to-delete      |
      |         4          |        Chocolate / Chili              |                8             |
      |         7          |        Oatmeal / Cinnamon             |               10             |
      |        10          |        Plain / Vanilla                |               15             |
      |        33          |        Oatmeal / Vanilla              |               40             |
      |         6          |        Peanut Butter / Cinnamon       |                8             |
      |        15          |        Plain / Chili                  |               16             |
      |         9          |        Chocolate                      |               10             |