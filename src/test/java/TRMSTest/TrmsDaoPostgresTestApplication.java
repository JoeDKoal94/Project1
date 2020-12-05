package TRMSTest;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.junit.Before;
import org.mockito.Mock;

import TRMS.dao.TrmsDaoPostgres;
import TRMS.pojos.ApplicationStatus;
import TRMS.pojos.Employee;
import TRMS.util.ConnectionUtil;

public class TrmsDaoPostgresTestApplication {

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
	
	private ApplicationStatus s;
	
	
	private PreparedStatement spy;
	
	private Connection realConnection;

	
	
	@Before
	public void setUp() throws Exception{
		assertNotNull(ds);
		when(connection.prepareStatement(any(String.class))).thenReturn(pstmt);
		when(ds.getConnection()).thenReturn(connection);
		
		//s.set
		
		
	}
	
	
	public TrmsDaoPostgresTestApplication() {
		// TODO Auto-generated constructor stub
	}

}
