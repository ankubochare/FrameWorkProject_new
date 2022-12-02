package pomImplementation;

import java.io.IOException;
import java.util.Map;

import org.openqa.selenium.WebDriver;

import genericLibraries.AutoConstantpath;
import genericLibraries.ExcellFileUtility;
import genericLibraries.JavaUtility;
import genericLibraries.PropertyFileUtility;
import genericLibraries.WebDriverUtility;
import pomPages.CreateNewOrganizationPage;
import pomPages.Loginpage;
import pomPages.NewOrganizationInfoPage;
import pomPages.OrganizationPage;
import pomPages.homePage;

public class CreateNewOrganization {
	


	public static void main(String[] args) throws IOException {
		WebDriverUtility webdriver = new WebDriverUtility();
		JavaUtility javautility = new JavaUtility();

		PropertyFileUtility property = new PropertyFileUtility();
		property.propertyFileInitialization(AutoConstantpath.PROPERTY_FILE_PATH);
		ExcellFileUtility excel = new ExcellFileUtility();
		excel.excelFileInitialization(AutoConstantpath.EXCEL_FILE_PATH);

		// Random random = new Random();
		// int randomNum = random.nextInt(100);

		String url = property.getDataFromPropertyFile("url");
		String username = property.getDataFromPropertyFile("login");
		String password = property.getDataFromPropertyFile("password");
		String time = property.getDataFromPropertyFile("timeouts");
		WebDriver driver = webdriver.openBrowserAndApplication(url, Long.parseLong(time));

		Loginpage loginpage = new Loginpage(driver);
		homePage home = new homePage(driver);
		OrganizationPage organizationpage = new OrganizationPage(driver);
		CreateNewOrganizationPage createneworg = new CreateNewOrganizationPage(driver);
		NewOrganizationInfoPage neworginfo=new NewOrganizationInfoPage(driver);

		// WebDriverManager.chromedriver().setup();
		// WebDriver driver = new ChromeDriver();
		// driver.manage().window().maximize();
		// driver.get("http://localhost:8888/index.php?action=Login&module=Users");
		// driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		if (loginpage.getLogo().isDisplayed()) {
			System.out.println("pass: loginpage displayed");
		} else {
			System.out.println("Fail: Loginpage not found");
		}


		loginpage.loginToApplication(username, password);
//		driver.findElement(By.xpath("//input[@name=\"user_name\"]")).sendKeys("username");
//		driver.findElement(By.xpath("//input[@name=\"user_password\"]")).sendKeys("password");
//		driver.findElement(By.id("submitButton")).click();

		// String HeaderLink =
		// driver.findElement(By.xpath("//a[@class=\"hdrLink\"]")).getText();
		if (home.getpageHeaderText().contains("Home")) {
			System.out.println("pass: Home page is diaplsyed");
		} else {
			System.out.println("Fail: Home page not found");
		}


		home.ClickOrganizationTab();
		// String OrganizationHeaderLink =
		// driver.findElement(By.xpath("//a[@class=\"hdrLink\"]")).getText();
		if (organizationpage.getpageHeader().contains("Organization")) {
			System.out.println("pass: Organization page is diaplsyed");
		} else {
			System.out.println("Fail: Organization page not found");
		}

		organizationpage.clickPlusImage();
		// driver.findElement(By.xpath("//img[@title='Create
		// Organization...']")).click();

		// String CreatenewOrganization =
		// driver.findElement(By.xpath("//span[@class=\"lvtHeaderText\"]")).getText();
		if (organizationpage.getNewOrganization().contains("Creating New Organization")) {
			System.out.println("pass: CreatenewOrganization page is diaplayed");
		} else {
			System.out.println("Fail: CreatenewOrganization page not found");
		}

		Map<String, String> map = excel.fetchMultipleDataBasedOnKeyFromExcel("TestData", "Create Organization");
		String accountname = map.get("Organization Name") + javautility.generateRandomNumber(100);
		// driver.findElement(By.name("accountname")).sendKeys(accountname);
		createneworg.setOrganizationName(accountname);
		createneworg.selectIndustryDropdown(webdriver, map.get("Industry"));
		createneworg.clickGroupradiobutton();
//		WebElement industryDropdown = driver.findElement(By.name("industry"));
//		webdriver.dropdown(industryDropdown, map.get("Industry"));
		// Select industry = new Select(industryDropdown);
		// industry.selectByVisibleText("Engineering");


		// driver.findElement(By.xpath("//input[@value=\"T\"]"));
		createneworg.clickSavebutton();
		// driver.findElement(By.xpath("//input[@accesskey=\"S\"]")).click();

		// String OrganizationInformation =
		// driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
		if (neworginfo.getPageHeader().contains(accountname)) {
			System.out.println("pass: NewOrganizationInformation created successfully");
		} else {
			System.out.println("Fail: NewOrganizationInformation  not created");
		}
		neworginfo.clickOrganization();
		// driver.findElement(By.xpath("//a[@class='hdrLink']")).click();

		// WebElement newOrganizatio = driver.findElement(By.xpath("//table[@class=\"lvt
		// small\"]/tbody/tr[last()]/td[3]"));
		if (organizationpage.getNewOrganizationinfo().contains(accountname)) {
			System.out.println("Test case pass");
			excel.writeDataIntoExcel("TestData", "pass", AutoConstantpath.EXCEL_FILE_PATH, "Create Organization");

		} else {
			System.out.println("Test case Fail");
			excel.writeDataIntoExcel("TestData", "Fail", AutoConstantpath.EXCEL_FILE_PATH, "Create Organization");
		}
		home.signOutTheApplication(webdriver);

//		WebElement administratorimage = driver.findElement(By.xpath("//img[@src=\"themes/softed/images/user.PNG\"]"));
//		webdriver.mouseHoverToElement(administratorimage);
//		Actions a = new Actions(driver);
//		a.moveToElement(administratorimage).perform();
		// driver.findElement(By.xpath("//a[.='Sign Out']")).click();

		excel.closeExcel();
		webdriver.closeBrowser();
		// driver.quit();

	}

}
