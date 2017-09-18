package com.avbx;

import static com.codeborne.selenide.Selenide.$;

public class YourAccPanel {

    public String getAccName() {
        return $(".user-account--user-name").getText();
    }

}
