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
    <div class="row-fluid">
        <div class="col-md-12 text-center">
            <h3>Search Results</h3>
        </div>
    </div>
    <div class="row-fluid">
        <div class="col-md-12 text-center">


            <table class="table table-striped table-bordered wrap-table">
                <thead>
                    <tr>
                        <th>Path</th>
                        <th>Name</th>
                        <th>Value</th>
                    </tr>
                </thead>
                <tbody>
                    <#list searchResult as leaf>
                    <tr>
                        <td>
                            <a href="/home?zkPath=${leaf.path}">${leaf.path}</a> 
                        </td>
                        <td>${leaf.name}</td>
                        <td>${leaf.strValue}</td>
                    </tr>
                    </#list>
                </tbody>
            </table>

        </div>
    </div>
</div>
</@main.page> 