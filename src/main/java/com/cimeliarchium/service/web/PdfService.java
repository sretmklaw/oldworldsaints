package com.cimeliarchium.service.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.StringJoiner;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.cimeliarchium.exception.AppException;
import com.cimeliarchium.model.dao.Reference;
import com.cimeliarchium.model.dao.Star;
import com.cimeliarchium.model.dto.DayCommemorationDto;
import com.cimeliarchium.service.dao.StarService;
import com.cimeliarchium.service.dto.DayCommemorationService;
import com.cimeliarchium.service.helper.SessionAttributeHelperService;

@Service
@PropertySource({
	"classpath:/errorcode.properties"
})
public class PdfService extends SessionAttributeHelperService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PdfService.class);

	private static final String PDF_ERROR_TIMEOUT = "PDF Error / Timeout";
	private static final String LOAD_FAILURE = "Retryable Failure on Load";
	public static final String DOC_NOT_FOUND = "Document not Found";

	private DayCommemorationService dayCommemorationStaticInitializer;
	private StarService starService;

	@Autowired
	public PdfService(DayCommemorationService dayCommemorationStaticInitializer, StarService starService) {
		this.dayCommemorationStaticInitializer = dayCommemorationStaticInitializer;
		this.starService = starService;
	}

	/**
	 * Method used to directly display the specified commemoration's Reference in the browser.
	 * Generates a PDF containing only the targeted page range excerpted from the full document.
	 * Note that for every PDF being built, a new ByteArrayOutputStream must be initialized, 
	 * in order to avoid asynchronous requests overwriting each other in the response output.
	 * 
	 * @param session the HttpSession
	 * @param commemorationId the Commemoration ID
	 * @param response the HttpServletResponse
	 * @param context the ServletContext
	 * @throws AppException
	 */
	public void buildCommemorationPDF(Long commemorationId, 
			@NotNull HttpSession session, 
			@NotNull HttpServletResponse response,
			@NotNull ServletContext context) throws AppException {

		super.throwIfMissing(session, buildPDFMissingSession);
		super.throwIfMissing(response, buildPDFMissingResponse);
		super.throwIfMissing(context, buildPDFMissingContext);

		final DayCommemorationDto dto = dayCommemorationStaticInitializer.getDtoMapById().get(commemorationId);
		ByteArrayOutputStream outputStream = null;
		try {
			outputStream = new ByteArrayOutputStream();
			if (dto == null) {
				// Return an empty PDF if no matching values are found for the passed-in ID.
				LOGGER.warn("Commemoration not found ID: {}", commemorationId);
				this.writeBlankPDFWithError(DOC_NOT_FOUND, response, outputStream);
			} else {
				final String commemorationName = dto.getCommemorationName();
				final Reference reference = dto.getReference();
				if (reference == null) {
					this.writeBlankPDFWithError(PdfService.DOC_NOT_FOUND, response, outputStream);
				} else {
					// Construct document title consisting of commemoration name, reference name, volume, and page range.
					final Long firstPageNumber = dto.getReferenceStart();
					final Long lastPageNumber = dto.getReferenceEnd();
					final StringBuilder excerptPdfTitle = new StringBuilder()
							.append(commemorationName).append(" - ")
							.append(reference.getReferenceName()).append(", ")
							.append("Vol. ").append(reference.getReferenceVolume()).append(", ");
					final StringJoiner exerptPdfPageRange = new StringJoiner("-").add(String.valueOf(firstPageNumber));
					if (lastPageNumber != null && (firstPageNumber < lastPageNumber)) {
						exerptPdfPageRange.add(String.valueOf(lastPageNumber));
					}
					// Build the PDF file path
					excerptPdfTitle.append("p. ").append(exerptPdfPageRange.toString());
					final StringBuilder filename = new StringBuilder()
							.append("/static/pdfs/")
							.append(commemorationId)
							.append(".pdf");
					// Download the PDF file and add the descriptive document title
					this.buildPDF(filename.toString(), excerptPdfTitle.toString(), outputStream, response, context);
				}
			}
		} finally {
			try {
				if (outputStream != null) {
					outputStream.flush();
					outputStream.close();
				}
			} catch (IOException e) {
				throw new AppException(buildPDFError);
			}
		}
	}

	/**
	 * Method used to directly display the specified star's Reference in the browser.
	 * Generates a PDF containing only the targeted page range excerpted from the full document.
	 * Note that for every PDF being built, a new ByteArrayOutputStream must be initialized, 
	 * in order to avoid asynchronous requests overwriting each other in the response output.
	 * 
	 * @param session the HttpSession
	 * @param starId the Star ID
	 * @param response the HttpServletResponse
	 * @param context the ServletContext
	 * @throws AppException
	 */
	public void buildStarPDF(Long starId, 
			@NotNull HttpSession session, 
			@NotNull HttpServletResponse response,
			@NotNull ServletContext context) throws AppException {

		super.throwIfMissing(session, buildPDFMissingSession);
		super.throwIfMissing(response, buildPDFMissingResponse);
		super.throwIfMissing(context, buildPDFMissingContext);

		final Star star = starService.getCachedValues(session).get(starId);
		ByteArrayOutputStream outputStream = null;
		try {
			outputStream = new ByteArrayOutputStream();
			if (star == null) {
				// Return an empty PDF if no matching values are found for the passed-in ID.
				LOGGER.warn("Star not found ID: {}", starId);
				this.writeBlankPDFWithError(DOC_NOT_FOUND, response, outputStream);
			} else {
				final String starName = star.getStarName();
				if (star.getReferenceStart() == null) {
					this.writeBlankPDFWithError(PdfService.DOC_NOT_FOUND, response, outputStream);
				} else {
					// Construct document title consisting of star name, reference name, and page range.
					final StringBuilder excerptPdfTitle = new StringBuilder()
							.append(starName).append(" - ")
							.append(star.getReferenceFormatted());
					// Build the PDF file path
					final StringBuilder filename = new StringBuilder()
							.append("/static/pdfs/")
							.append(starId)
							.append(".pdf");
					// Download the PDF file and add the descriptive document title
					this.buildPDF(filename.toString(), excerptPdfTitle.toString(), outputStream, response, context);
				}
			}
		} finally {
			try {
				if (outputStream != null) {
					outputStream.flush();
					outputStream.close();
				}
			} catch (IOException e) {
				throw new AppException(buildPDFError);
			}
		}
	}

	private void buildPDF(String filename, 
			String excerptPdfTitle,
			@NotNull ByteArrayOutputStream outputStream,
			@NotNull HttpServletResponse response,
			@NotNull ServletContext context) throws AppException {

		try (final InputStream inputStream = context.getResourceAsStream(filename)) {
			final byte[] excerptByteArray = inputStream.readAllBytes();
			final PDDocument excerptPdf = PDDocument.load(excerptByteArray);
			excerptPdf.getDocumentInformation().setTitle(excerptPdfTitle);
			this.writeExcerptedPDF(excerptPdf, PDF_ERROR_TIMEOUT, response, outputStream);
			try {
				excerptPdf.close();
			} catch (IOException e) {
				throw new AppException(buildPDFError);
			}
		}
		// If PDF fails to load, simply return an empty document and note the error.
		catch (IOException ex) {
			this.writeBlankPDFWithError(LOAD_FAILURE, response, outputStream);
		}
	}

	/**
	 * Catch-all method used to handle read or write errors at any point during PDF processing.
	 * 
	 * @param errorMessage the error message, which gets displayed as PDF title.
	 * @param response the HttpServletResponse
	 * @param outputStream the ByteArrayOutputStream
	 * @throws AppException
	 */
	public void writeBlankPDFWithError(String errorMessage, 
			HttpServletResponse response, 
			ByteArrayOutputStream outputStream) throws AppException {

		final PDDocument errorPdfDoc = new PDDocument();
		final PDPage page = new PDPage();
		final PDFont pdfFont= PDType1Font.HELVETICA_BOLD;
		final int fontSize = 14;
		PDPageContentStream contentStream = null;
		try {
			// Error Message Font and Color
			contentStream = new PDPageContentStream(
					errorPdfDoc, 
					page, 
					PDPageContentStream.AppendMode.APPEND, true, true);
			contentStream.setFont(pdfFont, fontSize);
			contentStream.setNonStrokingColor(1f,0f,0f);
			// Error Message Line 1
			contentStream.beginText();
			contentStream.newLineAtOffset(185, page.getMediaBox().getUpperRightY() - 64);
			contentStream.showText("If this error has been seen repeatedly,");
			contentStream.endText();
			// Error Message Line 2
			contentStream.beginText();
			contentStream.newLineAtOffset(150, page.getMediaBox().getUpperRightY() - 80);
			contentStream.showText("please wait several minutes before retrying again,");
			contentStream.endText();
			// Error Message Line 3
			contentStream.beginText();
			contentStream.newLineAtOffset(180, page.getMediaBox().getUpperRightY() - 96);
			contentStream.showText("or else try clearing your browser's cache.");
			contentStream.endText();
			// Append Error Message to Document
			errorPdfDoc.addPage(page);
			contentStream.close();
		} catch (IOException ex) {
			throw new AppException(writeBlankPDFError);
		} finally {
			try {
				if (contentStream != null) {
					contentStream.close();
				}
			} catch (IOException e) {
				throw new AppException(writeBlankPDFError);
			}
		}
		errorPdfDoc.getDocumentInformation().setTitle(errorMessage);
		this.writeExcerptedPDF(errorPdfDoc, PDF_ERROR_TIMEOUT, response, outputStream);
		try {
			errorPdfDoc.close();
		} catch (IOException e) {
			throw new AppException(writeBlankPDFError);
		}
	}

	/**
	 * Method used to write the excerpted PDF document contents to output stream.
	 * 
	 * @param excerptPdfDoc the generated PDF dodument
	 * @param excerptPdfTitle the generated PDF title
	 * @param response the HttpServletResponse
	 * @param outputStream the ByteArrayOutputStream
	 * @return 
	 * @throws AppException
	 */
	private void writeExcerptedPDF(
			PDDocument excerptPdfDoc, 
			String excerptPdfTitle,
			@NotNull HttpServletResponse response,
			@NotNull ByteArrayOutputStream outputStream) throws AppException {

		try {
			response.setContentType(MediaType.APPLICATION_PDF_VALUE);
			response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=\"" + excerptPdfTitle + ".pdf\"");
			excerptPdfDoc.save(outputStream);
			response.getOutputStream().write(outputStream.toByteArray());
		} catch (IOException ex) {
			throw new AppException(writeExcerptedPDFError);
		}
	}

	@Value("${pdfservice.buildpdf.response}")
	private String buildPDFMissingResponse;

	@Value("${pdfservice.buildpdf.session}")
	private String buildPDFMissingSession;

	@Value("${pdfservice.buildpdf.context}")
	private String buildPDFMissingContext;

	@Value("${pdfservice.buildpdf.error}")
	private String buildPDFError;

	@Value("${pdfservice.writeexcerptedpdf.error}")
	private String writeExcerptedPDFError;

	@Value("${pdfservice.writeblankpdf.error}")
	private String writeBlankPDFError;

}
