import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;

public class DataTest {
	
	Geometry jd = null;

	@Before
	/* Initializes a new dataset with default values */
	public void setup(){
		jd = new Geometry();
	}

	@Test
	public void testDefaultValues(){
		
		boolean expected = true;
		assertEquals( jd.hasValidDataSet(), expected );
			
	}

	@Test
	public void testImplausiblePositiveValues(){
		
		jd.setRawParameter( Geometry.Prm.STL, 5700.0);
		assertEquals( jd.validParameters(), false );
		jd.setDefaultParameters();

		
		jd.setRawParameter( Geometry.Prm.TTL, 100000.0);
		assertEquals( jd.validParameters(), false );
		jd.setDefaultParameters();

		jd.setRawParameter( Geometry.Prm.SIGMA, 100000.0);
		assertEquals( jd.validParameters(), false );
		jd.setDefaultParameters();
		
		jd.setRawParameter( Geometry.Prm.BHS, 100.0);
		assertEquals( jd.validParameters(), false );
		jd.setDefaultParameters();

		jd.setRawParameter( Geometry.Prm.CAW, 100.0);
		assertEquals( jd.validParameters(), false );
		jd.setDefaultParameters();

		jd.setRawParameter( Geometry.Prm.CL, 50.0);
		assertEquals( jd.validParameters(), false );
		jd.setDefaultParameters();


	}

	@Test
	public void testImplausibleNegativeValues(){
		
		jd.setRawParameter( Geometry.Prm.STL, -1.0);
		assertEquals( jd.validParameters(), false );
		jd.setDefaultParameters();

		
		jd.setRawParameter( Geometry.Prm.TTL, -1.0);
		assertEquals( jd.validParameters(), false );
		jd.setDefaultParameters();

		jd.setRawParameter( Geometry.Prm.SIGMA, -1.0);
		assertEquals( jd.validParameters(), false );
		jd.setDefaultParameters();
		
		jd.setRawParameter( Geometry.Prm.BHS, -1.0);
		assertEquals( jd.validParameters(), false );
		jd.setDefaultParameters();

		jd.setRawParameter( Geometry.Prm.CAW, -1.0);
		assertEquals( jd.validParameters(), false );
		jd.setDefaultParameters();

		jd.setRawParameter( Geometry.Prm.CL, -1.0);
		assertEquals( jd.validParameters(), false );
		jd.setDefaultParameters();


	}

	@Test
	public void testInconsistentValues(){
		jd.setRawParameter( Geometry.Prm.CL, 190.0);
		jd.setRawParameter( Geometry.Prm.drop, 150.0);
		jd.setRawParameter( Geometry.Prm.WR, 340.0);
		assertEquals( jd.validParameters(), false );
		jd.setDefaultParameters();
	
		jd.setRawParameter( Geometry.Prm.ALPHA, Math.toRadians(60.0));
		jd.setRawParameter( Geometry.Prm.STL, 620.0);
		jd.setRawParameter( Geometry.Prm.TTL, 200.0);
		assertEquals( jd.validParameters(), false );
		jd.setDefaultParameters();

		jd.setRawParameter( Geometry.Prm.CSL, 250.0);
		jd.setRawParameter( Geometry.Prm.WR, 340.0);
		assertEquals( jd.validParameters(), false );
		jd.setDefaultParameters();

	}	

}
