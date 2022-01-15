package com.auth.models;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum EncryptionAlgorithm {
	BCRYPT, SCRYPT;
	
	private static final List<EncryptionAlgorithm> VALUES =Collections.unmodifiableList(Arrays.asList(values()));
	private static final int SIZE = VALUES.size();
	private static final Random RANDOM = new Random();
	
	public static EncryptionAlgorithm randomAlgorithm()  {
	    return VALUES.get(RANDOM.nextInt(SIZE));
	}
}
