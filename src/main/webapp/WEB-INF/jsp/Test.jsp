<!DOCTYPE html>

<html>
<head>

    <style type="text/css">
        .cssInputBox {
            width: 100%;
            padding-right: 4px;
        }
		
        .cssInputBoxSmall {
            width: 30%;
            padding-right: 4px;
        }		

        .cssInputBtn {
        }

        .csstblUpload {
            border: 1px solid gray;
            font: normal 12px arial,verdana;
            background-color: #efefef;
        }

            .csstblUpload td {
                padding-right: 10px;
                padding-left: 10px;
                vertical-align: middle;
            }

        .cssUploadHeader {
            text-align: center;
            background-color: #d3d3d3;
            border-bottom: 1px solid gray;
            font: bold 13px arial,verdana;
            padding-bottom: 4px;
            padding-top: 4px;
        }

        .cssHR {
            color: #d0d0d0;
            background-color: #d0d0d0;
            border: none;
            height: 1px;
        }
    </style>
    <script type="application/javascript" src="<%=request.getContextPath()%>/resources/static/js/eViewer(HTML5).Toolkit_dev.js"></script>
    <script src="<%=request.getContextPath()%>/resources/static/js/eViewer(HTML5).js"></script>
    <script src="<%=request.getContextPath()%>/resources/static/js/jquery-1.4.2.min.js"></script>
    <script src="<%=request.getContextPath()%>/resources/static/js/mstspec.js"></script>
    
<script>

$('#i_file').change( function(event) {
var tmppath = URL.createObjectURL(event.target.files[0]);
    $("img").fadeIn("fast").attr('src',tmppath);       
});


function uploadAndViewDocument(){

	var objViewer = new eViewer(); 
               
	var docUId = objViewer.uploadDocument(document
				.getElementById("viewerURL").value, document
				.getElementById("documentUrl").value,null, true);
	
	var url = objViewer.viewDocument(document
				.getElementById("viewerURL").value, docUId,
						null, "admin", false, true,false,"",false);
	
	document.getElementById("ieViewerFrame").src = url;
	
	
}

</script>    
    
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Test Case For Toolkit</title>

<script>

	
</script>


</head>

<body onload="">
    <form>
        <div>
            <table cellpadding="0" cellspacing="0" border="0" width="100%">
                <tr>
                    <td valign="top" align="left" colspan="4">
                        <table class="csstblUpload" border="0" cellpadding="0" cellspacing="0">

                            <tr style="width=1%;">
                                <td class="cssUploadHeader" align="center">Viewer Sample</td>
                            </tr>
                            
                            
                            <tr>
                                <td align="center">
                                    <br />
                                    Document URL :
                                    <input type="text" id="documentUrl" name="txtDocumentURL" class="cssInputBox" value = "file:///C:/Users/amitagarwal3/Downloads/1htdqs2s.pdf" placeholder="http or https or file:/// url to document"/>
                                </td>
                                                        
                            
                                
								<td align="center">User Name
									<input type="text" name="txtUserName" id="txtUserName" class="cssInputBoxSmall" value = "administrator" />
                                </td> 								
                            </tr>
                            <tr>
                                <td>
                                    <hr class="cssHR" />
                                </td>
                            </tr>
                            <tr>
                                <td align="center">Viewer URL 
                                    <input type="text" id="viewerURL" name="txtViewerURL" class="cssInputBox" value = "http://localhost:8080/MSTServer" placeholder="http://servername:portnumber"/>
                                </td>
                              							
                            </tr>
                            <tr>
                            <td align="right">
                            <input type="submit" name="btnViewerUploadView" value="Upload & View Document" class="cssInputBtn" onclick="uploadAndViewDocument(); return false" />
                            </td>
							
                            </tr>
                            					
                        </table>
                    </td>

                </tr>
                <tr>

                    <td valign="top" align="left" colspan="4">
                        <div style="height: 3px">&nbsp </div>
                        Generated Session ID :
                      

                    </td>
                </tr>
                <tr>
                    <td colspan="4">
                        <div style="height: 10px">&nbsp </div>
                        <iframe id="ieViewerFrame" src="" width="100%" height="600px"></iframe>
                    </td>
                </tr>
            </table>
            <br />

        </div>
    </form>
</body>



</html>
