package hardCodedTests;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CreateNewContact {
	public static void main(String[] args) {

		Random random = new Random();
		int randomNumber = random.nextInt(100);

		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("http://localhost:8888/index.php?action=Login&module=Users");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

		if (driver.getTitle().contains("vtiger")) {
			System.out.println("Pass: Vtiger login page is diplayed");
		} else {
			System.out.println("Fail: Vtiger login page is not displayed");
		}

		driver.findElement(By.xpath("//input[@name=\"user_name\"]")).sendKeys("admin");
		driver.findElement(By.xpath("//input[@name=\"user_password\"]")).sendKeys("admin");
		driver.findElement(By.id("submitButton")).click();

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

		WebElement salutation = driver.findElement(By.name("salutationtype"));
		Select salu = new Select(salutation);
		salu.selectByVisibleText("Ms.");

		driver.findElement(By.name("firstname")).sendKeys("Ankita");
		String contactName = "Sri" + randomNumber;
		driver.findElement(By.name("lastname")).sendKeys(contactName);
		driver.findElement(By.xpath("(//img[@alt='Select'])[1]")).click();
		String parentWindow = driver.getWindowHandle();
		Set<String> windows = driver.getWindowHandles();
		for (String window : windows) {
			String expectedTitle = "Account&action";
			driver.switchTo().window(window);
			String actualTitle = driver.getTitle();
			if (actualTitle.contains(expectedTitle)) {
				break;
			}
		}
		String requiredOrganizationName = "vipro";
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
		Actions a = new Actions(driver);
		a.moveToElement(adminImage).perform();
		driver.findElement(By.xpath("//a[.='Sign Out']")).click();
		driver.quit();
	}

}
