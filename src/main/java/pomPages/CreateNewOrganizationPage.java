package pomPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import genericLibraries.WebDriverUtility;

public class CreateNewOrganizationPage {
	@FindBy(xpath = "//span[@class=\"lvtHeaderText\"]")
	private WebElement pageHeaderText;

	@FindBy(name = "accountname")
	private WebElement organizationNameTextField;

	@FindBy(name = "industry")
	private WebElement industryDropdown;

	@FindBy(xpath = "//input[@value=\"T\"]")
	private WebElement groupradiobutton;

	@FindBy(xpath = "//input[@accesskey=\"S\"]")
	private WebElement savebutton;

	public CreateNewOrganizationPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	public void setOrganizationName(String organization) {
		organizationNameTextField.sendKeys(organization);
	}

	public void clickGroupradiobutton() {
		groupradiobutton.click();

	}

	public void selectIndustryDropdown(WebDriverUtility webdriver, String industry) {
		webdriver.dropDown(industryDropdown, industry);
	}

	public WebElement getPageHeaderText() {
		return pageHeaderText;
	}

	public WebElement getOrganizationNameTextField() {
		return organizationNameTextField;
	}



	public void clickSavebutton() {
		savebutton.click();
	}

}
