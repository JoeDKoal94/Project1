package TRMSTest;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import TRMS.dao.TrmsDaoPostgres;
import TRMS.pojos.Employee;
import TRMS.pojos.Form;
import TRMS.util.ConnectionUtil;

@RunWith(MockitoJUnitRunner.class)
public class TrmsDaoPostgresTest {
	
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
	private Form f;
	
	
	private PreparedStatement spy;
	
	private Connection realConnection;

	
	private void initStmtHelper(String sql) throws SQLException{
		pstmt = realConnection.prepareStatement(sql);
		spy = Mockito.spy(pstmt);
		when(connUtil.createConnection()).thenReturn(connection);
		when(connection.prepareStatement(sql)).thenReturn(spy);
	}
	
	@Before
	public void setUp() throws Exception{
		assertNotNull(ds);
		when(connection.prepareStatement(any(String.class))).thenReturn(pstmt);
		when(ds.getConnection()).thenReturn(connection);
		
		f.setEventDate(LocalDate.parse("2020-12-10"));
		f.setEventTime(LocalTime.parse("10:00"));
		f.setEventLocation("Texas");
		f.setDescription("test");
		f.setGradingFormat("Standard");
		f.setTypeOfEvent("University Courses");
		f.setCost(150);
		f.setFormNumber(90);
		f.setProjectedAmount(120);
		f.setUrgent(false);
		f.setEmployeeId(1);
		f.setTimePosted(LocalTime.parse("08:00"));
		
		when(rs.first()).thenReturn(true);
		when(rs.getInt(1)).thenReturn(90);
		when(rs.getInt(2)).thenReturn(1);
		when(rs.getDate(3)).thenReturn(java.sql.Date.valueOf(f.getEventDate()));
		when(rs.getTime(4)).thenReturn(java.sql.Time.valueOf(f.getEventTime()));
		when(rs.getString(5)).thenReturn(f.getEventLocation());
		when(rs.getString(6)).thenReturn(f.getDescription());
		when(rs.getString(7)).thenReturn(f.getGradingFormat());
		when(rs.getString(8)).thenReturn(f.getTypeOfEvent());
		when(rs.getDouble(9)).thenReturn(f.getProjectedAmount());
		when(rs.getBoolean(10)).thenReturn(false);
		when(rs.getBoolean(11)).thenReturn(false);
		when(rs.getDouble(12)).thenReturn(f.getCost());
		when(rs.getTime(13)).thenReturn(java.sql.Time.valueOf(f.getTimePosted()));
		when(pstmt.executeQuery()).thenReturn(rs);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void nullCreateThrowsException() {
		new TrmsDaoPostgres(ds).createForm(null);
	}
	

	
	@Test
	public void createFormAndRetrieve() {
		TrmsDaoPostgres dao = new TrmsDaoPostgres(ds);
		dao.createForm(f);
		Form z = dao.retrieveForm(90);
		assertEquals(f, z);
	}
	
	@Test
	public void createForm() {
		new TrmsDaoPostgres(ds).createForm(f);
	}
	
	@Test
	public void updateForm() {
		TrmsDaoPostgres dao = new TrmsDaoPostgres(ds);
		dao.createForm(f);
		dao.updateForm(90, "event_location", "Florida");
		Form z = dao.retrieveForm(90);
		assertEquals("Florida", z.getEventLocation());
	}
	
	@Test
	public void deleteForm() throws SQLException {
		TrmsDaoPostgres dao = new TrmsDaoPostgres(ds);
		dao.createForm(f);
		dao.deleteForm(90);
		Form z = dao.retrieveForm(90);
		assertEquals(null, z);
	}
	
	

}
