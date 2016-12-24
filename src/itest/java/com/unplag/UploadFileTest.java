package com.unplag;

import com.unplag.model.UFile;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;

public class UploadFileTest {

	private final Unplag unplag;

	{
		String key = System.getProperty("key");
		String secret = System.getProperty("secret");
		unplag = new Unplag(key, secret);
	}

	private UFile uFile;

	@After
	public void deleteFile() throws Exception {
		System.out.println("Deleting file..");
		if (uFile == null)
			return;
		unplag.deleteFile(uFile.getId());
	}

	@Test
	public void uploadFile1() throws Exception {
		String text = "Jackson JSON processor could be controlled via providing a custom Jackson 2 ObjectMapper (or ObjectMapper for Jackson 1) instance. This could be handy if you need to redefine the default Jackson behaviour and to fine-tune how your JSON data structures look like. Detailed description of all Jackson features is out of scope of this guide. The example below gives you a hint on how to wire your ObjectMapper (ObjectMapper) instance into your Jersey application.";

		try (ByteArrayInputStream b = new ByteArrayInputStream(text.getBytes());
		     BufferedInputStream buff = new BufferedInputStream(b)) {

			UFile docx = unplag.uploadFile(buff, "docx", null);
			Assert.assertNotNull(docx);
			System.out.println(docx);
			uFile = docx;
		}
	}

	@Test
	public void uploadFile2() throws Exception {
		File file = new File("src/itest/resources/275word.docx");
		UFile docx = unplag.uploadFile(file, "docx", "275word");
		Assert.assertNotNull(docx);
		System.out.println(docx);
		uFile = docx;
	}

	@Test
	public void getFileInfo() throws Exception {
		uploadFile1();
		long id = uFile.getId();
		UFile docx = unplag.getFileInfo(id);
		Assert.assertNotNull(docx);
	}

}
