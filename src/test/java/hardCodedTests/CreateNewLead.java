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

public class CreateNewLead {

	public static void main(String[] args) {

		Random random = new Random();
		int randomNumber = random.nextInt(100);

		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("http://localhost:8888/index.php?action=Login&module=Users");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		if (driver.getTitle().contains("vtiger")) {
			System.out.println("Pass: Logo is displayed");

		} else {
			System.out.println("Fail: Logo not displayed");
		}

		driver.findElement(By.name("user_name")).sendKeys("admin");
		driver.findElement(By.name("user_password")).sendKeys("admin");

		driver.findElement(By.xpath("//input[@type='submit']")).click();

		if (driver.getTitle().contains("Administrator")) {
			System.out.println("pass: Login successful");
		} else {
			System.out.println("Fail: Login not successful");
		}

		driver.findElement(By.xpath("//a[.='Leads']")).click();
		driver.findElement(By.xpath("//img[@title='Create Lead...']")).click();
		WebElement createLeadHeader = driver.findElement(By.xpath("//span[@class='lvtHeaderText']"));
		if (createLeadHeader.getText().contains("Creating New Lead")) {
			System.out.println("Pass: create new Lead page display");
		} else {
			System.out.println("Fail: Create new Lead page not display");
		}

		WebElement salutation = driver.findElement(By.name("salutationtype"));
		Select sal = new Select(salutation);
		sal.selectByVisibleText("Mr.");

		String LeadName = "adb" + random;
		driver.findElement(By.name("lastname")).sendKeys(LeadName);
		driver.findElement(By.name("company")).sendKeys("info");
		driver.findElement(By.xpath("//input[@type='submit']")).click();

		WebElement leadinfoHeader = driver.findElement(By.xpath("//span[@class='dvHeaderText']"));
		if (leadinfoHeader.getText().contains(LeadName)) {
			System.out.println("Pass: Lead created successfully");
		} else {
			System.out.println("Lead not created  successfully");
		}

		driver.findElement(By.xpath("//input[@value='Duplicate']")).click();
		WebElement duplicate = driver.findElement(By.xpath("//span[@class='lvtHeaderText']"));
		if (duplicate.getText().contains(LeadName)) {
			System.out.println("Pass: Duplicate page is display");
		} else {
			System.out.println("Pass: Duplicate page not display");
		}
		String newLastName = "cde" + randomNumber;
		driver.findElement(By.name("lastname")).clear();
		driver.findElement(By.name("lastname")).sendKeys(newLastName);
		driver.findElement(By.xpath(" //input[contains(@value,'Save')]")).click();
		WebElement leadInfoPage = driver.findElement(By.xpath("//span[@class='dvHeaderText']"));
		if (leadInfoPage.getText().contains(newLastName)) {
			System.out.println("Pass : New lead created successfully");
		} else {
			System.out.println("Fail : Lead is not created");
		}

		driver.findElement(By.xpath("//a[@href='index.php?action=ListView&module=Leads&parenttab=Marketing']")).click();
		String name = driver
				.findElement(By.xpath("//table[@class='lvtBg']/descendant::div/table/tbody/tr[last()]/td[3]/a"))
				.getText();
		if (name.equals(newLastName)) {
			System.out.println("Test Case passed");
		} else {
			System.out.println("Test Case Failed");
		}

		WebElement adminImage = driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"));
		Actions a = new Actions(driver);
		a.moveToElement(adminImage).perform();
		driver.findElement(By.xpath("//a[.='Sign Out']")).click();
		driver.quit();
	}

}
