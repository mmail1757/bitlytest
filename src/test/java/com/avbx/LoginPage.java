package com.avbx;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.byValue;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class LoginPage {

    public void setUserName(String name) {
        $$(byName("username")).filter(visible).get(0).setValue(name);
    }

    public void setEmail(String email) {
        $(byName("email")).setValue(email);
    }

    public void setPassword(String password) {
        $$(byName("password")).filter(visible).get(0).setValue(password);
    }

    public void clickSingIn() {
        $(byValue("Sign in")).click();
    }

    public void login(String name, String password) {
        setUserName(name);
        setPassword(password);
        clickSingIn();
    }

    public void checkWrongLoginMessage() {
        $(byText("Nope. Try again.")).shouldBe(visible);
    }
}
