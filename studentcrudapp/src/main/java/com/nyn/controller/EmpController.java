package com.nyn.controller;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.nyn.doa.EmployeeDao;
import com.nyn.model.Employee;
import com.nyn.service.EmpService;
@Configuration
@Controller
public class EmpController {
	@Autowired
	 EmployeeDao dao;
	@Autowired
	Employee employee;
	
	@Autowired
	EmpService empService;
	
	@RequestMapping(value="/save" ,method=RequestMethod.POST)
	public ModelAndView saveEmp(@RequestParam("id") int id, @RequestParam("name") String name, @RequestParam
			("salary") float salary) {
		int c = dao.saveEmp(id, name, salary);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("saveview");
		if(c>=1) {
			String out = "data saved"+"   "+"id = "+id+" "+"name =  "+" "+name;
			/* String out = employee.toString(); */
			mv.addObject("result", out);
			String res=c+" row affected";
			System.out.println(res);
			return mv;
			
		}
		
		else {
			
			String s = "no change occured";
			mv.addObject("result", s);
			System.out.println(s);
		return mv;
		}
		
		
	}
	@RequestMapping(value="/delete/{id}",method = RequestMethod.DELETE)
	public void delete(@PathVariable("id") int id) {
		
		int out = dao.deleteEmp(id);
		System.out.println(out+"records deleted");
		
	}
	
	
	@GetMapping(value = "/zipd", produces="application/zip")
	public void zipDownload( HttpServletResponse  response) throws Exception {
		String zipFileName = "sample.zip";
		
		
		ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream());
		
		int c = 1;
		String filePaths[] = {"C:\\Users\\Trevor\\Desktop\\img1.jpg","C:\\Users\\Trevor\\Desktop\\img2.jpg"};
		
		for (String fileName : filePaths) {
			//FileSystemResource resource = new FileSystemResource(fileBasePath + fileName);
		//	ZipEntry zipEntry = new ZipEntry(resource.getFilename());
		//	zipEntry.setSize(resource.contentLength());
			
			
			try{
				byte[] bytes = Files.readAllBytes(Paths.get(fileName));
			zipOut.putNextEntry(new ZipEntry(c+".jpeg"));
			
			StreamUtils.copy(bytes, zipOut);
			zipOut.closeEntry();
			c++;
			}catch(Exception e) {
				System.out.println("Skipping for "+fileName);
			}
		}
		zipOut.finish();
		zipOut.close();
		response.setStatus(HttpServletResponse.SC_OK);
		response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + zipFileName + "\"");
	}
	
	@RequestMapping(value = "/zip" ,method= RequestMethod.GET)
	public ResponseEntity zipdown( ) throws Exception {
		return empService.download();
	}
	
	

	
	}

