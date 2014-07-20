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

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.zookeeper.ZooKeeper;

import com.martin.zkedit.utils.ServletUtil;
import com.martin.zkedit.utils.ZooKeeperUtil;
import com.martin.zkedit.viewobj.LeafBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = {"/acd/appconfig"})
public class RestAccess extends HttpServlet {

    private final static Logger logger = LoggerFactory.getLogger(RestAccess.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("Rest Action!");
        try {
            Properties globalProps = (Properties) this.getServletContext().getAttribute("globalProps");
            String zkServer = globalProps.getProperty("zkServer");
            String[] zkServerLst = zkServer.split(",");
            String accessRole = ZooKeeperUtil.ROLE_USER;
            if ((globalProps.getProperty("blockPwdOverRest") != null) && (Boolean.valueOf(globalProps.getProperty("blockPwdOverRest")) == Boolean.FALSE)) {
                accessRole = ZooKeeperUtil.ROLE_ADMIN;
            }

            String clusterName = request.getParameter("cluster");
            String appName = request.getParameter("app");
            String hostName = request.getParameter("host");
            String[] propNames = request.getParameterValues("propNames");
            String propValue = "";

            if (hostName == null) {
                hostName = ServletUtil.INSTANCE.getRemoteAddr(request);
            }
            ZooKeeper zk = ServletUtil.INSTANCE.getZookeeper(request, response, zkServerLst[0]);
            //get the path of the hosts entry.
            LeafBean hostsNode = ZooKeeperUtil.INSTANCE.getNodeValue(zk, ZooKeeperUtil.ZK_ROOT, ZooKeeperUtil.ZK_ROOT + "/" + hostName, hostName, accessRole);
            StringBuilder lookupPath = new StringBuilder(hostsNode.getStrValue());
            //You specify a cluster or an app name to group.
            if (clusterName != null) {
                lookupPath.append("/").append(clusterName).append("/").append(hostName);
            }
            if (appName != null) {
                lookupPath.append("/").append(appName).append("/").append(hostName);
            }

            StringBuilder resultOut = new StringBuilder();
            LeafBean propertyNode;
            String[] pathElements = lookupPath.toString().split("/");

            for (String propName : propNames) {
                StringBuffer concatPath = new StringBuffer();
                for (String path : pathElements) {
                    concatPath.append(path).append("/");
                    propertyNode = ZooKeeperUtil.INSTANCE.getNodeValue(zk, concatPath.toString(), concatPath + propName, propName, accessRole);
                    if (propertyNode != null) {
                        propValue = propertyNode.getStrValue();
                    }
                }
                resultOut.append(propName).append("=").append(propValue).append("\n");
            }

            response.setContentType("text/plain");
            try (PrintWriter out = response.getWriter()) {
                out.write(resultOut.toString());
            }

        } catch (Exception ex) {
            ServletUtil.INSTANCE.renderError(request, response, ex.getMessage());
        }

    }
}
