@Test
Feature: Test API Zinio

  Scenario: Get Catalog Zinio API
    Given send a POST request to take the authentication token
    When send a GET request to get the Zinio Catalog
    Then check the catalog request is correct

  Scenario: Get Catalog Zinio API with a invalid Token
    Given an invalid token
    When send a GET request to get the Zinio Catalog
    Then check the catalog request return a 401 Unauthorized: The access token is invalid or has expired

  Scenario: Get Catalog Zinio API without Token
    Given an empty token
    When send a GET request to get the Zinio Catalog
    Then check the catalog request return a 401 Unauthorized: The access token is missing