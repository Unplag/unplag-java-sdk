package com.unplag;

import com.unplag.exception.UnplagApiException;
import com.unplag.model.Rsp;
import com.unplag.model.UCheck;
import com.unplag.model.UFile;
import com.unplag.model.UPdfReport;
import com.unplag.model.UType;
import org.glassfish.jersey.client.oauth1.ConsumerCredentials;
import org.glassfish.jersey.client.oauth1.OAuth1ClientSupport;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.BodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.StreamDataBodyPart;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * Class provides functionality of https://www.unplag.com/api/v2
 */
public final class Unplag {

	private static final String UNPLAG_API_URI = "https://unplag.com/api/v2";
	private static final String UPLOAD = "file/upload";
	private static final String FILE_GET = "file/get";
	private static final String FILE_DELETE = "file/delete";
	private static final String CHECK_CREATE = "check/create";
	private static final String CHECK_GET = "check/get";
	private static final String CHECK_DELETE = "check/delete";
	private static final String CHECK_GENERATE_PDF_REPORT = "check/generate_pdf";
	private static final String CHECK_TOGGLE_CITATIONS = "check/toggle";

	private final WebTarget target;

	/**
	 * Constructs Unplag Api Client. Requires api key and secret
	 *
	 * @param apiKey    get key on https://unplag.com
	 * @param apiSecret get secret on https://unplag.com
	 */
	public Unplag(String apiKey, String apiSecret) {

		checkParams("API Key, API Secret must not be empty", apiKey, apiSecret);

		ConsumerCredentials consumerCredentials = new ConsumerCredentials(apiKey, apiSecret);
		Feature oauthFilterFeature = OAuth1ClientSupport.builder(consumerCredentials).feature().build();
		Client client = ClientBuilder
				.newBuilder()
				.register(oauthFilterFeature)
				.register(JacksonFeature.class)
				.register(MultiPartFeature.class)
				.build();
		this.target = client.target(UNPLAG_API_URI);
	}

	/**
	 * Uploads file
	 *
	 * @param file   your file
	 * @param format extension of file to upload. Allowed values: doc, docx, html, htm, odt, rtf, txt, pdf
	 * @param name   optional param
	 * @return Unplag file (UFile)
	 * @throws Exception if can't upload file
	 */
	public UFile uploadFile(File file, String format, String name) throws Exception {

		checkParams("File must not be NULL", file);

		try (FileInputStream fis = new FileInputStream(file);
		     BufferedInputStream bis = new BufferedInputStream(fis)) {

			return uploadFile(bis, format, name);
		}
	}

	/**
	 * Uploads file from InputStream
	 *
	 * @param in     InputStream
	 * @param format extension of file to upload. Allowed values: doc, docx, html, htm, odt, rtf, txt, pdf
	 * @param name   optional param
	 * @return Unplag file (UFile)
	 * @throws Exception if can't upload file
	 */
	public UFile uploadFile(InputStream in, String format, String name) throws Exception {

		checkParams("InputStream, format must not be NULL", in, format);

		BodyPart filePart = new StreamDataBodyPart("file", in);
		FormDataMultiPart multiPart = new FormDataMultiPart()
				.field("format", format);
		if (!isEmpty(name))
			multiPart.field("name", name);
		multiPart.bodyPart(filePart);

		Response response = target
				.path(UPLOAD)
				.request(MediaType.MULTIPART_FORM_DATA_TYPE)
				.accept(MediaType.APPLICATION_JSON)
				.post(Entity.entity(multiPart, multiPart.getMediaType()));

		Rsp.FileResponse rsp = response.readEntity(Rsp.FileResponse.class);
		checkResponse(rsp);

		return rsp.getValue();
	}

	/**
	 * Gets file info by id
	 *
	 * @param id file id
	 * @return Unplag file
	 * @throws Exception if file doesn't exist
	 */
	public UFile getFileInfo(long id) throws Exception {

		Rsp.FileResponse rsp = target
				.path(FILE_GET)
				.queryParam("id", id)
				.request(MediaType.APPLICATION_FORM_URLENCODED)
				.accept(MediaType.APPLICATION_JSON_TYPE)
				.get(Rsp.FileResponse.class);

		checkResponse(rsp);

		return rsp.getValue();
	}

