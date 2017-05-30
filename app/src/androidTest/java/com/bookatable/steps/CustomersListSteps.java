package com.bookatable.steps;

import com.bookatable.pages.CustomerListPage;
import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CustomersListSteps {

  private CustomerListPage mCustomerListPage;

  public CustomersListSteps(CustomerListPage customerListPage) {
    mCustomerListPage = customerListPage;
  }

  @After("@functional-scenarios") public void tearDown() throws Exception {
    mCustomerListPage.tearDown();
  }

  @Given("^User opens Customers List")
  public void user_opens_the_login_page() {
    mCustomerListPage.open();
  }

  @Then("^User sees customer with name \"([^\"]*)\"$")
  public void userSeesCustomerWithTitle(String title) throws Throwable {
    mCustomerListPage.checkListContainsCustomer(title);
  }

  @When("^User searches for customer with name \"([^\"]*)\"$")
  public void userSearchesForCustomerWithTitle(String title) throws Throwable {
    mCustomerListPage.doSearchForCustomer(title);
  }
}
