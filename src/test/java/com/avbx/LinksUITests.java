package com.avbx;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Configuration.startMaximized;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.junit.Assert.assertEquals;

public class LinksUITests {

    String valid_link1 = "http://gmail.com";
    String valid_link2 = "http://yahoo.com";
    String valid_link3 = "http://bing.com";
    List<String> links = Arrays.asList(valid_link1, valid_link2, valid_link1);
    String unvalid_link = "http://link";

    String name = "usern1";
    String password = "123456a";
    String wrongPassword = "123456aa";

    LoginPage lp = page(LoginPage.class);
    MainPage mp = page(MainPage.class);





    @BeforeClass
    public static void openInbox() {
        baseUrl = "https://bitly.com/a/sign_in";
        startMaximized = false;
        getWebDriver().manage().deleteAllCookies();

        open("/");
    }


    @Test
    public void positiveScenarioAddThreeLinks() {

        lp.login(name, password);
        mp.isUserOn(name);

        CreateBitLinkPanel blp = page(CreateBitLinkPanel.class);
        createLink(blp, mp, valid_link1);
        createLink(blp, mp, valid_link2);
        createLink(blp, mp, valid_link3);
        mp.getMainLinks().stream().forEach(i -> {
            i.click();
            links.contains(mp.getMainLink().getText());
        });
    }

    @Test
    public void positiveCheckAccName() {
        lp.login(name, password);
        mp.clickHambIcon();
        YourAccPanel panel = page(YourAccPanel.class);
        assertEquals(panel.getAccName(), name);
    }

    @Test
    public void negativeScenarioWrongLink() {

        lp.login(name, password);
        mp.isUserOn(name);
        mp.createBitLink();

        CreateBitLinkPanel blp = page(CreateBitLinkPanel.class);
        blp.setBitLink(unvalid_link);
        blp.createBitLink();
        mp.checkErrorMessage();
    }

    @Test
    public void negativeScenarioWrongLogin() {
        lp.login(name, wrongPassword);
        lp.checkWrongLoginMessage();
    }

    private void createLink(CreateBitLinkPanel panel, MainPage mainPage, String link) {
        mainPage.createBitLink();
        panel.setBitLink(link);
        panel.createBitLink();
        panel.closePanel();
    }

}