	/**
	 * Deletes file
	 *
	 * @param id file id
	 * @return deleted file
	 * @throws Exception if can't delete file
	 */
	public UFile deleteFile(long id) throws Exception {

		MultivaluedMap<String, String> map = new MultivaluedHashMap<>();
		map.putSingle("id", String.valueOf(id));

		Rsp.FileResponse rsp = target
				.path(FILE_DELETE)
				.request()
				.accept(MediaType.APPLICATION_JSON_TYPE)
				.buildPost(Entity.entity(map, MediaType.APPLICATION_FORM_URLENCODED_TYPE)).invoke(Rsp.FileResponse.class);

		checkResponse(rsp);

		return rsp.getValue();
	}

	/**
	 * Creates and launches check of existed file
	 *
	 * @param id                file id
	 * @param type              check type
	 * @param versusFiles       optional param required for check type Doc_vs_docs
	 * @param excludeCitations  optional param for excluding citations
	 * @param excludeReferences optional param for excluding references
	 * @return check information
	 * @throws Exception if can't create check
	 */
	public UCheck createCheck(long id, UType type, Long[] versusFiles, Boolean excludeCitations, Boolean excludeReferences)
			throws Exception {

		return createCheckWithCallback(id, type, null, versusFiles, excludeCitations, excludeReferences);
	}

	/**
	 * See {@link #createCheck(long, UType, Long[], Boolean, Boolean) createCheck}
	 * This method has difference - <b>unplag.com</b> will send check info to callback url
	 *
	 * @param id                file id
	 * @param type              check type
	 * @param callback          url to send callback, when check will ended.
	 * @param versusFiles       optional param required for check type Doc_vs_docs
	 * @param excludeCitations  optional param for excluding citations
	 * @param excludeReferences optional param for excluding references
	 * @return check information
	 * @throws Exception if can't create check
	 */
	public UCheck createCheckWithCallback(long id, UType type, String callback, Long[] versusFiles, Boolean excludeCitations,
			Boolean excludeReferences) throws Exception {

		checkParams("Check Type must not be NULL", type);
		if (type == UType.DOC_VS_DOCS && versusFiles == null) {
			throw new IllegalArgumentException("DOC_VS_DOCS requires versusFiles parameter");
		}

		MultivaluedMap<String, String> params = new MultivaluedHashMap<>();
		params.putSingle("file_id", String.valueOf(id));
		params.putSingle("type", type.getValue());
		if (!isEmpty(callback)) {
			params.putSingle("callback_url", callback);
		}
		if (versusFiles != null && versusFiles.length > 0) {
			params.put("versus_files", Arrays.stream(versusFiles).map(String::valueOf).collect(Collectors.toList()));
		}
		if (excludeCitations != null) {
			params.putSingle("exclude_citations", String.valueOf(excludeCitations ? 1 : 0));
		}
		if (excludeReferences != null) {
			params.putSingle("exclude_references", String.valueOf(excludeReferences ? 1 : 0));
		}

		Rsp.CheckResponse rsp = target
				.path(CHECK_CREATE)
				.request()
				.accept(MediaType.APPLICATION_JSON_TYPE)
				.buildPost(Entity.entity(params, MediaType.APPLICATION_FORM_URLENCODED_TYPE))
				.invoke(Rsp.CheckResponse.class);

		checkResponse(rsp);

		return rsp.getValue();
	}

