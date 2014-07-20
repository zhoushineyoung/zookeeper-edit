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
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.martin.zkedit.utils.CmdUtil;
import com.martin.zkedit.utils.ServletUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = {"/monitor"})
public class Monitor extends HttpServlet {

    private final static Logger logger = LoggerFactory.getLogger(Monitor.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("Monitor Action!");
        try {
            Properties globalProps = (Properties) this.getServletContext().getAttribute("globalProps");
            String zkServer = globalProps.getProperty("zkServer");
            String[] zkServerLst = zkServer.split(",");

            Map<String, Object> templateParam = new HashMap<>();
            StringBuffer stats = new StringBuffer();
            for (String zkObj : zkServerLst) {
                stats.append("<br/><hr/><br/>").append("Server: ").append(zkObj).append("<br/><hr/><br/>");
                String[] monitorZKServer = zkObj.split(":");
                stats.append(CmdUtil.INSTANCE.executeCmd("stat", monitorZKServer[0], monitorZKServer[1]));
                stats.append(CmdUtil.INSTANCE.executeCmd("envi", monitorZKServer[0], monitorZKServer[1]));
            }
            templateParam.put("stats", stats);
            ServletUtil.INSTANCE.renderHtml(request, response, templateParam, "monitor.html.ftl");

        } catch (IOException | InterruptedException | TemplateException ex) {
            ServletUtil.INSTANCE.renderError(request, response, ex.getMessage());
        }
    }
}
