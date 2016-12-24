package com.unplag.exception;

import java.io.IOException;

public class UnplagApiException  extends IOException {

	public UnplagApiException() {
	}

	public UnplagApiException(String message) {
		super(message);
	}

	public UnplagApiException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnplagApiException(Throwable cause) {
		super(cause);
	}
}
