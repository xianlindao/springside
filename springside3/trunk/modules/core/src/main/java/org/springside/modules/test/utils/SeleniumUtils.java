/*
 * $HeadURL$
 * $Id$
 * Copyright (c) 2010 by Ericsson, all rights reserved.
 */

package org.springside.modules.test.utils;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.util.Assert;

/**
 *
 * @author calvin
 */
public class SeleniumUtils {

	public static final String HTMLUNIT = "htmlunit";

	public static final String FIREFOX = "firefox";

	public static final String IE = "ie";

	public static final String REMOTE = "remote";

	public static WebDriver buildDriver(String driverName) throws MalformedURLException, Exception {
		WebDriver driver = null;

		if (HTMLUNIT.equals(driverName)) {
			driver = new HtmlUnitDriver();
			((HtmlUnitDriver) driver).setJavascriptEnabled(true);
		}

		if (FIREFOX.equals(driverName)) {
			driver = new FirefoxDriver();
		}

		if (IE.equals(driverName)) {
			driver = new InternetExplorerDriver();
		}

		if (driverName.startsWith(REMOTE)) {
			String[] params = driverName.split(":");
			Assert.isTrue(params.length == 3,
					"Remote driver is not right, accept format is \"remote:local:firefox\", but the input is\""
							+ driverName + "\"");

			String remoteAddress = params[1];
			String driverType = params[2];
			DesiredCapabilities cap = null;
			if (FIREFOX.equals(driverType)) {
				cap = DesiredCapabilities.firefox();
			}

			if (IE.equals(driverType)) {
				cap = DesiredCapabilities.internetExplorer();
			}

			driver = new RemoteWebDriver(new URL("http://" + remoteAddress + ":3000/wd"), cap);
		}

		Assert.notNull(driver, "No driver could be found by name:" + driverName);

		return driver;
	}

}