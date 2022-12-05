package pomImplementation;

import java.io.IOException;
import java.util.Map;

import org.openqa.selenium.WebDriver;

import genericLibraries.AutoConstantpath;
import genericLibraries.ExcellFileUtility;
import genericLibraries.JavaUtility;
import genericLibraries.PropertyFileUtility;
import genericLibraries.WebDriverUtility;
import pomPages.Contactpage;
import pomPages.CreatenewContactpage;
import pomPages.Loginpage;
import pomPages.NewContactinfopage;
import pomPages.homePage;

public class CreateNewContact {
	public static void main(String[] args) throws IOException {

		WebDriverUtility webdriver = new WebDriverUtility();
		JavaUtility javaUtility = new JavaUtility();

		PropertyFileUtility property = new PropertyFileUtility();
		property.propertyFileInitialization(AutoConstantpath.PROPERTY_FILE_PATH);

		ExcellFileUtility excel = new ExcellFileUtility();
		excel.excelFileInitialization(AutoConstantpath.EXCEL_FILE_PATH);

		String url = property.getDataFromPropertyFile("url");
		String username = property.getDataFromPropertyFile("username");
		String password = property.getDataFromPropertyFile("password");
		long time = Long.parseLong(property.getDataFromPropertyFile("timeouts"));

		WebDriver driver = webdriver.openBrowserAndApplication(url, time);

		Loginpage loginpage = new Loginpage(driver);
		Contactpage contactpage = new Contactpage(driver);
		CreatenewContactpage createnewcontactpage = new CreatenewContactpage(driver);
		NewContactinfopage newcontactinfopage = new NewContactinfopage(driver);
		homePage homepage = new homePage(driver);

		if (loginpage.getLogo().isDisplayed()) {
			System.out.println("Pass: Vtiger login page is diplayed");
		} else {
			System.out.println("Fail: Vtiger login page is not displayed");
		}

//		driver.findElement(By.xpath("//input[@name=\"user_name\"]")).sendKeys(username);
//		driver.findElement(By.xpath("//input[@name=\"user_password\"]")).sendKeys(password);
//		driver.findElement(By.id("submitButton")).submit();

		loginpage.loginToApplication(username, password);

		if (homepage.getpageHeaderText().contains("Home")) {
			System.out.println("Pass : Login successful");
		} else {
			System.out.println("Fail : Login not successful");
		}

		homepage.ClickContactsTab();

		if (contactpage.getHeader().contains("Contacts")) {
			System.out.println("Pass : Contacts page displayed");
		} else {
			System.out.println("Fail : Contacts page not displayed");
		}

		contactpage.clickplusButton();

		if (createnewcontactpage.getPageHeader().contains("Creating New Contact")) {
			System.out.println("Pass : Creating new Contact page is displayed");
		} else {
			System.out.println("Fail : Creating new Contact page is not displayed");
		}

		Map<String, String> map = excel.fetchMultipleDataBasedOnKeyFromExcel("TestData", "Create Contact");

		createnewcontactpage.selectFirstNameSalutation(webdriver, map.get("First Name Salutation"));
		String contactName = map.get("Last Name") + javaUtility.generateRandomNumber(100);
		createnewcontactpage.setLastName(contactName);
		createnewcontactpage.selectExistingOrganization(webdriver, map.get("Organization Name"), driver);
		createnewcontactpage.uploadContactImage(map.get("Contact Image"));
		createnewcontactpage.clickSaveButton();

		if (newcontactinfopage.getPageHeader().contains(contactName)) {
			System.out.println("Pass : New contact created successfully");
		} else {
			System.out.println("Fail : Contact is not created");
		}

		newcontactinfopage.clickContactsLink();

		if (contactpage.getHeader().contains("Contacts")) {
			System.out.println("Pass : Contacts page displayed");
		} else {
			System.out.println("Fail : Contacts page is not displayed");
		}

		if (contactpage.getLastContatName().equalsIgnoreCase(contactName)) {
			System.out.println("Test Case Passed");
			excel.writeDataIntoExcel("TestData", "Pass", AutoConstantpath.EXCEL_FILE_PATH, "Create Contact");
		}

		else {
			System.out.println("Test Case Failed");
			excel.writeDataIntoExcel("TestData", "Fail", AutoConstantpath.EXCEL_FILE_PATH, "Create Contact");
		}

		homepage.signOutTheApplication(webdriver);

		excel.closeExcel();
		webdriver.closeBrowser();

	}
}
