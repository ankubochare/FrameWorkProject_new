package genericUtilityimplementation;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import genericLibraries.AutoConstantpath;
import genericLibraries.ExcellFileUtility;
import genericLibraries.JavaUtility;
import genericLibraries.PropertyFileUtility;
import genericLibraries.WebDriverUtility;

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

		if (driver.getTitle().contains("vtiger")) {
			System.out.println("Pass: Vtiger login page is diplayed");
		} else {
			System.out.println("Fail: Vtiger login page is not displayed");
		}

		driver.findElement(By.xpath("//input[@name=\"user_name\"]")).sendKeys(username);
		driver.findElement(By.xpath("//input[@name=\"user_password\"]")).sendKeys(password);
		driver.findElement(By.id("submitButton")).submit();

		if (driver.getTitle().contains("Administrator")) {
			System.out.println("Pass : Login successful");
		} else {
			System.out.println("Fail : Login not successful");
		}

		driver.findElement(By.xpath("//a[contains(text(),'Contacts')]")).click();

		if (driver.getTitle().contains("Contacts")) {
			System.out.println("Pass: Contact page is dispalyed");
		} else {
			System.out.println("Fail: Contact page not found");
		}

		driver.findElement(By.xpath("//img[@title='Create Contact...']")).click();

		WebElement newcontactveri = driver.findElement(By.xpath("//span[@class='lvtHeaderText']"));// your on
																											// create
																											// new
																											// contcat
		if (newcontactveri.getText().contains("Creating New Contact")) {
			System.out.println("Pass: Create New Contact page is displayed");
		} else {
			System.out.println("Fail: Create New Contact page not found");
		}

		Map<String, String> map = excel.fetchMultipleDataBasedOnKeyFromExcel("TestData", "create Contact");

		WebElement salutation = driver.findElement(By.name("salutationtype"));
		webdriver.dropDown(salutation, map.get("First Name Salutation"));

		String contactName = map.get("Last Name") + javaUtility.generateRandomNumber(100);
		driver.findElement(By.name("lastname")).sendKeys(contactName);
		driver.findElement(By.xpath("(//img[@alt='Select'])[1]")).click();

		String parentWindow = webdriver.getParentWindow();
		webdriver.handleChildBrowserPopup("Accounts&action");

		String requiredOrganizationName = map.get("Organization Name");
		String commonPath = "//div[@id='ListViewContents']/descendant::table[@cellpadding=\"5\"]/tbody/tr";
		List<WebElement> organizationList = driver.findElements(By.xpath(commonPath));
		for (int i = 2; i < organizationList.size(); i++) {
			WebElement organization = driver.findElement(By.xpath(commonPath + "[" + i + "]/td[1]/a"));
			String organizationName = organization.getText();
			if (organizationName.equals(requiredOrganizationName)) {
				organization.click();
				break;
			}
		}
		driver.switchTo().window(parentWindow);

		WebElement contactimage = driver.findElement(By.xpath("//input[@name=\"imagename\"]"));

		contactimage.sendKeys("C:\\Users\\anku\\Desktop\\Screen short\\sleepmethod.png");
		driver.findElement(By.xpath("//input[contains(@value,'Save')]")).click();

		WebElement contactinfo = driver.findElement(By.xpath("//span[@class='dvHeaderText']"));
		if (contactinfo.getText().contains(contactName)) {
			System.out.println("pass: New contact Created sucessfully ");
		} else {
			System.out.println("Fail: contact is not created");
		}

		driver.findElement(By.xpath("//a[@class='hdrLink']")).click();

		WebElement contactpageHeadr = driver
				.findElement(By.xpath("//a[@href='index.php?action=ListView&module=Contacts&parenttab=Marketing']"));
		if (contactpageHeadr.getText().contains("Contacts")) {
			System.out.println("pass: contact page displayed");
		} else {
			System.out.println("Fail: contact page not displayed");
		}
		String newContactName = driver
				.findElement(By.xpath("//table[@class='lvt small']/descendant::tr[last()]/td[4]/a")).getText();
		System.out.println(newContactName);

		if (newContactName.equalsIgnoreCase(contactName)) {
			System.out.println("Test Case Passed");
		} else {
			System.out.println("Test Case Failed");
		}

		WebElement adminImage = driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"));
		webdriver.mouseHoverToElement(adminImage);
		driver.quit();
	}

}
