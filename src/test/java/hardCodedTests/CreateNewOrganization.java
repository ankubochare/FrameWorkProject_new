package hardCodedTests;

import java.time.Duration;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CreateNewOrganization {
	
	public static void main(String[] args) {


		Random random = new Random();
		int randomNum = random.nextInt(100);

		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("http://localhost:8888/index.php?action=Login&module=Users");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		WebElement logo = driver.findElement(By.xpath("//img[@alt=\"logo\"]"));
		if (logo.isDisplayed()) {
			System.out.println("pass: loginpage displayed");
		} else {
			System.out.println("Fail: Loginpage not found");
		}


		driver.findElement(By.xpath("//input[@name=\"user_name\"]")).sendKeys("admin");
		driver.findElement(By.xpath("//input[@name=\"user_password\"]")).sendKeys("admin");
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

		String accountname = "coral" + randomNum;
		driver.findElement(By.name("accountname")).sendKeys(accountname);

		WebElement industryDropdown = driver.findElement(By.name("industry"));
		Select industry = new Select(industryDropdown);
		industry.selectByVisibleText("Engineering");

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
		} else {
			System.out.println("Test case Fail");
		}

		WebElement administratorimage = driver.findElement(By.xpath("//img[@src=\"themes/softed/images/user.PNG\"]"));
		Actions a = new Actions(driver);
		a.moveToElement(administratorimage).perform();
		driver.findElement(By.xpath("//a[.='Sign Out']")).click();

		driver.quit();
	}

}
