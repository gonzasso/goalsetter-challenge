Feature: Parent Wallet API
  Parent Wallet API

  @API
  Scenario Outline: Login and verify parent <info> equals <value>
    Given I am an authorized user
    When I get the parent information
    Then Parent <info> must be <value>

    Examples:
    | info              | value   |
    | "walletBalance"   | "25.00" |
    | "financialStatus" | "VIP"   |
