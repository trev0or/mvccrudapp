package com.nyn.doa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.nyn.model.Employee;

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
	
	public List<Employee> getEmployees(){
		return jdbcTemplate.query("select * from employee", new RowMapper<Employee>() {

			public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				Employee e  = new Employee();
				e.setId(rs.getInt(1));
				e.setName(rs.getString(2));
				e.setSalary(rs.getFloat(3));
				return e;
			}
			
		});
	}

}
