package com.avbx;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CreateBitLinkPanel {

    public void setBitLink(String bitLink) {
        $(".text-field--textarea").setValue(bitLink);
    }

    public void createBitLink() {
        $(byText("CREATE")).click();
    }

    public void closePanel() {
        $$(".close-icon").filter(visible).get(0).click();
    }

    public void setLinkTitle(String title) {
        $(".input-field--input").setValue(title);
    }

    public void saveBitLink() {
        $(byText("SAVE")).click();
    }

}
