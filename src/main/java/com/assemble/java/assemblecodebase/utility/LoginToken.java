package com.assemble.java.assemblecodebase.utility;

public class LoginToken {

  // Produces a random string for use with login tokens.
  public static String getNewToken() {

    // Create a string of characters to choose from
    String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890!@#$%^&*()-=_+[]{}";

    // Create a String Builder object of the passed in length
    StringBuilder randomString = new StringBuilder(64);

    // Iterating through a loop "length" amount of times,
    // use Math.random() to pick a character from the above string, and
    // append it to the random string
    for (int i = 0; i < 64; i++) {
      randomString.append(characters.charAt((int) (Math.random() * characters.length())));
    }

    // Return the random string.z
    return randomString.toString();
  }
}
