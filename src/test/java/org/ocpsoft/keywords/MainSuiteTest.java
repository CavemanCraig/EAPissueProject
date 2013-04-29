package org.ocpsoft.keywords;

import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.forge.parser.java.JavaClass;
import org.jboss.forge.parser.java.Member;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ocpsoft.utils.Utility;
import com.thoughtworks.selenium.DefaultSelenium;

@RunWith(Arquillian.class)
public class MainSuiteTest {//Begin Class
	
   @Deployment(testable = false) // testable = false to run as a client
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "EAPissueProject.war")
						.addPackage("com.ocpsoft.utils")
						.addAsResource("META-INF/persistence.xml")
						.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}

@Drone
DefaultSelenium browser;

@ArquillianResource
URL deploymentURL;

	@Test
	public void testIssue() throws InterruptedException {//Begin Test Case
		String testSuiteName = "MySuiteTest";
		String testCaseName = "testCase1";
		Member<JavaClass, ?> member = null;
		member = Utility.getMemberFromTestCaseName(testCaseName, testSuiteName);
		String[] steps = Utility.getStepsFromMethod(member);
		if(steps == null){
			Assert.assertTrue("Could not get any steps for test case to validate.", false);
		}
		Assert.assertTrue("We got the steps, count: " + steps.length, true);
	}//End Test Case

	
}//End Class