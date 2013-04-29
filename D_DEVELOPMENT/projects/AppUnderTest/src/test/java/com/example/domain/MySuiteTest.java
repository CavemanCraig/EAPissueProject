package com.example.domain;

import java.io.File;
import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.thoughtworks.selenium.DefaultSelenium;

@RunWith(Arquillian.class)
public class MySuiteTest {

	private static final String WEBAPP_SRC = "src/main/webapp";
	@Drone
	DefaultSelenium browser;
	@ArquillianResource
	URL deploymentURL;

	@Deployment(testable = false)
	static public WebArchive createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "FBTutorialDemo.war")
				.addAsResource("META-INF/persistence.xml")
				.addClass(Helper.class)
				.addAsWebResource(new File(WEBAPP_SRC, "index.html"))
				.addAsWebResource(new File(WEBAPP_SRC, "index2.html"))
				.addAsWebResource(new File(WEBAPP_SRC, "myInfo.html"))
				.addAsWebResource(new File(WEBAPP_SRC, "friendsInfo.html"))
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@Test
	public void testName() throws InterruptedException {
		System.out.println("Step 1");
		System.out.println("Step 2");
	}
}