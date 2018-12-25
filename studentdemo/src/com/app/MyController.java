package com.app;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;;

@Controller
public class MyController 
{
	
	@Autowired
	HibernateTemplate hibernatetemplate;

	@Autowired
	JdbcTemplate jdbcTemplate; 
	
	@RequestMapping("/")
public  String indexPage()
	{
		return "login";
	}
	

	@RequestMapping("/userLogin")
	public String userLogin(HttpServletRequest request)
	{
		
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		String viewName="";
		Map<String,Object> map=jdbcTemplate.queryForMap("select * from users where userid=? and password=?",new String[]{username,password});
		if(map.size()>0){
			viewName="main";
			
		}
		else{
			viewName="error";
			
		}
		
		return viewName;
			
		
	}
	@RequestMapping("/insert")
	public String insertStudentRecord()
	{
		
		return "insertStudent";
		
	}
	@RequestMapping("/insertUser")
public String insertuser(HttpServletRequest request)
{
	String name=request.getParameter("name");
	String college=request.getParameter("college");
	String department=request.getParameter("department");
	String address=request.getParameter("address");
	
	student student=new student();
	student.setName(name);
	student.setCollege(college);
	student.setDepartment(department);
	student.setAddress(address);
	hibernatetemplate.persist(student);
	return "main";
}
	@RequestMapping("/show")
	public String showStudentRecord()
	{
		return "showstudent";
	}
	@RequestMapping("/showUser")
	public ModelAndView getAllStudent()
	{
    List stu= hibernatetemplate.find("select * from stuinfo");
    ModelAndView mav=new ModelAndView();
    mav.setViewName("List");
    mav.addObject("List",stu);
    return mav;
	}
	@RequestMapping("delete")
	public String deleteStudentRecord()
	{
	   return "deletestudent";
	}
	@RequestMapping("/deleteUser")
	public String deleteUser(HttpServletRequest request)
	{
		String viewName="";
		String name=request.getParameter("name");
		Map<String,Object> map=jdbcTemplate.queryForMap("delete * from stuinfo where name=?" ,new String[]{name});
		if(map.size()>0){
			viewName="main";
			
		}
		else{
			viewName="error";
			
		}
		
		return viewName;
			
	
	
	
	
	
	
}}