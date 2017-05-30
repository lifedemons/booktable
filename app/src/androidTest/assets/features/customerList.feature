Feature: Browse Customers List

  Background:
    Given User opens Customers List

  @ScenarioId("FUNCTIONAL.LIST.001") @functional-scenarios
  Scenario: User can browse list
    Then User sees customer with name "Charles"

  @ScenarioId("FUNCTIONAL.LIST.002") @functional-scenarios
  Scenario: User can search for certain customer by name
    When User searches for customer with name "Mikhail"
    Then User sees customer with name "Mikhail"