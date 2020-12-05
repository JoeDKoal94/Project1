package TRMSTest;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import TRMS.dao.TrmsDaoPostgres;
import TRMS.pojos.Employee;
import TRMS.pojos.Form;
import TRMS.util.ConnectionUtil;

public class TrmsDaoPostgresTestEmployee {

public TrmsDaoPostgres testDao = new TrmsDaoPostgres();
	
	@Mock
	private DataSource ds;
	
	@Mock
	private ConnectionUtil connUtil;
	
	@Mock
	private Connection connection;
	
	@Mock
	private ResultSet rs;
	
	@Mock
	private PreparedStatement pstmt;
	
	private Employee e;
	
	
	private PreparedStatement spy;
	
	private Connection realConnection;

	
	
	@Before
	public void setUp() throws Exception{
		assertNotNull(ds);
		when(connection.prepareStatement(any(String.class))).thenReturn(pstmt);
		when(ds.getConnection()).thenReturn(connection);
		
		e.setFirstName("Chuck");
		e.setLastName("Testa");
		e.setAddress("123 Lane");
		e.setEmployeeId(1);
		e.setAuthority("Employee");
		e.setAvailableAmount(1000);
		e.setCity("Tampa");
		e.setEmailAdd("123@gmail.com");
		e.setPassword("password");
		e.setUsername("username");
		e.setPhoneNumber("1234567890");
		e.setPostalCode("12345");
		
		when(rs.first()).thenReturn(true);
		when(rs.getInt(1)).thenReturn(1);
		when(rs.getString(2)).thenReturn(e.getFirstName());
		when(rs.getString(3)).thenReturn(e.getLastName());
		when(rs.getString(4)).thenReturn(e.getPhoneNumber());
		when(rs.getString(5)).thenReturn(e.getAddress());
		when(rs.getDouble(6)).thenReturn(e.getAvailableAmount());
		when(rs.getString(7)).thenReturn(e.getAuthority());
		when(rs.getString(8)).thenReturn(e.getCity());
		when(rs.getString(9)).thenReturn(e.getState());
		when(rs.getString(10)).thenReturn(e.getPostalCode());
		when(rs.getString(11)).thenReturn(e.getEmailAdd());
		when(rs.getString(12)).thenReturn(e.getUsername());
		when(rs.getString(13)).thenReturn(e.getPassword());
		when(pstmt.executeQuery()).thenReturn(rs);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void nullCreateThrowsException() {
		new TrmsDaoPostgres(ds).createForm(null);
	}
	
	
	@Test
	public void createEmployee() {
		new TrmsDaoPostgres(ds).createEmployee(e);
	}
	
	@Test
	public void createEmployeeandRetrieve() {
		TrmsDaoPostgres dao = new TrmsDaoPostgres(ds);
		dao.createEmployee(e);
		Employee r = dao.retrieveEmployee("username");
		assertEquals(e, r);
	}
	
	
}
