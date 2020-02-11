package org.example;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ReusableMethod
{      // initializing object for WebDriver
    static protected WebDriver driver;

    @Before // 1) this openBrowser method will run always first before running any other method
    public void openBrowser()
    {
        System.setProperty("webdriver.chrome.driver", "src\\test\\java\\BrowsersDriver\\chromedriver.exe");
        driver = new ChromeDriver();        //launching chromeDriver
        driver.manage().window().fullscreen();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    // 2) creating timeStamp method for unique number to use in email
    public String timeStamp()
    {
        DateFormat dateFormat = new SimpleDateFormat("ddMMyyHHmmss");
        Date date = new Date();
        return (dateFormat.format(date));
    } // 2 A) creating timeStampWithoutSecond method for unique number to use in email when asking to confirmEmail
        //as using above method second will change and email address not match in SuperDrug Website
    public String timeStampWithoutSecond()
    {
        DateFormat dateFormat = new SimpleDateFormat("ddMMyyHHmm");
        Date date = new Date();
        return (dateFormat.format(date));
    }
    //3) creating method for  waitForClickable with two parameter
    public void waitForClickable(By by ,int time)
    {
        WebDriverWait waitClickable = new WebDriverWait(driver,time);
        waitClickable.until (ExpectedConditions.visibilityOfElementLocated(by));
    }
    //4) creating method for text to be visible with two parameter
    public void waitForVisible(By by , int time)
    {
        WebDriverWait waitVisibility = new WebDriverWait(driver,time);
        waitVisibility.until(ExpectedConditions.visibilityOfElementLocated(by));
    }
    //5) creating method for wait for element is present
    public void waitForElementIsPresent(By by , int time)
    {
        WebDriverWait waitPresent = new WebDriverWait(driver,time);
        waitPresent.until (ExpectedConditions.visibilityOfElementLocated(by));
    }
    // 6) creating parameterized method for clicking an element
    public void clickOnElement(By by)
    {
        driver.findElement(by).click();
    }
    // 7) creating method for enter text/sendKey
        public void sendKey(By by , String text)
        {
            driver.findElement(by).sendKeys(text);
        }
    // 8) creating method for selectFromDropDownByValue
        public  void selectFromDropDownByValue(By by , String text)
        {
            Select select = new Select(driver.findElement(by));
            select.selectByValue(text);
        }
        //9) creating method for selectFromDropDownByVisible Text
        public void selectFromDropDownByVisibleText(By by ,String text)
        {
            Select select = new Select(driver.findElement(by));
            select.selectByVisibleText(text);
        }
        // 10) creating method for selectFromDropDownByIndex
        public void selectFromDropDownByIndex(By by ,int index ) {
            Select select = new Select(driver.findElement(by));
            select.selectByIndex(index);
        }
         // 11) creating method for getText which is returning string value
        public String getText(By by)
        {
            String get_text = driver.findElement(by).getText();
            return get_text;
        }
        //This method will run at last to close browser
        @After
        public void methodToCloseBrowser()
        {
            driver.quit();
        }

    @Test  //user should register successfully in nop commerce website
    public void userShouldAbleRegisterSuccessfullyNopCommerce()
    {   //open url
        driver.get("https://demo.nopcommerce.com/");

        clickOnElement(By.linkText("Register"));
        //waiting until page load and show register button
        waitForClickable(By.name("register-button"),60);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
       // waitForClickable(By.cssSelector("input#gender-female"),40);
        //using above method i am not able to print first name always so used thread.sleep
        clickOnElement(By.cssSelector("input#gender-female"));
        sendKey(By.cssSelector("input#FirstName"),"Tarjani");
        waitForElementIsPresent(By.cssSelector("input#FirstName"),50);
        sendKey(By.name("LastName"),"Kachhia");

        //filling out all other fields
        selectFromDropDownByValue(By.name("DateOfBirthDay"),"12");
        selectFromDropDownByIndex(By.name("DateOfBirthMonth"),4);
        selectFromDropDownByVisibleText(By.name("DateOfBirthYear"),"1981");
        sendKey(By.id("Email"),"kachhiatarjani+"+timeStamp()+"@gmail.com");
        sendKey(By.id("Password"),"Abcd123");
        sendKey(By.cssSelector("input#ConfirmPassword"),"Abcd123");
        clickOnElement(By.id("register-button"));

        //Actual result is what we get after registration
        String actual = getText(By.className("result"));

        //expected result is  our requirement
        String expected ="Your registration completed";

        //if both result match then test pass otherwise fail with user defined message
        Assert.assertEquals("Test Fail",expected,actual);
        }

    @Test //user able to login in next website
        public void userAbleToRegisterSuccessfullyNext()
    {
        //get url
        driver.get("https://www.next.co.uk/");

        //click on account button using clickOnMethod
        clickOnElement(By.xpath("//a[contains(@title,'account ')]"));
        clickOnElement(By.xpath("//span[contains(text(),'Register Now')]"));

        //select Mrs using selectFromDropDownVisibleText method
        selectFromDropDownByVisibleText(By.id("Title"),"Mrs");

        //filling out other fields using sendKey method with providing locator and text
        sendKey(By.cssSelector("input#FirstName"),"Tarjani");
        sendKey(By.name("LastName"),"Kachhia");
        sendKey(By.id("Email"),"kachhiatarjani+"+ timeStamp()+ "@gmail.com");
        sendKey(By.xpath("//input[@type='password']"),"Abcd123");

        //clicking on show password
        clickOnElement(By.cssSelector("span.show"));

        //filling out other information
        sendKey(By.id("DobDate"),"20 04 85");
        sendKey(By.name("PhoneNumber"),"07894905098");
        sendKey(By.cssSelector("input#HouseNumberOrName"),"68");
        sendKey(By.id("Postcode"),"HA0 2LH");
        clickOnElement(By.cssSelector("button#SearchPostcode"));
        clickOnElement(By.xpath("//label[contains(text(),'YES')]"));
        clickOnElement(By.name("ChkByPost"));
        clickOnElement(By.id("ChkByTel"));
        clickOnElement(By.cssSelector("button#SignupButton"));
        clickOnElement(By.id("accMatchContinue"));

        //this expected is requirement
        String expected = "Thank you for registering.";
        //actual we are getting from testing
        String actual=getText(By.xpath("//p[contains(@class,'register')]"));
        //if both expected and actual is equal then test is pass else fail
        Assert.assertEquals("Test Failed",expected,actual);
    }

       @Test
      public void userShouldAbleToRegisterSuccessfullySuperDrug()
       {
           //get url
           driver.get("https://www.superdrug.com/register");
           //click on register
           clickOnElement(By.xpath("//span[contains(text(),'Register')]"));
           clickOnElement(By.linkText("Join today!"));

           //filling out all other fields using send key and click on method
           //Used timeStampMethodWithoutSecond as it required confirmEmail address
           sendKey(By.id("email"), "kachhiatarjani+" + timeStampWithoutSecond()+"@gmail.com");
           sendKey(By.name("confirmEmail"), "kachhiatarjani+"+ timeStampWithoutSecond()+"@gmail.com");
           sendKey(By.cssSelector("input#pwd"), "Abcd1234");
           sendKey(By.name("checkPwd"), "Abcd1234");

           //proceed to another page an filling out fields
           selectFromDropDownByValue(By.id("day_select"),"21");
           selectFromDropDownByVisibleText(By.cssSelector("select#month_select"),"April");
           selectFromDropDownByValue(By.cssSelector("select#year_select"),"1981");
           clickOnElement(By.xpath("//button[contains(@id,'nexrt_cta')]"));
            selectFromDropDownByValue(By.id("address.title"),"mrs");
            sendKey(By.cssSelector("input#addressFirstname"),"Tj");
            sendKey(By.name("lastName"),"Kachhia");
            sendKey(By.id("house_number"),"68");
            sendKey(By.cssSelector("input#post_code"),"HA0 2LH");
            clickOnElement(By.id("find_address"));
            selectFromDropDownByValue(By.id("postcode_addresslist"),"10413701");
            waitForClickable(By.xpath("//input[@name='acceptTermsAndConditions']"),20);

            //Accepting cookies
            clickOnElement(By.id("truste-consent-button"));

            clickOnElement(By.id("acceptTermsId"));
            clickOnElement(By.cssSelector("button#next_cta"));
            clickOnElement(By.xpath("//button[contains(@class,'popup__ok')]"));
            //waiting element to be present before click
            waitForElementIsPresent(By.xpath("//li[contains(@id,'nav__user')]//span[contains(@class,'icon__text')]"),20);

            //this is the message shows once registered
           String actual = getText(By.xpath("//li[contains(@id,'nav__user')]//span[contains(@class,'icon__text')]"));

           //This is Requirement
            String expected = "Hello Tj";

            //If expected match actual then Test pass otherwise fail with userDefined message
            Assert.assertEquals("Test Failed",actual,expected);
       }

       @Test
            public void clickingOnRugCategoryAndSortByLowToHighUserAbleToSeeProductLowToHigh()
       {
           driver.get("https://www.dunelm.com/");
           clickOnElement(By.xpath("//a[contains(@data-testid,'navLink') and contains(text(),'Rugs')]"));

           //selecting from drop down price low to high
           selectFromDropDownByValue(By.name("sort-by"),"price_asc");

            //actual url from web page given by inbuilt method
           String actual_url =  driver.getTitle();

          ////expected url from page source
           String expected_url = "Rugs | Modern, Shaggy & Large Rugs | Dunelm";

            Assert.assertEquals("Test Failed", actual_url,expected_url);
       }

        @Test
        public void purposelyFailIkeaRegistration()
        {
            //get url
          driver.get("https://www.ikea.com/gb/en/");
          //click on profile icon
          clickOnElement(By.xpath("//a[contains(@title,'My profile')]"));
          //click on create new account
          clickOnElement(By.xpath("//a[contains(@href,'regular')]"));
          //filling out all fields
          sendKey(By.xpath("//input[contains(@id,'firstName')]"),"Tj");
          sendKey(By.xpath("//input[contains(@id,'lastName')]"),"Kachhia");
          sendKey(By.name("mobile"),"07894905098");
          sendKey(By.xpath("//input[(@type='email')]"),"kachhiatarjani+"+timeStamp()+"@gmail.com");
          sendKey(By.xpath("//input[(@type='password')]"),"Abcd1234!");
          //Accept cookies
          clickOnElement(By.xpath("//span[contains(text(),'OK')]"));
          //Tick check box with email and top checkbox tick automatically
          clickOnElement(By.name("allowEmail"));
          //click on create profile
         clickOnElement(By.xpath("//span[contains(text(),'Create profile')]"));

         //Actual result from web page
         String actual = getText(By.xpath("//h1[contains(@id,'headline')]"));
         //expected result from requirement
         String expected = "Welcome Tj";
         //Matching two results
         Assert.assertEquals("TEST FAILED",expected,actual);
         //Results don't match so test will failed and message( given by individual ) will appear
        }
}





