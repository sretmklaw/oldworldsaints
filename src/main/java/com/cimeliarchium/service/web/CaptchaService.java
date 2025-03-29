package com.cimeliarchium.service.web;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.AttributedString;
import java.util.Base64;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.cimeliarchium.enums.DisplayKeyEnum;
import com.cimeliarchium.enums.ModelAttributeEnum;
import com.cimeliarchium.enums.SessionAttributeEnum;
import com.cimeliarchium.exception.AppException;
import com.cimeliarchium.service.helper.SessionAttributeHelperService;

@Service
@PropertySource("classpath:/errorcode.properties")
public class CaptchaService extends SessionAttributeHelperService {

	// Generated Text Constants
	private static final String TEXT_SALT = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	private static final Integer TEXT_LENGTH = 7;
	private static final Integer TEXT_WIDTH = 10;
	private static final Integer TEXT_HEIGHT = 25;

	// Image Display Constants
	private static final String IMAGE_FILETYPE = "JPEG";
	private static final Integer IMAGE_WIDTH = 100;
	private static final Integer IMAGE_HEIGHT = 40;
	private static final Font IMAGE_FONT = new Font("Arial", Font.ITALIC, 20);
	private static final Color IMAGE_FOREGROUND = new Color(100,100,100);
	private static final Color IMAGE_BACKGROUND = new Color(200,200,200);

	public static Boolean hasErrorCode(String errorCode) {
		return errorCode != null 
				&& !errorCode.isEmpty() 
				&& !errorCode.equals(DisplayKeyEnum.UNKNOWN.getValue());
	}

	/**
	 * Method used to obtain pseudo-random hexadecimal string
	 * 
	 * @param session the HttpSession
	 * @return hexString
	 * @throws AppException 
	 */
	public String generateText(HttpSession session) throws AppException {

		super.throwIfMissing(session, generateTextMissingSession);
		final StringBuffer captchaStrBuffer = new StringBuffer();
		final Random randomizer = new Random();
		while (captchaStrBuffer.length() < TEXT_LENGTH) {
			int index = (int) (randomizer.nextFloat() * TEXT_SALT.length());
			captchaStrBuffer.append(TEXT_SALT.substring(index, index + 1));
		}
		final String captchaText = captchaStrBuffer.toString();
		session.setAttribute(SessionAttributeEnum.CAPTCHA.getValue(), captchaText);
		return captchaText;
	}

	/*
	 * Method used to draw image from generated text
	 * 
	 * @param session the HttpSession
	 * @param model the Model
	 */
	public void initModel(
			HttpSession session,
			Model model) throws AppException {

		final String captchaImage = this.generateImage(session);
		model.addAttribute(ModelAttributeEnum.CAPTCHA_IMAGE.getValue(), captchaImage);
	}

	/**
	 * Method used to generate image
	 * 
	 * @param session the HttpSession
	 * @return captchaImage
	 * @throws AppException
	 */
	public String generateImage(HttpSession session) throws AppException {

		// Initialize the CAPTCHA text
		final String captchaText = this.generateText(session);
		super.throwIfMissing(captchaText, generateImageMissingCaptchaText);

		// Attempt to draw image with OutputStream closure
		String captchaImage = null;
		try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
			final BufferedImage buffer = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
			final Graphics img = buffer.createGraphics();
			AttributedString attrString = new AttributedString(captchaText);
			attrString.addAttribute(TextAttribute.FONT, IMAGE_FONT);
			attrString.addAttribute(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
			img.setFont(IMAGE_FONT);
			img.setColor(IMAGE_FOREGROUND);
			img.fillRect(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);
			img.setColor(IMAGE_BACKGROUND);
			img.drawString(attrString.getIterator(), TEXT_WIDTH, TEXT_HEIGHT);
			ImageIO.write(buffer, IMAGE_FILETYPE, output);
			captchaImage = Base64.getEncoder().encodeToString(output.toByteArray());
		} 
		// Re-throw any Exception, after safely closing OutputStream
		catch (IOException ex) {
			throw new AppException(generateImageMissingCaptchaImage);
		}
		return captchaImage;
	}

	/**
	 * Method used to display CAPTCHA and for Request submission.
	 * We should only generate a CAPTCHA and enable form submit given
	 * an authenticated user, and when a predefined error type code exists.
	 * 
	 * @param model the Model
	 * @param session the HttpSession
	 * @param hasErrorCode whether the error is undefined
	 * @throws AppException
	 */
	public void maybeRegenerateCaptchaForUser(
			Model model, 
			@NotNull HttpSession session,
			@NotNull Boolean hasErrorCode) throws AppException {

		if (hasErrorCode) {
			model.addAttribute(ModelAttributeEnum.CAPTCHA_IMAGE.getValue(), this.generateImage(session));
			model.addAttribute(ModelAttributeEnum.CAPTCHA_ENABLED.getValue(), true);
			model.addAttribute(ModelAttributeEnum.FORM_ENABLED.getValue(), true);
		}
	}

	@Value("${captchaservice.generatetext.session}")
	private String generateTextMissingSession;

	@Value("${captchaservice.generateimage.captchatext}")
	private String generateImageMissingCaptchaText;

	@Value("${captchaservice.generateimage.captchaimage}")
	private String generateImageMissingCaptchaImage;
}
