package com.avbx;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.util.List;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class MainPage {

    public void isUserOn(String user) {

        $(".button--GHOST-PRIMARY").should(Condition.exist);

    }

    public void createBitLink() {
        $(byText("CREATE BITLINK")).click();
    }

    public void checkErrorMessage() {
        $(byText("Not a valid URL")).shouldBe(Condition.visible);
    }

    public SelenideElement getMainLink() {
        return $(".item-detail--url");
    }

    public List<SelenideElement> getMainLinks() {
        return $$(".bitlink-item--title");
    }

    public void clickHambIcon() {
        $(".hamburger-icon").click();
    }

}
