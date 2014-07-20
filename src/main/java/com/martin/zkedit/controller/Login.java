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
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.martin.zkedit.utils.LdapAuth;
import com.martin.zkedit.utils.ServletUtil;
import com.martin.zkedit.utils.ZooKeeperUtil;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = {"/login"})
public class Login extends HttpServlet {

    private final static Logger logger = LoggerFactory.getLogger(Login.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("Login Action!");
        try {
            Properties globalProps = (Properties) getServletContext().getAttribute("globalProps");
            Map<String, Object> templateParam = new HashMap<>();
            templateParam.put("uptime", globalProps.getProperty("uptime"));
            templateParam.put("loginMessage", globalProps.getProperty("loginMessage"));
            ServletUtil.INSTANCE.renderHtml(request, response, templateParam, "login.html.ftl");
        } catch (TemplateException ex) {
            ServletUtil.INSTANCE.renderError(request, response, ex.getMessage());
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("Login Post Action!");
        try {
            Properties globalProps = (Properties) getServletContext().getAttribute("globalProps");
            Map<String, Object> templateParam = new HashMap<>();
            HttpSession session = request.getSession(true);
            session.setMaxInactiveInterval(Integer.valueOf(globalProps.getProperty("sessionTimeout")));
            //TODO: Implement custom authentication logic if required.
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String role = null;
            Boolean authenticated = false;
            //if ldap is provided then it overrides roleset.
            if (globalProps.getProperty("ldapAuth").equals("true")) {
                authenticated = new LdapAuth().authenticateUser(globalProps.getProperty("ldapUrl"), username, password, globalProps.getProperty("ldapDomain"));
                if (authenticated) {
                    JSONArray jsonRoleSet = (JSONArray) ((JSONObject) new JSONParser().parse(globalProps.getProperty("ldapRoleSet"))).get("users");
                    for (Iterator it = jsonRoleSet.iterator(); it.hasNext();) {
                        JSONObject jsonUser = (JSONObject) it.next();
                        if (jsonUser.get("username") != null && jsonUser.get("username").equals("*")) {
                            role = (String) jsonUser.get("role");
                        }
                        if (jsonUser.get("username") != null && jsonUser.get("username").equals(username)) {
                            role = (String) jsonUser.get("role");
                        }
                    }
                    if (role == null) {
                        role = ZooKeeperUtil.ROLE_USER;
                    }

                }
            } else {
                JSONArray jsonRoleSet = (JSONArray) ((JSONObject) new JSONParser().parse(globalProps.getProperty("userSet"))).get("users");
                for (Iterator it = jsonRoleSet.iterator(); it.hasNext();) {
                    JSONObject jsonUser = (JSONObject) it.next();
                    if (jsonUser.get("username").equals(username) && jsonUser.get("password").equals(password)) {
                        authenticated = true;
                        role = (String) jsonUser.get("role");
                    }
                }
            }
            if (authenticated) {
                logger.info("Login successfull: " + username);
                session.setAttribute("authName", username);
                session.setAttribute("authRole", role);
                response.sendRedirect("/home");
            } else {
                session.setAttribute("flashMsg", "Invalid Login");
                ServletUtil.INSTANCE.renderHtml(request, response, templateParam, "login.html.ftl");
            }

        } catch (ParseException | TemplateException ex) {
            ServletUtil.INSTANCE.renderError(request, response, ex.getMessage());
        }
    }
}
