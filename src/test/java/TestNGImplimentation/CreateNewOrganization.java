package TestNGImplimentation;

import java.io.IOException;
import java.util.Map;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import genericLibraries.AutoConstantpath;
import genericLibraries.BaseClass;

public class CreateNewOrganization extends BaseClass {
	

	@Test
	public void CreatenewOrganization() throws IOException {
		SoftAssert soft = new SoftAssert();

		homepage.ClickOrganizationTab();
		// String OrganizationHeaderLink =
		// driver.findElement(By.xpath("//a[@class=\"hdrLink\"]")).getText();
		soft.assertTrue(organizationpage.getpageHeader().contains("Organization"));

		organizationpage.clickPlusImage();
		// driver.findElement(By.xpath("//img[@title='Create
		// Organization...']")).click();

		// String CreatenewOrganization =
		// driver.findElement(By.xpath("//span[@class=\"lvtHeaderText\"]")).getText();
		soft.assertTrue(organizationpage.getNewOrganization().contains("Creating New Organization"));

		Map<String, String> map = excel.fetchMultipleDataBasedOnKeyFromExcel("TestData", "Create Organization");
		String accountname = map.get("Organization Name") + java.generateRandomNumber(100);
		// driver.findElement(By.name("accountname")).sendKeys(accountname);
		createneworganizationpage.setOrganizationName(accountname);
		createneworganizationpage.selectIndustryDropdown(webdriver, map.get("Industry"));
		createneworganizationpage.clickGroupradiobutton();
//		WebElement industryDropdown = driver.findElement(By.name("industry"));
//		webdriver.dropdown(industryDropdown, map.get("Industry"));
		// Select industry = new Select(industryDropdown);
		// industry.selectByVisibleText("Engineering");


		// driver.findElement(By.xpath("//input[@value=\"T\"]"));
		createneworganizationpage.clickSavebutton();
		// driver.findElement(By.xpath("//input[@accesskey=\"S\"]")).click();

		// String OrganizationInformation =
		// driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
		soft.assertTrue(neworganizationinfopage.getPageHeader().contains(accountname));

		neworganizationinfopage.clickOrganization();
		// driver.findElement(By.xpath("//a[@class='hdrLink']")).click();

		// WebElement newOrganizatio = driver.findElement(By.xpath("//table[@class=\"lvt
		// small\"]/tbody/tr[last()]/td[3]"));
		soft.assertTrue(organizationpage.getNewOrganizationinfo().contains(accountname));
		if (organizationpage.getNewOrganizationinfo().contains(accountname)) {
			System.out.println("Test case pass");
			excel.writeDataIntoExcel("TestData", "pass", AutoConstantpath.EXCEL_FILE_PATH, "Create Organization");

		} else {
			System.out.println("Test case Fail");
			excel.writeDataIntoExcel("TestData", "Fail", AutoConstantpath.EXCEL_FILE_PATH, "Create Organization");
		}
		// homepage.signOutTheApplication(webdriver);

//		WebElement administratorimage = driver.findElement(By.xpath("//img[@src=\"themes/softed/images/user.PNG\"]"));
//		webdriver.mouseHoverToElement(administratorimage);
//		Actions a = new Actions(driver);
//		a.moveToElement(administratorimage).perform();
		// driver.findElement(By.xpath("//a[.='Sign Out']")).click();

//		excel.closeExcel();
//		webdriver.closeBrowser();
		// driver.quit();

	}

}
