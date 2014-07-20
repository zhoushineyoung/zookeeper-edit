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
package com.martin.zkedit.dao;

import com.martin.zkedit.domain.History;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.flywaydb.core.Flyway;
import org.javalite.activejdbc.Base;
import org.slf4j.LoggerFactory;

public class Dao {

    private final static Integer FETCH_LIMIT = 50;
    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(Dao.class);
    private final Properties globalProps;

    public Dao(Properties globalProps) {
        this.globalProps = globalProps;
    }

    public void open() {
    	String driver = globalProps.getProperty("jdbcClass");
    	String jdbcUrl = globalProps.getProperty("jdbcUrl");
    	String user = globalProps.getProperty("jdbcUser");
    	String pass = globalProps.getProperty("jdbcPwd");
        Base.open(driver, jdbcUrl, user, pass);
    }

    public void close() {
        Base.close();
    }

    public void checkNCreate() {
        Flyway flyway = new Flyway();
        // flyway.setDataSource(globalProps.getProperty("jdbcUrl"), globalProps.getProperty("jdbcUser"), globalProps.getProperty("jdbcPwd"));
        OpenConnectionCountDriverDataSource dataSource = OpenConnectionCountDriverDataSource.getInstance(Thread.currentThread().getContextClassLoader(), null, globalProps.getProperty("jdbcUrl"), globalProps.getProperty("jdbcUser"), globalProps.getProperty("jdbcPwd"));
        flyway.setDataSource(dataSource);
        flyway.setLocations("db/migration");
        //Will wipe db each time. Avoid this in prod.
        if (globalProps.getProperty("env").equals("dev")) {
            flyway.clean();
        }
        //Remove the above line if deploying to prod.
        flyway.migrate();
    }

    public List<History> fetchHistoryRecords() {
        this.open();
        List<History> history = History.findAll().orderBy("ID desc").limit((int)FETCH_LIMIT);
        history.size();
        this.close();
        return history;

    }

    public List<History> fetchHistoryRecordsByNode(String historyNode) {
        this.open();
        // List<History> history = History.where("CHANGE_SUMMARY like ?", historyNode).orderBy("ID desc").limit(FETCH_LIMIT);
        List<History> history = History.where("CHANGE_SUMMARY like ?", historyNode).orderBy("ID desc").limit((int)FETCH_LIMIT);
        history.size();
        this.close();
        return history;
    }

    public void insertHistory(String user, String ipAddress, String summary) {
        try {
            this.open();
            //To avoid errors due to truncation.
            if (summary.length() >= 500) {
                summary = summary.substring(0, 500);
            }
            History history = new History();
            history.setChangeUser(user);
            history.setChangeIp(ipAddress);
            history.setChangeSummary(summary);
            history.setChangeDate(new Date());
            history.save();
            this.close();
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }

    }
}
