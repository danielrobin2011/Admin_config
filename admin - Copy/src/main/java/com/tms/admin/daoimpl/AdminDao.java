package com.tms.admin.daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
//import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.tms.admin.dao.IAdminDao;
import com.tms.admin.model.Employee;
import com.tms.admin.model.Group;

@Repository
public class AdminDao implements IAdminDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static final String FETCH_EMPLOYEES="SELECT empId,fName,mName,lName,emailId from employee where isArchived=false";
	private static final String FETCH_GROUPS="SELECT DISTINCT e.fName,e.mName,e.lName,g.name,gd.description FROM ticketmanagementdb.group g inner join ticketmanagementdb.group_admin ga on g.id=ga.groupId inner join ticketmanagementdb.group_description gd on g.id=gd.groupId inner join ticketmanagementdb.employee_group_ref egr on egr.groupId=g.id inner join ticketmanagementdb.employee e on ga.adminId = e.empId";
	private static final String FETCH_ONE_GROUP="SELECT e.fName, e.mName, e.lName, g.name, gd.description  FROM ticketmanagementdb.group g inner join ticketmanagementdb.group_admin ga on g.id=ga.groupId inner join ticketmanagementdb.group_description gd on g.id=gd.groupId inner join employee_group_ref egr on g.id=egr.groupId inner join ticketmanagementdb.employee e on egr.empId=e.empId WHERE g.id=?";
	private static final String ADD_GROUP_NAME ="INSERT INTO `ticketmanagementdb`.`group` (`name`, `isArchived`) VALUES (?,0)";
	private static final String ADD_GROUP_DESCRIPTION = "INSERT INTO `ticketmanagementdb`.`group_description` (`groupId`, `description`,`isArchived`) VALUES (?,?,?)";
	//ga.adminId,  egr.empId,
//	private static final String ADD_GROUP_NAME = "INSERT INTO `ticketmanagementdb`.`group` (`name`, `isArchived`) VALUES (?,0)";
//	private static final String ADD_GROUP_NAME = "INSERT INTO `ticketmanagementdb`.`group` (`name`, `isArchived`) VALUES (?,0)";
	@Override
	public List<Employee> getAllEmployees() {
		return this.jdbcTemplate.query(FETCH_EMPLOYEES, new RowMapper<Employee>() {
			public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
			Employee s=new Employee();
			s.setEmpFname(rs.getString("fName"));
			s.setEmpMname(rs.getString("mName"));
			s.setEmpLname(rs.getString("lName"));
			s.setEmailId(rs.getString("emailId"));
			s.setEmpId(rs.getInt("empId"));
			return s;
			}
		});	
	}	
	
	@Override
	public List<Group> getAllGroups() {
		return this.jdbcTemplate.query(FETCH_GROUPS, new RowMapper<Group>() {
			public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
			Group s=new Group();
			//loop and append
			s.setAdminName(rs.getString("e.fName")+" "+rs.getString("e.mName")+" "+rs.getString("e.lName"));
			s.setGroupName(rs.getString("g.name"));
			s.setDescription(rs.getString("gd.description"));
			return s;
			}
		});	
	}
	
	
	@Override
	public Group getOneGroup(int id) {
		return this.jdbcTemplate.queryForObject(FETCH_ONE_GROUP, new RowMapper<Group>() {
			public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
			System.out.println("Inside getOneGroup");
			Group s=new Group();
			//loop and append
			s.setAdminName(rs.getString("e.fName")+" "+rs.getString("e.mName")+" "+rs.getString("e.lName"));
			s.setGroupName(rs.getString("g.name"));
			s.setDescription(rs.getString("gd.description"));
			System.out.println("getOneGroup parameters set");
//			for(int i=0;i<(s.getGroupEmpIds()).size();i++) {
//				List<String> temp=new ArrayList<String>();
//				temp.add(rs.getString("egr.empId"));
//				s.setGroupEmpIds(temp);
//			}
//			
//			List<Employee> inputList = new ArrayList<Employee>();
//	        for(Employee e:s.getGroupEmpIds()){
//		            tmp = e.getEmpId();
//		           inputList.add(tmp);
//		    }
//	        s.setGroupEmpIds(inputList);
			return s;
			}
		},id);
	}
	
	
	@Override
	public void addGroup(Group obj) {
		
		
			jdbcTemplate.update(ADD_GROUP_NAME, obj.getGroupName());
//			
//			String name = (String)getJdbcTemplate().queryForObject(FETCH_GROUP_ID, new Object[] { obj.getGroupName() }, String.class);
		    String FETCH_GROUP_ID	 = "SELECT id FROM ticketmanagementdb.`group` where name='"+ obj.getGroupName() + "'";
		     int group_id = jdbcTemplate.queryForObject(FETCH_GROUP_ID, int.class);
		     System.out.println(group_id);
		     
	        jdbcTemplate.update(ADD_GROUP_DESCRIPTION, group_id, obj.getDescription(),0); 
	        
//	        List<Object[]> inputList = new ArrayList<Object[]>();
//	        for(Employee e:obj.getGroupEmpIds()){
//	           Object[] tmp = {e.get(), e.getSalary(), e.getDept()};
//	           inputList.add(tmp);
//	        }
//	        jdbcTemplate.batchUpdate(query, inputList); 
	    }
	}
