package pomImplementation;

import java.io.IOException;
import java.util.Map;

import org.openqa.selenium.WebDriver;

import genericLibraries.AutoConstantpath;
import genericLibraries.ExcellFileUtility;
import genericLibraries.JavaUtility;
import genericLibraries.PropertyFileUtility;
import genericLibraries.WebDriverUtility;
import pomPages.CreateNewLeadPage;
import pomPages.DuplicatingLeadPage;
import pomPages.LeadsPage;
import pomPages.Loginpage;
import pomPages.NewLeadInfoPage;
import pomPages.homePage;

public class CreateNewLead {

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
		homePage homepage = new homePage(driver);
		LeadsPage leadsPage = new LeadsPage(driver);
		CreateNewLeadPage createNewLead = new CreateNewLeadPage(driver);
		NewLeadInfoPage newLead = new NewLeadInfoPage(driver);
		DuplicatingLeadPage duplicatingPage = new DuplicatingLeadPage(driver);

		if (loginpage.getLogo().isDisplayed()) {
			System.out.println("Pass: Vtiger login page is diplayed");
		} else {
			System.out.println("Fail: Vtiger login page is not displayed");
		}

		loginpage.loginToApplication(username, password);

		if (homepage.getpageHeaderText().contains("Home")) {
			System.out.println("Pass : Login successful");
		} else {
			System.out.println("Fail : Login not successful");
		}

		homepage.ClickLeadsTab();

		leadsPage.clickPlusButton();
		// WebElement createLeadHeaderText =
		// driver.findElement(By.xpath("//span[@class='lvtHeaderText']"));
		if (createNewLead.getPageHeader().contains("Creating New Lead")) {
			System.out.println("Pass : Creating new lead page is displayed");
		} else {
			System.out.println("Fail : Creating new lead page is not displayed");
		}

		Map<String, String> map = excel.fetchMultipleDataBasedOnKeyFromExcel("TestData", "Create Lead");

		createNewLead.selectSalutation(webdriver, map.get("First Name Salutation"));

		String leadName = map.get("Last Name") + javaUtility.generateRandomNumber(100);
		createNewLead.setLastName(leadName);
		createNewLead.setCompany(map.get("Company"));
		createNewLead.clickSaveButton();

		if (newLead.getPageHeader().contains(leadName)) {
			System.out.println("Pass : New lead created successfully");
		} else {
			System.out.println("Fail : Lead is not created");
		}

		newLead.clickDuplicateButton();

		if (duplicatingPage.getPageHeader().contains(leadName)) {
			System.out.println("Pass : Duplicating page displayed");
		} else {
			System.out.println("Fail : Duplicating page is not displayed");
		}

		String newLastName = map.get("New Last Name") + javaUtility.generateRandomNumber(100);
		duplicatingPage.setNewLeadName(newLastName);
		duplicatingPage.clickSaveButton();

		if (newLead.getPageHeader().contains(newLastName)) {
			System.out.println("Pass : New lead created successfully");
		} else {
			System.out.println("Fail : Lead is not created");
		}

		newLead.clickLeads();
		if (leadsPage.getLastLeadName().equals(newLastName)) {
			System.out.println("Test Case passed");
			excel.writeDataIntoExcel("TestData", "Pass", AutoConstantpath.EXCEL_FILE_PATH, "Create Lead");
		}

		else {
			System.out.println("Test Case Failed");
			excel.writeDataIntoExcel("TestData", "Fail", AutoConstantpath.EXCEL_FILE_PATH, "Create Lead");
		}

		homepage.signOutTheApplication(webdriver);

		excel.closeExcel();
		webdriver.closeBrowser();
	}


}
