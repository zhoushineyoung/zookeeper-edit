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
package com.martin.zkedit.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(filterName = "filteranno", urlPatterns = "/*")
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig fc) throws ServletException {
        //Do Nothing
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain fc) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        if (!request.getRequestURI().contains("/login") && !request.getRequestURI().contains("/acd/appconfig")) {
            RequestDispatcher dispatcher;
            HttpSession session = request.getSession();
            if (session != null) {
                if (session.getAttribute("authName") == null || session.getAttribute("authRole") == null) {
                    dispatcher = request.getRequestDispatcher("/login");
                    dispatcher.forward(request, response);
                    return;
                }

            } else {
                request.setAttribute("fail_msg", "Session timed out!");
                dispatcher = request.getRequestDispatcher("/Login");
                dispatcher.forward(request, response);
                return;
            }
        }

        fc.doFilter(req, res);
    }

    @Override
    public void destroy() {
        //Do nothing
    }

}
