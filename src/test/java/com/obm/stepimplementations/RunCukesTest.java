package com.obm.stepimplementations;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = {"src/test/resources/features/test"}, format = {"html:target/json_report/cucumber.html"})
public class RunCukesTest {
	

}