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
<!-- Menu Bar -->
<div class="navbar navbar-default navbar-fixed-top" role="navigation">
    <div class="container">

        <div class="navbar-header">
            <a href="/home"><img src="images/zookeeper.png"/></a>
            &nbsp;&nbsp;
        </div>
        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <#if displayPath??>
                <!-- <li><a href="/home?zkPath=/appconfig/hosts&navigate=true">Hosts</a></li> -->
                <li><a href="/home?zkPath=/&navigate=true">Root</a></li>

                <#if authRole?? && authRole == 'ADMIN' >
                <li><a href="#" data-toggle="modal" data-target="#addNodeModal">Add Node</a></li>
                <li><a href="#" data-toggle="modal" data-target="#addPropertyModal" id="addPropertyBtn">Add Property</a></li>
                <li><a href="#" data-toggle="modal" data-target="#deleteModal">Delete</a></li>
                </#if> 

                <li><a href="#" data-toggle="modal" data-target="#searchModal">Search</a></li>
                <li><a href="/history">History</a></li>

                <#else>
                <li><a href="/">Root</a></li>
                </#if> 

            </ul>
            <ul class="nav navbar-nav navbar-right">
                <#if authName??>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <span class="glyphicon glyphicon-user"></span>&nbsp;${authName}&nbsp;<b class="caret"></b>&nbsp;</a>
                    <ul class="dropdown-menu">
                        <li><a href="/logout">Logout</a></li>
                    </ul>
                </li>
                <#else>
                <li><a href="/login">Login</a></li>
                </#if> 

            </ul>
        </div>

    </div>
</div>


