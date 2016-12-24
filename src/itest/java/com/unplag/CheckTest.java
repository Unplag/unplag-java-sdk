package com.unplag;

import com.unplag.model.UCheck;
import com.unplag.model.UFile;
import com.unplag.model.UPdfReport;
import com.unplag.model.UType;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.util.Locale;

public class CheckTest {

	private final Unplag unplag;

	{
		String key = System.getProperty("key");
		String secret = System.getProperty("secret");
		unplag = new Unplag(key, secret);
	}

	private UFile uFile;

	@Before
	public void upload() throws Exception {

		String text = "Carlos Ray \"Chuck\" Norris (born March 10, 1940) is an American martial artist, actor, film producer and screenwriter. After serving in the United States Air Force, he began his rise to fame as a martial artist, and has since founded his own school, Chun Kuk Do.";
		try (ByteArrayInputStream bais = new ByteArrayInputStream(text.getBytes());
		     BufferedInputStream bis = new BufferedInputStream(bais)
		) {
			System.out.println("Uploading document");
			UFile uFile = unplag.uploadFile(bis, "txt", "chuck-sob4ak");
			System.out.println(uFile);
			this.uFile = uFile;
		}
	}

	@Test
	public void checkDocument() throws Exception {

		UCheck check = unplag.createCheck(uFile.getId(), UType.WEB, null, null, null);
		System.out.println("Launched check\t" + check);

		while (check.getProgress() < 1.0) {
			check = unplag.getCheckInfo(check.getId());
			System.out.println("Get info\t" + check);
			Thread.sleep(2000L);
		}
		Assert.assertNotNull(check);
	}

	@Test
	public void generateReport() throws Exception {
		checkDocument();
		uFile = unplag.getFileInfo(uFile.getId());
		long checkId = uFile.getUChecks()[0].getId();
		System.out.println("Generating report");
		UPdfReport uPdfReport = unplag.generateReport(checkId, null);
		while (uPdfReport.getStatus().equals("generate")) {
			Thread.sleep(1000L);
			uPdfReport = unplag.generateReport(checkId, null);
		}
		Assert.assertNotNull(uPdfReport);
		System.out.println(uPdfReport);
	}

	@Test
	public void toggleCitations() throws Exception {
		checkDocument();
		uFile = unplag.getFileInfo(uFile.getId());
		long checkId = uFile.getUChecks()[0].getId();
		System.out.println("Toggling citations");
		UCheck uCheck = unplag.toggleCitations(checkId, true, true);
		Assert.assertNotNull(uCheck);
	}

	@After
	public void deleteChecksAndFile() throws Exception {
		uFile = unplag.getFileInfo(uFile.getId());
		for (UCheck check : uFile.getUChecks()) {
			long id = check.getId();
			try {
				System.out.println("Deleting check with id " + id);
				check = unplag.deleteCheck(id);
				System.out.println(check);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		System.out.println("Deleting file");
		unplag.deleteFile(uFile.getId());
	}

}
