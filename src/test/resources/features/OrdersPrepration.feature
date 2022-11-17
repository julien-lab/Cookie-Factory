Feature: preparation of commands

  Background:
    Given 3 clients orders

  Scenario: Order Supervisor send the list of cookies to prepare
    When Order supervisor send cookies to do to the cook
    Then Cook get cookies to do

  Scenario: Cook bake the cookies to prepare
    When Cook bake cookies to prepare
    Then Cookies are ready

  Scenario: Order Supervisor check the order finished and send them to cashier
    When Order Supervisor check order finish and send them to cashier
    Then Cashier get orders ready to be take by the client


