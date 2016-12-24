package com.unplag;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

public class UnplagTest {

	private final Unplag unplag = new Unplag("xxx", "yyy");

	@Test(expected = IllegalArgumentException.class)
	public void uploadFileNull() throws Exception {
		unplag.uploadFile((File) null, "doc", null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void uploadStreamNull() throws Exception {
		unplag.uploadFile((InputStream) null, "doc", null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void uploadFormatNull() throws Exception {
		try (ByteArrayInputStream bais = new ByteArrayInputStream(new byte[] { 1, 2, 3 })) {
			unplag.uploadFile(bais, null, null);
		}
	}

}