	/**
	 * Gets check info
	 *
	 * @param id check id
	 * @return Check information
	 * @throws Exception if can't get check info
	 */
	public UCheck getCheckInfo(long id) throws Exception {

		Rsp.CheckResponse rsp = target
				.path(CHECK_GET)
				.queryParam("id", id)
				.request(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
				.accept(MediaType.APPLICATION_JSON_TYPE)
				.get(Rsp.CheckResponse.class);

		checkResponse(rsp);

		return rsp.getValue();
	}

	/**
	 * Gets check info after check ends
	 *
	 * @param id check id
	 * @return Check information
	 * @throws Exception if can't get check info
	 */
	public UCheck waitForCheckInfo(long id) throws Exception {
		UCheck uCheck = getCheckInfo(id);
		while (uCheck.getProgress() < 1.0) {
			Thread.sleep(60000L); // 1min
			uCheck = getCheckInfo(id);
		}
		return uCheck;
	}

	/**
	 * Deletes check
	 *
	 * @param id check id
	 * @return check info (id)
	 * @throws Exception if can't delete check
	 */
	public UCheck deleteCheck(long id) throws Exception {

		MultivaluedMap<String, String> params = new MultivaluedHashMap<>();
		params.putSingle("id", String.valueOf(id));

		Rsp.CheckResponse rsp = target
				.path(CHECK_DELETE)
				.request()
				.accept(MediaType.APPLICATION_JSON_TYPE)
				.buildPost(Entity.entity(params, MediaType.APPLICATION_FORM_URLENCODED_TYPE))
				.invoke(Rsp.CheckResponse.class);

		checkResponse(rsp);

		return rsp.getValue();
	}

	/**
	 * Generates report
	 *
	 * @param id     check id
	 * @param locale optional param. Allowed locales: en_EN, uk_UA, es_ES, nl_BE
	 * @return link to download pdf report
	 * @throws Exception if can't generate pdf report
	 */
	public UPdfReport generateReport(long id, Locale locale) throws Exception {

		MultivaluedMap<String, String> map = new MultivaluedHashMap<>();
		map.putSingle("id", String.valueOf(id));
		if (locale != null) {
			map.putSingle("lang", locale.toString());
		}

		Rsp.PdfReportResponse rsp = target
				.path(CHECK_GENERATE_PDF_REPORT)
				.request()
				.accept(MediaType.APPLICATION_JSON_TYPE)
				.buildPost(Entity.entity(map, MediaType.APPLICATION_FORM_URLENCODED_TYPE))
				.invoke(Rsp.PdfReportResponse.class);

		checkResponse(rsp);

		return rsp.getValue();
	}

	/**
	 * Toggles citations and references of already ended check
	 *
	 * @param id                check id
	 * @param excludeCitations  flag to turn off citations
	 * @param excludeReferences flag to turn off references
	 * @return check info with excluded / included citations
	 * @throws Exception if can't toggle citations
	 */
	public UCheck toggleCitations(long id, boolean excludeCitations, boolean excludeReferences) throws Exception {
		MultivaluedMap<String, String> map = new MultivaluedHashMap<>();
		map.putSingle("id", String.valueOf(id));
		map.putSingle("exclude_citations", String.valueOf(excludeCitations ? 1 : 0));
		map.putSingle("exclude_references", String.valueOf(excludeReferences ? 1 : 0));

		Rsp.ToggleCitationsResponse rsp = target
				.path(CHECK_TOGGLE_CITATIONS)
				.request()
				.accept(MediaType.APPLICATION_JSON_TYPE)
				.buildPost(Entity.entity(map, MediaType.APPLICATION_FORM_URLENCODED_TYPE))
				.invoke(Rsp.ToggleCitationsResponse.class);

		checkResponse(rsp);

		return rsp.getValue();
	}

	/**
	 * Checks request params
	 *
	 * @param errorText text for throwing Exception
	 * @param params    request params
	 * @throws IllegalArgumentException if params are incorrect
	 */
	private void checkParams(String errorText, Object... params) throws IllegalArgumentException {
		for (Object param : params) {
			if (param == null ||
					(param instanceof String && isEmpty(((String) param)))) {
				throw new IllegalArgumentException(errorText);
			}
		}
	}

	/**
	 * Checks response for errors
	 *
	 * @param response unplag response
	 * @throws UnplagApiException if response contains any errors
	 */
	private void checkResponse(Rsp response) throws UnplagApiException {
		Objects.requireNonNull(response);
		if (!response.isSuccessful()) {
			throw new UnplagApiException(Arrays.toString(response.getErrors()));
		}
	}

}
