package com.revof11.jgitflowmaven;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.URL;
import java.util.Iterator;
import java.util.stream.Collectors;

/**
 * Tests our object that says "Hello" to someone.
 */
@Test (
  description = "Tests our object that says \"Hello\" to someone."
)
public class HelloTest {

  /**
   * Runs the actual test that we want for simulation.
   * @param name the name of the person we want to say "Hello" to
   */
  @Test (
    dataProvider = "getNames",
    description = "Runs the actual test that we want for simulation."
  )
  public void testHello(final String name) {
    String result = new Hello().getHelloMessage(name);
    Assert.assertEquals(result, String.format("Hello, %s!", name), "Invalid hello string returned.");
  }

  /**
   * Retrieves a list of names to say "Hello" to.
   * @return a list of names to say "Hello" to
   * @throws Exception if anything goes horribly awry during the run
   */
  @DataProvider (name = "getNames")
  public static Iterator<Object[]> getNames() throws Exception {
    URL nameFile = HelloTest.class.getResource("/names.txt");
    Assert.assertNotNull(nameFile, "Unable to locate resource name file.");
    return IOUtils.readLines(nameFile.openStream(), "UTF-8")
      .parallelStream()
      .filter(StringUtils::isNotBlank)
      .map(next -> new Object[]{next})
      .collect(Collectors.toList())
      .iterator();
  }
}
