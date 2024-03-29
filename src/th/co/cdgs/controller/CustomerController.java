package th.co.cdgs.controller;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import th.co.cdgs.bean.CustomerDto;

@Path("customer")
public class CustomerController {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCustomer() {
		List<CustomerDto> list = new ArrayList<>();
		ResultSet rs = null;
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/workshop","root","p@ssw0rd");
			pst = conn.prepareStatement(					"Select customer_id,"+
															"CONCAT(first_name,' ',last_name) as full_name,"+
															"address,tel,email From customer");
			rs = pst.executeQuery();
			CustomerDto cust = null;
			while(rs.next()) {
				cust = new CustomerDto();
				cust.setCustomerId(rs.getLong("customer_id"));
				cust.setFullName(rs.getString("full_name"));
				cust.setAddress(rs.getString("address"));
				cust.setTel(rs.getString("tel"));
				cust.setEmail(rs.getString("email"));
				list.add(cust);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if( conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if( rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if( pst != null)
				try {
					pst.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return Response.ok().entity(list).build();
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createCustomer(CustomerDto customer) {
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/workshop","root","p@ssw0rd");
			pst = conn.prepareStatement("insert into customer (first_name,last_name,address,tel,email) value(?,?,?,?,?)");
			int index = 1;
			pst.setString(index++, customer.getFirstname());
			pst.setString(index++, customer.getLastname());
			pst.setString(index++, customer.getAddress());
			pst.setString(index++, customer.getTel());
			pst.setString(index++, customer.getEmail());
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if( conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if( pst != null)
				try {
					pst.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return Response.ok().entity(customer).build();
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateCustomer(CustomerDto customer) {
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/workshop","root","p@ssw0rd");
			pst = conn.prepareStatement("UPDATE customer  SET\r\n" + 
					"first_name  = ? ,\r\n" + 
					"last_name = ? ,\r\n" + 
					"address = ? ,\r\n" + 
					"tel= ?  ,\r\n" + 
					"email = ?\r\n" + 
					"WHERE customer_Id = ?");
			int index = 1;
			pst.setString(index++, customer.getFirstname());
			pst.setString(index++, customer.getLastname());
			pst.setString(index++, customer.getAddress());
			pst.setString(index++, customer.getTel());
			pst.setString(index++, customer.getEmail());
			pst.setLong(index++, customer.getCustomerId());
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if( conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if( pst != null)
				try {
					pst.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return Response.ok().entity(customer).build();
	}
	
	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteCustomer(@PathParam("id") Long customerId) {
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/workshop","root","p@ssw0rd");
			pst = conn.prepareStatement("delete from customer where customer_id = ?");
			int index = 1;
			pst.setLong(index++, customerId);
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if( conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if( pst != null)
				try {
					pst.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return Response.ok().entity("Delete complete").build();
		
	}
}
