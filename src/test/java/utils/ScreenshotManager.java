package utils;

import com.microsoft.playwright.Page;
import io.qameta.allure.Allure;

import java.io.ByteArrayInputStream;

public class ScreenshotManager {

    public static void takeScreenshot(String stepName) {
        // Implement logic to take a screenshot and save it with the test name
        // This is a placeholder implementation and should be replaced with actual code to capture screenshots
        System.out.println("Taking screenshot for test: " + stepName);
       byte[] bytes= PlaywrightFactory.getPage().screenshot(new Page.ScreenshotOptions().setFullPage(true));
        Allure.addAttachment(stepName,new ByteArrayInputStream(bytes));

    }
}
