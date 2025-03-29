package com.cimeliarchium.service.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.cimeliarchium.exception.AppException;
import com.cimeliarchium.service.helper.SessionAttributeHelperService;

@Service
@PropertySource({
	"classpath:/errorcode.properties"
})
public class InsetMapBackgroundService extends SessionAttributeHelperService {

	private static final Logger LOGGER = LoggerFactory.getLogger(InsetMapBackgroundService.class);

	/**
	 * Method used to display inset map background overlay images for use by Leaflet JavaScript.
	 * Note that for every overlay image built, a new ByteArrayOutputStream gets initialized, 
	 * in order to avoid asynchronous requests overwriting each other in the response output.
	 * 
	 * @param insetName the map Inset name
	 * @param response the HttpServletResponse
	 * @param context the ServletContext
	 * @throws AppException
	 * @throws IOException 
	 */
	public void buildInsetMapBackground(
			@NotNull String insetName, 
			@NotNull HttpServletResponse response,
			@NotNull ServletContext context) throws AppException, IOException {

		super.throwIfMissing(response, buildInsetMapBackgroundMissingInsetName);
		super.throwIfMissing(response, buildInsetMapBackgroundMissingResponse);
		super.throwIfMissing(context, buildInsetMapBackgroundMissingContext);

		// Build the background image file path
		final StringBuilder fileNameBuilder = new StringBuilder()
				.append("/static/images/map-tiles/")
				.append("map-background")
				.append("-")
				.append(insetName)
				.append(".png");
		// Download the background image
		ByteArrayOutputStream outputStream = null;
		InputStream inputStream = null;
		try {
			outputStream = new ByteArrayOutputStream();
			final String fileName = fileNameBuilder.toString();
			inputStream = context.getResourceAsStream(fileName);
			LOGGER.trace("Found map background image '{}' on classpath: {}", fileName, inputStream != null);
			response.setContentType(MediaType.IMAGE_PNG_VALUE);
			response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=\\" + fileName);
			IOUtils.copy(inputStream, outputStream);
			response.getOutputStream().write(outputStream.toByteArray());
		} finally {
			try {
				if (outputStream != null) {
					outputStream.flush();
					outputStream.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				throw new AppException(buildInsetMapBackgroundError);
			}
		}
	}

	@Value("${insetmapbackgroundservice.buildinsetmapbackground.insetname}")
	private String buildInsetMapBackgroundMissingInsetName;

	@Value("${insetmapbackgroundservice.buildinsetmapbackground.response}")
	private String buildInsetMapBackgroundMissingResponse;

	@Value("${insetmapbackgroundservice.buildinsetmapbackground.context}")
	private String buildInsetMapBackgroundMissingContext;

	@Value("${insetmapbackgroundservice.buildinsetmapbackground.error}")
	private String buildInsetMapBackgroundError;

}
