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
<!-- Footer starts -->
<div class="footer">
    <div class="container">
        <div class="row-fluid">
            <div class="col-md-4 text-left">
                <p class="text-muted credit">Powered by: <a href="http://www.eclipse.org/jetty/" target="_blank">Jetty</a></p>
            </div>
            <div class="col-md-4 text-center">
                <#if uptime??>
                <p class="text-muted credit">
                    Started On ${uptime}
                </p>
                </#if>
            </div>
            <div class="col-md-4 text-right">
                <p class="text-muted credit"><a href="https://github.com/zhoushineyoung/zookeeper-edit/issues" target="_blank">Bugs</a></p>
            </div>
        </div>
    </div>
</div>
<!-- Footer ends -->
