Feature: Savings Page
  Child Allowance Info Verification

  @Android
  Scenario Outline: Login and verify child allowance page header is correct
    Given I login to the app
    When I tap on child Savings button on Home Page
    Then Child Savings header should be <header>

    Examples:
      | header              |
      | "German's Savings"  |