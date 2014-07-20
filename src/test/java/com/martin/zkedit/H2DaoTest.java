/**
 *
 * Copyright (c) 2014 zhoushineyoung. All Rights Reserved.
 *
 * Licensed under the The MIT License (MIT); 
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */
package com.martin.zkedit;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationVersion;
import org.flywaydb.core.internal.dbsupport.DbSupport;
import org.flywaydb.core.internal.dbsupport.DbSupportFactory;
import org.flywaydb.core.internal.dbsupport.JdbcTemplate;
import org.flywaydb.core.internal.dbsupport.Schema;
import org.flywaydb.core.internal.dbsupport.h2.H2DbSupport;
import org.flywaydb.core.internal.util.jdbc.DriverDataSource;
import org.junit.Test;

import com.martin.zkedit.dao.OpenConnectionCountDriverDataSource;

public class H2DaoTest {

	@Test
	public void multipleSetDataSourceCalls() throws Exception {
		DriverDataSource dataSource1 = new DriverDataSource(Thread.currentThread().getContextClassLoader(), null,
				"jdbc:h2:mem:flyway_db_1;DB_CLOSE_DELAY=-1", "sa", "");

		DriverDataSource dataSource2 = new DriverDataSource(Thread.currentThread().getContextClassLoader(), null,
				"jdbc:h2:mem:flyway_db_2;DB_CLOSE_DELAY=-1", "sa", "");

		Connection connection1 = dataSource1.getConnection();
		Connection connection2 = dataSource2.getConnection();

		Schema schema1 = new H2DbSupport(connection1).getSchema("PUBLIC");
		Schema schema2 = new H2DbSupport(connection2).getSchema("PUBLIC");

		assertTrue(schema1.empty());
		assertTrue(schema2.empty());

		Flyway flyway = new Flyway();

		flyway.setDataSource(dataSource1);
		flyway.setDataSource(dataSource2);

		flyway.setLocations("migration/sql");
		flyway.migrate();

		System.out.println(Arrays.toString(flyway.getSchemas()));
		
		assertTrue(schema1.empty());
		assertFalse(schema2.empty());

		connection1.close();
		connection2.close();
	}
	
	@Test
    public void dollarQuotedString() throws SQLException {
    	OpenConnectionCountDriverDataSource dataSource = OpenConnectionCountDriverDataSource.getInstance(null);

		DbSupport dbSupport = DbSupportFactory.createDbSupport(dataSource.getConnection(), true);
    	JdbcTemplate jdbcTemplate = dbSupport.getJdbcTemplate();
    	
    	System.out.println(dataSource.getOpenConnectionCount());
    	
    	Flyway flyway = new Flyway();
    	flyway.setDataSource(dataSource);
    	flyway.setLocations("migration/sql/h2");
        flyway.migrate();

        MigrationVersion version = flyway.info().current().getVersion();
        System.out.println(version.toString());
        System.out.println(flyway.info().current().getDescription());
        // 'Mr. Semicolon+Linebreak;\nanother line'
        jdbcTemplate.execute("INSERT INTO test_user (name) VALUES (?)", "martin.zhou");
        List<String> strings = jdbcTemplate.query("select name from test_user", null);
        if (null != strings && strings.size() > 0) {
        	for (String string : strings) {
				System.out.println("string = " + string);
			}
		}else {
			System.out.println("no data.");
		}
        System.out.println(jdbcTemplate.queryForString("select name from test_user where name like '%line'''"));
    }
	
	@Test
    public void noConnectionLeak() {
        OpenConnectionCountDriverDataSource dataSource = OpenConnectionCountDriverDataSource.getInstance(null);

        assertEquals(0, dataSource.getOpenConnectionCount());
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.setLocations("migration/sql");
        flyway.clean();
        assertEquals(0, dataSource.getOpenConnectionCount());
        assertEquals(4, flyway.migrate());
        assertEquals(0, dataSource.getOpenConnectionCount());
    }

	@Test
	public void connectionCount() throws Exception {
		OpenConnectionCountDriverDataSource dataSource = OpenConnectionCountDriverDataSource.getInstance(null);

		assertEquals(0, dataSource.getOpenConnectionCount());
		Connection connection = dataSource.getConnection();
		assertEquals(1, dataSource.getOpenConnectionCount());
		connection.close();
		assertEquals(0, dataSource.getOpenConnectionCount());

		Connection connection2 = dataSource.getConnection();
		assertEquals(1, dataSource.getOpenConnectionCount());
		Connection connection3 = dataSource.getConnection();
		assertEquals(2, dataSource.getOpenConnectionCount());
		connection2.close();
		assertEquals(1, dataSource.getOpenConnectionCount());
		connection3.close();
		assertEquals(0, dataSource.getOpenConnectionCount());
	}
}
