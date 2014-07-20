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
<#import "layouts/login-layout.html.ftl" as main> 
<@main.page>

<div class="container">

    <form method="post" action="/login">
        <div class="row">
            <div class="col-md-4">
                &nbsp;
            </div>
            <div class="col-md-4">
                <h2 class="text-center">Login</h2>
                <input type="text" name="username" class="form-control" placeholder="Username" required autofocus>
                <br/>
                <input type="password" name="password" class="form-control" placeholder="Password" required>
                <br/>
                <input type="submit" name="action" value="Sign In" class="btn btn-lg btn-primary btn-block"/>
                <br/>
                <#if loginMessage??>
                <p class="text-success text-center">${loginMessage}</p>
                <br/>
                </#if> 
                
                <#if flashMsg??>
                <p class="text-danger text-center">${flashMsg}</p>
                </#if> 
            </div>
            <div class="col-md-4">
                &nbsp;
            </div>
        </div> 

    </form>

</div> <!-- /container -->
</@main.page> 