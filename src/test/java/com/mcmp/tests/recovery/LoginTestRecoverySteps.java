package com.mcmp.tests.recovery;

import com.autoui.fwk.recovery.WebRecovery;
import com.autoui.fwk.utils.WebUtils;

public class LoginTestRecoverySteps extends WebRecovery {

    @Override
    public void recover() {
        driver.get("");
        WebUtils.handleCertificateError(driver);
    }
}
