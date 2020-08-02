package com.nyn.doa;

import org.springframework.jdbc.core.JdbcTemplate;

public class EmployeeDao {
	
	private JdbcTemplate jdbcTemplate;
	

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}



	public int saveEmp(int id, String name, float salary) {
		
		String sql = "insert into employee values('"+id+"','"+name+"','"+salary+"')";
		return jdbcTemplate.update(sql);
	}
	
	public int deleteEmp(int id) {
		String sql = "delete from employee where id  = '"+id+"'";
		
		return jdbcTemplate.update(sql);
	}

}
