package genericUtilityimplementation;

import java.io.IOException;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import genericLibraries.AutoConstantpath;
import genericLibraries.ExcellFileUtility;
import genericLibraries.JavaUtility;
import genericLibraries.PropertyFileUtility;
import genericLibraries.WebDriverUtility;

public class CreateNewOrganization {
	
	public static void main(String[] args) throws IOException {

		ExcellFileUtility excel = new ExcellFileUtility();
		JavaUtility javautility = new JavaUtility();
		PropertyFileUtility property = new PropertyFileUtility();
		WebDriverUtility webdriver = new WebDriverUtility();

		property.propertyFileInitialization(AutoConstantpath.PROPERTY_FILE_PATH);
		excel.excelFileInitialization(AutoConstantpath.EXCEL_FILE_PATH);

		// Random random = new Random();
		// int randomNum = random.nextInt(100);

		String url = property.getDataFromPropertyFile("url");
		String time = property.getDataFromPropertyFile("timeout");
		WebDriver driver = webdriver.openBrowserAndApplication(url, Long.parseLong(time));

		// WebDriverManager.chromedriver().setup();
		// WebDriver driver = new ChromeDriver();
		// driver.manage().window().maximize();
		// driver.get("http://localhost:8888/index.php?action=Login&module=Users");
		// driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		WebElement logo = driver.findElement(By.xpath("//img[@alt=\"logo\"]"));
		if (logo.isDisplayed()) {
			System.out.println("pass: loginpage displayed");
		} else {
			System.out.println("Fail: Loginpage not found");
		}

		String username = property.getDataFromPropertyFile("username");
		String password = property.getDataFromPropertyFile("password");

		driver.findElement(By.xpath("//input[@name=\"user_name\"]")).sendKeys("username");
		driver.findElement(By.xpath("//input[@name=\"user_password\"]")).sendKeys("password");
		driver.findElement(By.id("submitButton")).click();

		String HeaderLink = driver.findElement(By.xpath("//a[@class=\"hdrLink\"]")).getText();
		if (HeaderLink.contains("Home")) {
			System.out.println("pass: Home page is diaplsyed");
		} else {
			System.out.println("Fail: Home page not found");
		}


		driver.findElement(By.xpath("//a[contains(text(),'Organizations')]")).click();

		String OrganizationHeaderLink = driver.findElement(By.xpath("//a[@class=\"hdrLink\"]")).getText();
		if (OrganizationHeaderLink.contains("Organization")) {
			System.out.println("pass: Organization page is diaplsyed");
		} else {
			System.out.println("Fail: Organization page not found");
		}


		driver.findElement(By.xpath("//img[@title='Create Organization...']")).click();

		String CreatenewOrganization = driver.findElement(By.xpath("//span[@class=\"lvtHeaderText\"]")).getText();
		if (CreatenewOrganization.contains("Creating New Organization")) {
			System.out.println("pass: CreatenewOrganization page is diaplayed");
		} else {
			System.out.println("Fail: CreatenewOrganization page not found");
		}

		Map<String, String> map = excel.fetchMultipleDataBasedOnKeyFromExcel("TestData", "Create Organization");
		String accountname = map.get("Organization Name") + javautility.generateRandomNumber(100);
		driver.findElement(By.name("accountname")).sendKeys(accountname);

		WebElement industryDropdown = driver.findElement(By.name("industry"));
		webdriver.dropdown(industryDropdown, map.get("Industry"));
		// Select industry = new Select(industryDropdown);
		// industry.selectByVisibleText("Engineering");


		driver.findElement(By.xpath("//input[@value=\"T\"]"));

		driver.findElement(By.xpath("//input[@accesskey=\"S\"]")).click();

		String OrganizationInformation = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
		if (OrganizationInformation.contains(accountname)) {
			System.out.println("pass: NewOrganizationInformation page is diaplayed");
		} else {
			System.out.println("Fail: NewOrganizationInformation page not found");
		}

		driver.findElement(By.xpath("//a[@class='hdrLink']")).click();

		WebElement newOrganizatio = driver
				.findElement(By.xpath("//table[@class=\"lvt small\"]/tbody/tr[last()]/td[3]"));
		if (newOrganizatio.getText().contains(accountname)) {
			System.out.println("Test case pass");
			excel.writeDataIntoExcel("Create Organization", "pass", AutoConstantpath.EXCEL_FILE_PATH, "TestData");

		} else {
			System.out.println("Test case Fail");
			excel.writeDataIntoExcel("Create Organization", "Fail", AutoConstantpath.EXCEL_FILE_PATH, "TestData");
		}

		WebElement administratorimage = driver.findElement(By.xpath("//img[@src=\"themes/softed/images/user.PNG\"]"));
		webdriver.mouseHoverToElement(administratorimage);
//		Actions a = new Actions(driver);
//		a.moveToElement(administratorimage).perform();
		driver.findElement(By.xpath("//a[.='Sign Out']")).click();

		excel.closeExcel();
		webdriver.closeBrowser();
		// driver.quit();

	}

}
