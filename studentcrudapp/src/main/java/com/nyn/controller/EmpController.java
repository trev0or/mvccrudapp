package com.nyn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.nyn.doa.EmployeeDao;
import com.nyn.model.Employee;

@Controller
public class EmpController {
	@Autowired
	 EmployeeDao dao;
	@Autowired
	Employee employee;
	
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

}
