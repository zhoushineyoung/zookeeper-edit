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
package com.martin.zkedit.domain;

import java.util.Date;

import org.javalite.activejdbc.Model;

public class History extends Model {

    private Long id;
    private String changeUser;
    private Date changeDate;
    private String changeSummary;
    private String changeIp;

    @Override
    public Long getId() {
        this.id = super.getLong("ID");
        return id;
    }

    public void setId(Long id) {
        super.setLong("ID", id);
    }

    public String getChangeUser() {
        this.changeUser = super.getString("CHANGE_USER");
        return changeUser;
    }

    public void setChangeUser(String changeUser) {
        super.setString("CHANGE_USER", changeUser);
    }

    public Date getChangeDate() {
        this.changeDate = super.getTimestamp("CHANGE_DATE");
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        super.setTimestamp("CHANGE_DATE", changeDate);
    }

    public String getChangeSummary() {
        this.changeSummary = super.getString("CHANGE_SUMMARY");
        return changeSummary;
    }

    public void setChangeSummary(String changeSummary) {
        super.setString("CHANGE_SUMMARY", changeSummary);
    }

    public String getChangeIp() {
        this.changeIp = super.getString("CHANGE_IP");
        return changeIp;
    }

    public void setChangeIp(String changeIp) {
        super.setString("CHANGE_IP", changeIp);
    }

}
