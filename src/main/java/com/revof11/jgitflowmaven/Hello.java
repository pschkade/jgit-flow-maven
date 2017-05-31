package com.revof11.jgitflowmaven;

/**
 * Simple class that just says "Hello" to someone.
 */
public class Hello {

  /**
   * Retrieves a "Hello" message to someone.
   * @param person the person we want to say "Hello" to
   * @return a "Hello" message to the person provided
   */
  public String getHelloMessage(String person) {
    return String.format("Hello, %s!", person);
  }
}
