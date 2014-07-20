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
import java.util.Date;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.zookeeper.KeeperException;

import com.martin.zkedit.utils.ServletUtil;
import com.martin.zkedit.utils.ZooKeeperUtil;
import com.martin.zkedit.viewobj.LeafBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@SuppressWarnings("serial")
@WebServlet(urlPatterns = {"/export"})
public class Export extends HttpServlet {

    private final static Logger logger = LoggerFactory.getLogger(Export.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("Export Get Action!");
        try {
            Properties globalProps = (Properties) this.getServletContext().getAttribute("globalProps");
            String zkServer = globalProps.getProperty("zkServer");
            String[] zkServerLst = zkServer.split(",");

            String authRole = (String) request.getSession().getAttribute("authRole");
            if (authRole == null) {
                authRole = ZooKeeperUtil.ROLE_USER;
            }
            String zkPath = request.getParameter("zkPath");
            StringBuilder output = new StringBuilder();
            output.append("#App Config Dashboard (ACD) dump created on :").append(new Date()).append("\n");
            Set<LeafBean> leaves = ZooKeeperUtil.INSTANCE.exportTree(zkPath, ServletUtil.INSTANCE.getZookeeper(request, response, zkServerLst[0]), authRole);
            for (LeafBean leaf : leaves) {
                output.append(leaf.getPath()).append('=').append(leaf.getName()).append('=').append(ServletUtil.INSTANCE.externalizeNodeValue(new String(leaf.getValue()))).append('\n');
            }// for all leaves
            response.setContentType("text/plain");
            try (PrintWriter out = response.getWriter()) {
                out.write(output.toString());
            }

        } catch (InterruptedException | KeeperException ex) {
            ServletUtil.INSTANCE.renderError(request, response, ex.getMessage());
        }
    }
}
