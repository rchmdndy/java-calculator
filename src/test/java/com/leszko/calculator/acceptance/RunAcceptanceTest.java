package com.leszko.calculator.acceptance;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/acceptance",
    glue = "com.leszko.calculator.acceptance",
    plugin = {"pretty", "html:build/reports/acceptance.html"}
)
public class RunAcceptanceTest {
}
