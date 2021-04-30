import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class LoginFBShopee {

    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver","C:/Windows/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.shopee.co.id/");
        String parentWindow = driver.getWindowHandle();
        System.out.println(parentWindow);

        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.findElement(By.className("shopee-popup__close-btn")).click();
        driver.findElement(By.linkText("Log In")).click();

        Thread.sleep(2000);
        // driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.findElement(By.className("_1iDCwS")).click();

        Assert.assertTrue(waitForNewWindow(driver,10), "New window is not opened");
        Set<String> allWindow = driver.getWindowHandles();
        System.out.println(allWindow);

        Iterator<String> itr = allWindow.iterator();
        while(itr.hasNext()) {
            String childWindow = itr.next();
            // Compare whether the main windows is not equal to child window. If not equal, we will close.
            if(!parentWindow.equals(childWindow)){
                driver.switchTo().window(childWindow);
                System.out.println(driver.switchTo().window(childWindow).getTitle());
                driver.findElement(By.name("email")).sendKeys("your@email.com");
                driver.findElement(By.name("pass")).sendKeys("your_password");
                Thread.sleep(2000);
                // driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
                driver.findElement(By.name("login")).click();
                // driver.close();
            }
        }

        // This is to switch to the main window
        driver.switchTo().window(parentWindow);
        System.out.println(driver.switchTo().window(parentWindow));
        Thread.sleep(2000);
        driver.findElement(By.name("loginKey")).sendKeys("Sekarang kembali");
        Thread.sleep(3000);
        driver.close();
    }

    public static boolean waitForNewWindow(WebDriver driver, int timeout){
        boolean flag = false;
        int counter = 0;
        while(!flag){
            try {
                Set<String> winId = driver.getWindowHandles();
                if(winId.size() > 1){
                    flag = true;
                    return flag;
                }
                Thread.sleep(1000);
                counter++;
                if(counter > timeout){
                    return flag;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return false;
            }
        }
        return flag;
    }
}