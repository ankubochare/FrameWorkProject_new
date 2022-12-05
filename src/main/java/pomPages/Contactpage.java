package pomPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Contactpage {

	@FindBy(xpath = "//img[@title='Create Contact...']")
	private WebElement plusbutton;

	@FindBy(xpath = "//a[@href='index.php?action=ListView&module=Contacts&parenttab=Marketing']")
	private WebElement pageHeader;

	@FindBy(xpath = "//table[@class='lvt small']/descendant::tr[last()]/td[4]/a")
	private WebElement lastcontactList;

	public Contactpage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	public void clickplusButton() {
		plusbutton.click();
	}

	public String getHeader() {
		return pageHeader.getText();
	}

	public String getLastContatName() {
		return lastcontactList.getText();
	}

}
