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
<#import "layouts/main-layout.html.ftl" as main> 
<@main.page>

<div class="container">
    <form method="post" action="/history" role="form"  class="form-horizontal">
        <div class="row-fluid">
            <div class="col-md-12 text-center">
                <h3>Top 50 Changes</h3>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-2 control-label">
                Node History
            </label>
            <div class="col-md-8">
                <input type="text" name="historyNode" value="${historyNode}" class="form-control" required>
            </div>
            <div class="col-md-2">
                <button type="submit" name="action" value="showhistory" class="btn btn-primary">Show History</button>
            </div>
        </div>

        <div class="row-fluid">
            <div class="col-md-12 text-center">
                <table class="table table-striped">  
                    <thead>  
                        <tr>  
                            <th>Id</th>  
                            <th>Date</th>  
                            <th>User</th>  
                            <th>From</th>  
                            <th>Summary</th>  
                        </tr>  
                    </thead>  
                    <tbody>
                        <#list historyLst as history>
                        <tr>  
                            <td>${history.id}</td>  
                            <td>${history.changeDate}</td>
                            <td>${history.changeUser}</td>  
                            <td>${history.changeIp}</td>  
                            <td>${history.changeSummary}</td>
                        </tr>  
                        </#list>
                    </tbody>  
                </table>  


            </div>
        </div>
    </form>
</div>

</@main.page> 