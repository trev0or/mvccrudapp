
package com.nyn.service;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

public class EmpService {

	public ResponseEntity<?> download() throws Exception {

		
		File file = new File("sample.zip");
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		ZipOutputStream zipOut = new ZipOutputStream(fileOutputStream);

		int c = 1;
		String filePaths[] = { "C:\\Users\\Trevor\\Desktop\\img1.jpg", "C:\\Users\\Trevor\\Desktop\\img2.jpg", };

		for (String fileName : filePaths) {
			// FileSystemResource resource = new FileSystemResource(fileBasePath +
			// fileName);
			// ZipEntry zipEntry = new ZipEntry(resource.getFilename());
			// zipEntry.setSize(resource.contentLength());

			try {
				byte[] bytes = Files.readAllBytes(Paths.get(fileName));
				zipOut.putNextEntry(new ZipEntry(c + ".jpeg"));

				StreamUtils.copy(bytes, zipOut);
				zipOut.closeEntry();
				c++;
			} catch (Exception e) {
				System.out.println("Skipping for " + fileName);
			}
		}
		zipOut.finish();
		zipOut.close();
		byte[] b = Files.readAllBytes(Paths.get(file.getPath()));
		return ResponseEntity.ok()
				// Content-Disposition
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
				// Content-Type
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				// Contet-Length
				.contentLength(file.length()) //
				.body(b);
	}

	
	  public ResponseEntity<?> downloadzip(MultipartFile csvFile) throws Exception
	  { File file = new File("sample.zip"); // FileReader fileReader = new
	 
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		ZipOutputStream zipOut = new ZipOutputStream(fileOutputStream);
	 
	  InputStreamReader inputStreamReader	=new InputStreamReader(csvFile.getInputStream());
	  CSVReader csvReader = new CSVReaderBuilder(inputStreamReader) 
              .withSkipLines(1) 
              .build(); 
	  List<String[]> allData = csvReader.readAll();
	  for (String[] row : allData) { 
			try {
				byte[] bytes = Files.readAllBytes(Paths.get(row[1]));
				zipOut.putNextEntry(new ZipEntry(row[0]+ ".jpeg"));

				StreamUtils.copy(bytes, zipOut);
				zipOut.closeEntry();
				
			} catch (Exception e) {
				System.out.println("Skipping for " + row[1]);
			}
      } 
	  
	
	  byte[] b =Files.readAllBytes(Paths.get(file.getPath())); 
	  System.out.println("data aadded to the zip file");
	  file.delete();
	  System.out.println("zip file deleted"); 
	  return ResponseEntity.ok()
				// Content-Disposition
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
				// Content-Type
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				// Contet-Length
				.contentLength(file.length()) //
				.body(b);
	  
	  
	  
	  }
	 
	
	
	
	
}
