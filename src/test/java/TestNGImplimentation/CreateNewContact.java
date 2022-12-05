package TestNGImplimentation;

import java.io.IOException;
import java.util.Map;

import org.testng.annotations.Test;

import genericLibraries.AutoConstantpath;
import genericLibraries.BaseClass;

@Test
public class CreateNewContact extends BaseClass {
	public void CreatenewContact() throws IOException {

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
		String contactName = map.get("Last Name") + java.generateRandomNumber(100);
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
