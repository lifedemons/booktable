Feature: Browse Customers List

  Background:
    Given User opens Customers List

  @ScenarioId("FUNCTIONAL.LIST.001") @functional-scenarios
  Scenario: User can browse list
    Then User sees customer with title "a at voluptatem"

  @ScenarioId("FUNCTIONAL.LIST.002") @functional-scenarios
  Scenario: User can search for certain customer by title
    When User searches for customer with title "a ea culpa eius"
    Then User sees customer with title "a ea culpa eius"