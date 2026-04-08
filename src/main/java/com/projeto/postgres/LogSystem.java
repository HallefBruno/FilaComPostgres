package com.projeto.postgres;

import java.util.Arrays;
import java.util.stream.Collectors;

public class LogSystem {
	
	private LogSystem() {}

	public static void log(Object c, Object... params) {
		String msg = Arrays.stream(params).map(String::valueOf).collect(Collectors.joining(", "));
		System.out.println(c.getClass().getCanonicalName()+ ", " + msg);
	}
}
