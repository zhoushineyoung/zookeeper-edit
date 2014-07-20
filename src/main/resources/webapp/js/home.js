/**
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
*/
$(document).ready(function() {

    $("#importFileView").click(function() {
        var scmServer = $("#scmServer").val();
        var scmFilePath = $("#scmFilePath").val();
        var scmFileRevision = $("#scmFileRevision").val();
        $("#importFileView").attr('href', scmServer + scmFileRevision + "@" + scmFilePath);

    });

    //Class based selector 
    $(".href-select").click(function() {
        var propName = $(this).text();
        var propVal = $(this).attr('itemprop');
        $("#newProperty").attr('readonly', true);
        $("#newProperty").val(propName);
        $("#newValue").val(propVal);

        $("#savePropertyBtn").hide();
        $("#updatePropertyBtn").show();
    });

    //Id based selector
    $("#addPropertyBtn").click(function() {
        $("#newProperty").attr('readonly', false);
        $("#updatePropertyBtn").hide();
        $("#savePropertyBtn").show();
    });


}); 