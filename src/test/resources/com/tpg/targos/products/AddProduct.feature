Feature: Add New Product
  add a new product to be sold via web channel

  Scenario: Add new Product
    Given I am a product buyer "buyer-01"
    And a new product is available
      | product name | product description | product type | product id | manufacturer product reference type | manufacturer product reference | currency | unit price | quantity |
      | The Sony Alpha A60000 | The Sony Alpha A6000 is a 24-megapixel mirrorless camera with the world's fastest AF | Electronics | 554-7207 | EAN | 4905524974409 | £ | 375.99 | 5 |
    When I add the new product
    Then the product is available for purchase via the web channel