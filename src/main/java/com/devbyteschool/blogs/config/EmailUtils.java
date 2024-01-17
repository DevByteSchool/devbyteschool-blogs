package com.devbyteschool.blogs.config;

import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
public class EmailUtils {

	@Autowired
	private JavaMailSender mailSender;

	public void sendMail(String from, String to, String subject, String body) {
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
				mimeMessage.setFrom(new InternetAddress(from));
				mimeMessage.setSubject(subject);
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
				helper.setText(body, body);
			}
		};

		try {
			mailSender.send(preparator);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void sendMailWithAttachment(String from, String to, String subject, String body,
			List<MultipartFile> listMultipartFile) {
		List<File> listFile = new ArrayList<File>();
		try {

			MimeMessagePreparator preparator = new MimeMessagePreparator() {

				public void prepare(MimeMessage mimeMessage) throws Exception {
					mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
					mimeMessage.setFrom(new InternetAddress(from));
					mimeMessage.setSubject(subject);
					MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
					helper.setText(body, true);

					for (MultipartFile multipartFile : listMultipartFile) {

						File file = convertMultiPartToFile(multipartFile);
						helper.addAttachment(file.getName(), file);
						listFile.add(file);

					}
				}
			};
			mailSender.send(preparator);
			for (File file : listFile) {
				file.delete();
				file.exists();
			}
			listFile.clear();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.gc();
		}

	}

	public void sendMailWithInlineResources(String from, String to, String subject, String body,
			List<MultipartFile> listMultipartFile) {
		List<File> listFile = new ArrayList<File>();
		try {

			MimeMessagePreparator preparator = new MimeMessagePreparator() {
				public void prepare(MimeMessage mimeMessage) throws Exception {
					mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
					mimeMessage.setFrom(new InternetAddress(from));
					mimeMessage.setSubject(subject);
					MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
					helper.setText(body, true);
					for (MultipartFile multipartFile : listMultipartFile) {
						File file = convertMultiPartToFile(multipartFile);
						helper.addInline(file.getName(), file);
						listFile.add(file);
					}

				}
			};

			mailSender.send(preparator);
			for (File file : listFile) {
				file.delete();
				file.exists();
			}
			listFile.clear();

		} catch (MailException ex) {
			ex.printStackTrace();

		} finally {
			System.gc();
		}
	}
	
	private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        convFile.deleteOnExit();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        convFile.exists();
        return convFile;
    
    }

}
