package com.shivay.medref.util;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.apache.commons.text.RandomStringGenerator;

public class Utility {
	
	
	public static String generateRandomPassword() {
		int len = 8;
		String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghi"
          +"jklmnopqrstuvwxyz!@#$%&";
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(chars.charAt(rnd.nextInt(chars.length())));
		return sb.toString();
	}
	
	public static String generateRandomSpecialCharacters(int length) {
	    RandomStringGenerator pwdGenerator = new RandomStringGenerator.Builder().withinRange(33, 45)
	        .build();
	    return pwdGenerator.generate(length);
	}
	
	public static String generateCommonTextPassword() {
	    String pwString = generateRandomSpecialCharacters(2).concat(generateRandomNumbers(2))
	      .concat(generateRandomAlphabet(2, true))
	      .concat(generateRandomAlphabet(2, false));
	    List<Character> pwChars = pwString.chars()
	      .mapToObj(data -> (char) data)
	      .collect(Collectors.toList());
	    Collections.shuffle(pwChars);
	    String password = pwChars.stream()
	      .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
	      .toString();
	    return password;
	}
	
	
	public static String generateRandomNumbers(int n) {
		
		return new Random().ints(n).toString();
	}
	public static String generateRandomAlphabet(int n,boolean b) {
		 int leftLimit = 97; // letter 'a'
		    int rightLimit = 122; // letter 'z'
		    int targetStringLength = 10;
		    Random random = new Random();

		    String generatedString = random.ints(leftLimit, rightLimit + 1)
		      .limit(targetStringLength)
		      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
		      .toString();

		   if(b)
			   return generatedString.toUpperCase();
		    
		   return generatedString.toLowerCase();
	
	}

	
}
