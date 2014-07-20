<!--
#
# Copyright (c) 2014 zhoushineyoung. All Rights Reserved.
# 
# Licensed under the The MIT License (MIT); 
# 
# THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
# IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
# FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
# AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
# LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
# OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
# SOFTWARE.
#
-->
<#macro page>
<html>
    <head>
        <#include "../common/include.html.ftl"/>
    </head>
    <body>
        <div id="wrap">
            <#include "../common/login-menubar.html.ftl"/>
            <!-- Content starts -->
            <#nested>
            <!-- Content ends -->
        </div>    
        <#include "../common/footer.html.ftl"/>
    </body>
</html>
</#macro>

