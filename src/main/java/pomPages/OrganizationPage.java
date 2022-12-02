package pomPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OrganizationPage {

	@FindBy(xpath = "//a[@class='hdrLink']")
	private WebElement PageHeadr;
	@FindBy(xpath = "//img[@title='Create Organization...']")
	private WebElement PluseImage;
	@FindBy(xpath = "//span[@class=\"lvtHeaderText\"]")
	private WebElement neworganization;
	@FindBy(xpath = "//table[@class='lvt small']/descendant::tr[last()]/td[3]/a")
	private WebElement newOrganizationinfo;

	public OrganizationPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	public String getpageHeader() {
		return PageHeadr.getText();
	}

	public void clickPlusImage() {
		PluseImage.click();
	}

	public String getNewOrganization() {
		return neworganization.getText();
	}

	public String getNewOrganizationinfo() {
		return newOrganizationinfo.getText();
	}
}
