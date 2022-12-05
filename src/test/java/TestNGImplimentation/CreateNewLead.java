package TestNGImplimentation;

import java.io.IOException;
import java.util.Map;

import org.testng.annotations.Test;

import genericLibraries.AutoConstantpath;
import genericLibraries.BaseClass;

public class CreateNewLead extends BaseClass {

	@Test
	public void CreatenewLead() throws IOException {

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

		String leadName = map.get("Last Name") + java.generateRandomNumber(100);
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

		String newLastName = map.get("New Last Name") + java.generateRandomNumber(100);
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
