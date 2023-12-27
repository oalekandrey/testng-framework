package utilities;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public class SeleniumUtils {

    public static void switchToWindow(String targetTitle) {
        String origin = Driver.getDriver().getWindowHandle();
        for (String handle : Driver.getDriver().getWindowHandles()) {
            Driver.getDriver().switchTo().window(handle);
            if (Driver.getDriver().getTitle().equals(targetTitle)) {
                return;
            }
        }
        Driver.getDriver().switchTo().window(origin);
    }


    public static void hover(WebElement element) {
        Actions actions = new Actions(Driver.getDriver());
        actions.moveToElement(element).perform();
    }


    public static List<String> getElementsText(List<WebElement> list) {
        List<String> elemTexts = new ArrayList<>();
        for (WebElement el : list) {
            if (!el.getText().isEmpty()) {
                elemTexts.add(el.getText().trim());
            }
        }
        return elemTexts;
    }


    public static void waitForVisibility(WebElement element, int seconds) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(seconds));
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(element)));
    }
    public static void waitForVisibility(By locator, int seconds) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(seconds));
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfElementLocated(locator)));
    }
    public static void waitForVisibilityOfAll(List<WebElement> elementList, int seconds) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(seconds));
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfAllElements(elementList)));
    }
    public static void waitForClickablility(WebElement element, int seconds) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(seconds));
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(element)));
    }
    public static void waitForClickablility(By locator, int seconds) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(seconds));
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(locator)));
    }
    public static void waitForPresenceOfElementLocated(By locator, int seconds) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(seconds));
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(locator)));
    }
    public static void waitForTitleContains(String partOfTitle, int seconds) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(seconds));
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.titleContains(partOfTitle)));
    }
    public static void waitForTitleIs(String title, int seconds) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(seconds));
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.titleIs(title)));
    }
    public static void waitForUrlContains(String partOfUrl, int seconds) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(seconds));
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.urlContains(partOfUrl)));
    }
    public static void waitFor(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static void waitForPageToLoad(int seconds) {
        ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            }
        };
        try {
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(seconds));
            wait.until(expectation);
        } catch (Throwable error) {
            System.out.println("Timed out waiting for page load");
        }
    }
    public static WebElement fluentWait(WebElement webElement, int timeOutSeconds, int pollingSeconds) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(Driver.getDriver())
                .withTimeout(Duration.ofSeconds(timeOutSeconds)).pollingEvery(Duration.ofSeconds(pollingSeconds))
                .ignoring(NoSuchElementException.class);
        WebElement element = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return webElement;
            }
        });
        return element;
    }
    public static boolean elementExists(WebElement element, int seconds) {
        try {
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(seconds));
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(element)));
            return true;
        } catch (StaleElementReferenceException | TimeoutException | NoSuchElementException e) {
            return false;
        }
    }
    public static String getScreenshotOnFailure () {
        TakesScreenshot ts = (TakesScreenshot) Driver.getDriver();
        File source = ts.getScreenshotAs(OutputType.FILE);
        String date = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        String fileName = "failed" + date + ".png";
        String target = System.getProperty("user.dir") + "/target/extentReports/" + fileName;
        File finalDestination = new File(target);
        try {
            FileUtils.copyFile(source, finalDestination);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    public static void scroll(int horizontalAxis, int verticalAxis) {
        JavascriptExecutor js = (JavascriptExecutor)Driver.getDriver();
        js.executeScript("window.scrollBy("+horizontalAxis+","+verticalAxis+")");
    }
    public static void jsClick(WebElement webelement) {
        JavascriptExecutor js = (JavascriptExecutor)Driver.getDriver();
        js.executeScript("arguments[0].click();", webelement);
    }
    public static void uploadFile(By chooseFileButton, String pathToAFileToBeUploaded ) {
        Driver.getDriver().findElement(chooseFileButton).sendKeys(pathToAFileToBeUploaded);
    }
    public static void highlightElement (WebElement element){
        JavascriptExecutor executor = (JavascriptExecutor) Driver.getDriver();

        for (int i = 0; i < 2; i++){
            try{
                if(i == 0){
                    executor.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "color: black; border:3px solid red; background:yellow");
                }else{
                    waitFor(10);
                    executor.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void waitForAlert(){
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(),Duration.ofSeconds(10));
        wait.until(ExpectedConditions.alertIsPresent());
    }
    public static void click(WebElement element){
        waitForClickablility(element,5);
        highlightElement(element);
        element.click();
    }
    public static void sendKeys(WebElement element, String input){
        waitForVisibility(element,5);
        highlightElement(element);
        element.sendKeys(input);
    }
    public static String getText(WebElement element){
        waitForVisibility(element,5);
        highlightElement(element);
        return element.getText();
    }
    public static void moveIntoView (WebElement element){
        try{
            ((JavascriptExecutor)Driver.getDriver()).executeScript("arguments[0].schrollIntoView(true);", element);
        } catch (Exception e){
            e.printStackTrace();
        }
        highlightElement(element);
    }

    public static Object[][] readFromCSV(String pathToFile){
        List<String[]> list = new ArrayList<>();

        try(Scanner scanner = new Scanner(new File(pathToFile))){
            while(scanner.hasNextLine()){
                String[] eachRow = scanner.nextLine().split(",");
                list.add(eachRow);
        }
            }catch(Exception e){
            e.printStackTrace();
                }

        int columnSize = list.get(0).length;
        int row = list.size();
        Object[][] arr = new Object[row][columnSize];

        for(int i = 0; i < list.size(); i++){
            arr[i] = list.get(i);
        }
        return arr;
    }

    public static Object[][] readFromExcelSheet_XLS(String pathToFile, String workSheetName)  {
        HSSFWorkbook workbook = new HSSFWorkbook();

        try(FileInputStream fis = new FileInputStream(pathToFile);  ) {
            workbook = new HSSFWorkbook(fis);
        }catch (IOException e){
            e.printStackTrace();
        }

        HSSFSheet workSheet = workbook.getSheet(workSheetName);
        int noOfRows = workSheet.getLastRowNum() + 1;
        int noOfColumns = workSheet.getRow(0).getLastCellNum();
        Object[][] dataTable = new String[noOfRows][noOfColumns];

        for (int i = workSheet.getFirstRowNum(); i < workSheet.getLastRowNum() + 1; i++) {
            Row row = workSheet.getRow(i);
            for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
                Cell cell = row.getCell(j);
                dataTable[i][j] = cell.getStringCellValue();
            }
        }

        try {
            workbook.close();
        }catch (IOException e){
            e.printStackTrace();
        }

        return dataTable;
    }


    public static Object[][] readFromExcelSheet_XLSX(String pathToFile, String workSheetName) {
        XSSFWorkbook workbook = new XSSFWorkbook();

        try(FileInputStream fis = new FileInputStream(pathToFile)){
            workbook = new XSSFWorkbook(fis);
        }catch (IOException e){
            e.printStackTrace();
        }

        XSSFSheet workSheet = workbook.getSheet(workSheetName);
        int noOfRows = workSheet.getLastRowNum() + 1;
        int noOfColumns = workSheet.getRow(0).getLastCellNum();
        Object[][] dataTable = new String[noOfRows][noOfColumns];

        for (int i = workSheet.getFirstRowNum(); i < workSheet.getLastRowNum() + 1; i++) {
            Row row = workSheet.getRow(i);
            for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
                Cell cell = row.getCell(j);
                dataTable[i][j] = cell.getStringCellValue();
            }
        }

        try {
            workbook.close();
        }catch (IOException e){
            e.printStackTrace();
        }

        return dataTable;
    }




}
