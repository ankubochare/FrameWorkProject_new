package pomPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import genericLibraries.WebDriverUtility;

public class homePage {

	@FindBy(xpath = "//a[@class=\"hdrLink\"]")
	private WebElement PageHeaderText;

	@FindBy(xpath = "//a[contains(text(),'Organizations')]")
	private WebElement OrganizationTab;
	@FindBy(xpath = "//a[contains(text(),'Leads')]")
	private WebElement LeadsTab;
	@FindBy(xpath = "//a[contains(text(),'Contacts')]")
	private WebElement ContactTabs;
	@FindBy(xpath = "//img[@src=\\\"themes/softed/images/user.PNG\\\"]")
	private WebElement administratorImage;
	@FindBy(xpath = "//a[.='Sign Out']")
	private WebElement signout;

	public homePage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	public String getpageHeaderText() {
		return PageHeaderText.getText();
	}

	public void ClickOrganizationTab() {
		OrganizationTab.click();
	}

	public void ClickLeadsTab() {
		LeadsTab.click();
	}

	public void ClickContactsTab() {
		ContactTabs.click();
	}

	public void signOutTheApplication(WebDriverUtility webdriver) {
		webdriver.mouseHoverToElement(administratorImage);
		signout.click();
	}
}
