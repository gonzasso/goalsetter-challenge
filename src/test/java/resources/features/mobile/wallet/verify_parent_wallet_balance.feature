Feature: Parent Wallet Page
  Wallet Balance operations

  @Android
  Scenario Outline: Login and verify parent wallet info and balance
    Given I login to the app
    When I tap on parent wallet button on Home Page
    Then Parent wallet header should be <title>
    And Wallet balance should be <balance>

    Examples:
      | title        | balance |
      | "My Wallet"  | "$25"   |