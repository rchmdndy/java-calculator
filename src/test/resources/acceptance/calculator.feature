Feature: Calculator
  As a user, I want to use a calculator so that I don't need to add numbers myself.

  Scenario Outline: Add two numbers
    Given the calculator endpoint is available
    When I add <a> and <b>
    Then the result should be <result>

    Examples:
      | a | b | result |
      | 1 | 2 | 3      |
      | 3 | 4 | 7      |
      | 10| 20| 30     |
      | 0 | 0 | 0      |
      | -1| 5 | 4      |
