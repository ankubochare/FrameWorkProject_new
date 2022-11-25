package pomPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OrganizationPage {

	@FindBy(xpath = "\"//a[@class=\\\"hdrLink\\\"]\"")
	private WebElement PageHeadrText;
	@FindBy(xpath = "//img[@title='Create Organization...']")
	private WebElement PluseImage;
	@FindBy(xpath = "//table[@class=\\\"lvt small\\\"]/tbody/tr[last()]/td[3]")
	private WebElement neworganization;

	public OrganizationPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	public String getpageHeaderText() {
		return PageHeadrText.getText();
	}

	public String clickPlusImage() {
		return PluseImage.getText();
	}

	public String getNewOrganization() {
		return neworganization.getText();
	}
}
