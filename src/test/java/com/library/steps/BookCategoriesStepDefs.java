package com.library.steps;

import com.library.pages.BookPage;
import com.library.utility.BrowserUtil;
import com.library.utility.DB_Util;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.List;
import java.util.Map;

public class BookCategoriesStepDefs {
    BookPage bookPage=new BookPage();

    @When("the user navigates to {string} page")
    public void the_user_navigates_to_page(String moduleName) {
        bookPage.navigateModule(moduleName);
        BrowserUtil.waitFor(2);

    }
    List<String> actualCategories;
    @When("the user clicks book categories")
    public void the_user_clicks_book_categories() {
         actualCategories = BrowserUtil.getAllSelectOptions(bookPage.mainCategoryElement);
        System.out.println("actualCategories = " + actualCategories);

        // EXCLUDE ALL FROM UI
        actualCategories.remove(0);
        System.out.println("----AFTER EXCLUDE ALL FROM LIST ----");
        System.out.println("actualCategories = " + actualCategories);



    }
    @Then("verify book categories must match book_categories table from db")
    public void verify_book_categories_must_match_book_categories_table_from_db() {

        String query="select name from book_categories";

        DB_Util.runQuery(query);

        List<String> expectedCategories = DB_Util.getColumnDataAsList(1);

        Assert.assertEquals(expectedCategories,actualCategories);

    }


    @When("the librarian click to add book")
    public void the_librarian_click_to_add_book() {
        bookPage.addBook.click();
    }
    @When("the librarian enter book name {string}")
    public void the_librarian_enter_book_name(String name) {
        bookPage.bookName.sendKeys(name);
    }
    @When("the librarian enter ISBN {string}")
    public void the_librarian_enter_isbn(String isbn) {
        bookPage.isbn.sendKeys(isbn);
    }
    @When("the librarian enter year {string}")
    public void the_librarian_enter_year(String year) {
        bookPage.year.sendKeys(year);
    }
    @When("the librarian enter author {string}")
    public void the_librarian_enter_author(String author) {
        bookPage.author.sendKeys(author);
    }
    @When("the librarian choose the book category {string}")
    public void the_librarian_choose_the_book_category(String catagory) {
        BrowserUtil.selectOptionDropdown(bookPage.categoryDropdown, catagory);
    }
    @When("the librarian click to save changes")
    public void the_librarian_click_to_save_changes() {
        bookPage.saveChanges.click();
    }
    @Then("verify {string} message is displayed")
    public void verify_message_is_displayed(String string) {
        Assert.assertTrue(bookPage.toastMessage.isDisplayed());
    }
    @Then("verify {string} information must match with DB")
    public void verify_information_must_match_with_db(String expectedBookName) {
    String query = "select name, author, isbn from books\n" +
            "where name = '"+expectedBookName+"'";

    DB_Util.runQuery(query);

        Map<String, String> rowMap = DB_Util.getRowMap(1);

        String actualBookName = rowMap.get("name");

        Assert.assertEquals(expectedBookName, actualBookName);
    }
}
