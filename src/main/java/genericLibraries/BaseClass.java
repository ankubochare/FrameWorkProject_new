package genericLibraries;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import pomPages.Contactpage;
import pomPages.CreateNewLeadPage;
import pomPages.CreateNewOrganizationPage;
import pomPages.CreatenewContactpage;
import pomPages.DuplicatingLeadPage;
import pomPages.LeadsPage;
import pomPages.Loginpage;
import pomPages.NewContactinfopage;
import pomPages.NewLeadInfoPage;
import pomPages.NewOrganizationInfoPage;
import pomPages.OrganizationPage;
import pomPages.homePage;

public class BaseClass {
	
	protected ExcellFileUtility excel;
	protected PropertyFileUtility property;
	protected JavaUtility java;
	protected WebDriverUtility webdriver;
	protected WebDriver driver;
	protected Loginpage loginpage;
	protected homePage homepage;
	protected OrganizationPage organizationpage;
	protected CreateNewOrganizationPage createneworganizationpage;
	protected NewOrganizationInfoPage neworganizationinfopage;
	protected Contactpage contactpage;
	protected CreatenewContactpage createnewcontactpage;
	protected NewContactinfopage newcontactinfopage;
	protected LeadsPage leadsPage;
	protected CreateNewLeadPage createNewLead;
	protected NewLeadInfoPage newLead;
	protected DuplicatingLeadPage duplicatingPage;

	//@BeforeSutie
	
	@BeforeTest
	public void testSetup()
	{
		 excel=new ExcellFileUtility();
		 property=new PropertyFileUtility();
		 java=new JavaUtility();
	 webdriver=new WebDriverUtility();
	 
	 property.propertyFileInitialization(AutoConstantpath.PROPERTY_FILE_PATH);
	 excel.excelFileInitialization(AutoConstantpath.EXCEL_FILE_PATH);
	}
	
	
	@BeforeClass
	
	public void classSetup() throws IOException
	{
		String url=property.getDataFromPropertyFile("url");
		String time=property.getDataFromPropertyFile("timeouts");
		long timeouts=Long.parseLong(time);
		driver =webdriver.openBrowserAndApplication(url, timeouts);
		 loginpage=new Loginpage(driver);
			Assert.assertTrue(loginpage.getLogo().isDisplayed());

		 homepage=new homePage(driver);
		 organizationpage=new OrganizationPage(driver);
		 createneworganizationpage=new  CreateNewOrganizationPage(driver);
		 neworganizationinfopage =new NewOrganizationInfoPage(driver);
			contactpage = new Contactpage(driver);
			createnewcontactpage = new CreatenewContactpage(driver);
			newcontactinfopage = new NewContactinfopage(driver);
			leadsPage = new LeadsPage(driver);
			createNewLead = new CreateNewLeadPage(driver);
			newLead = new NewLeadInfoPage(driver);
			duplicatingPage = new DuplicatingLeadPage(driver);
	}
	
	@BeforeMethod
	public void methodSetup() throws IOException
	{
		String username=property.getDataFromPropertyFile("username");
		String password=property.getDataFromPropertyFile("password");
		loginpage.loginToApplication(username, password);
		Assert.assertTrue(homepage.getpageHeaderText().contains("Home"));

	}

	@AfterMethod
	public void methodTearDown() {
		homepage.signOutTheApplication(webdriver);
	}

	@AfterClass
	public void classTearDown() {
		webdriver.closeBrowser();
	}

	@AfterTest
	public void testTearDown() {
		excel.closeExcel();
	}
	//@AfterSutie

}
