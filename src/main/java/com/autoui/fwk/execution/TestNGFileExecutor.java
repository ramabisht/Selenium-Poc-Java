package com.autoui.fwk.execution;

import com.autoui.fwk.reporting.Logger;

/**
 * Executor to run tests from the command line using testNG XML file
 * <ul>
 * <li>Comma seperated TestNG.xml file paths must be specified as system
 * property with key <b>autoui.testsuites</b></li>
 * <li>New TestNG Listeners if any can be specified as system property with key
 * <b>autoui.listeners</b></li>
 * <li>Default output directory is .\reports. Change should be specified as
 * system property with key <b>autoui.reportdir</b></li>
 * </ul>
 * <p>
 * Eg: Consider we have to run testNG.xml file
 *
 * <pre>
 * java -autoui.testsuites=testNG.xml
 * 			-cp fat-h5-system-test-0.0.3-SNAPSHOT.jar com.autoui.fwk.execution.TestNGFileExecutor
 * </pre>
 *
 * @author rama.bisht
 */
public class TestNGFileExecutor {
    private static final Logger _logger = Logger.getLogger(TestNGFileExecutor.class);

    public static void main(String[] args) {
        _logger.info("Constructing test suite");
        Executor mapper = new Executor();
        mapper.generateTestNGFileSuites();
        mapper.runTestNGFileSuites();
    }
}
