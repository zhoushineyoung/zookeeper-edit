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
package com.martin.zkedit.controller;

import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.martin.zkedit.dao.Dao;
import com.martin.zkedit.domain.History;
import com.martin.zkedit.utils.ServletUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = {"/history"})
public class ChangeLog extends HttpServlet {

    private final static Logger logger = LoggerFactory.getLogger(ChangeLog.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("History Get Action!");
        try {
            Properties globalProps = (Properties) this.getServletContext().getAttribute("globalProps");
            Dao dao = new Dao(globalProps);
            Map<String, Object> templateParam = new HashMap<>();
            List<History> historyLst = dao.fetchHistoryRecords();
            templateParam.put("historyLst", historyLst);
            templateParam.put("historyNode", "");
            ServletUtil.INSTANCE.renderHtml(request, response, templateParam, "history.html.ftl");
        } catch (TemplateException ex) {
            ServletUtil.INSTANCE.renderError(request, response, ex.getMessage());
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("History Post Action!");
        try {
            Properties globalProps = (Properties) this.getServletContext().getAttribute("globalProps");
            Dao dao = new Dao(globalProps);
            Map<String, Object> templateParam = new HashMap<>();
            String action = request.getParameter("action");
            List<History> historyLst;
            if (action.equals("showhistory")) {

                String historyNode = request.getParameter("historyNode");
                historyLst = dao.fetchHistoryRecordsByNode("%" + historyNode + "%");
                templateParam.put("historyLst", historyLst);
                templateParam.put("historyNode", historyNode);
                ServletUtil.INSTANCE.renderHtml(request, response, templateParam, "history.html.ftl");

            } else {
                response.sendRedirect("/history");
            }
        } catch (TemplateException ex) {
            ServletUtil.INSTANCE.renderError(request, response, ex.getMessage());
        }
    }
}
