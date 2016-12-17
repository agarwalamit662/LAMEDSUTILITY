
var client_rsa_enc = null;
var client_rsa_dec = null;
var publicKey = null;
var isEncAllow = "false" ;
function eViewer(id) {

	this.id=id;
	client_rsa_enc = new RSAKey();
	client_rsa_dec = new RSAKey();
	client_rsa_dec.setPrivateEx('a5261939975948bb7a58dffe5ff54e65f0498f9175f5a09288810b8975871e99af3b5dd94057b0fc07535f5f97444504fa35169d461d0d30cf0192e307727c065168c788771c561a9400fb49175e9e6aa4e23fe11af69e9412dd23b0cb6684c4c2429bce139e848ab26d0829073351f4acd36074eafd036a5eb83359d2a698d3',
				'10001',
				'8e9912f6d3645894e8d38cb58c0db81ff516cf4c7e5a14c7f1eddb1459d2cded4d8d293fc97aee6aefb861859c8b6a3d1dfe710463e1f9ddc72048c09751971c4a580aa51eb523357a3cc48d31cfad1d4a165066ed92d4748fb6571211da5cb14bc11b6e2df7c1a559e6d5ac1cd5c94703a22891464fba23d0d965086277a161');
   
   /* this.getPublicKey = function (viewerURL) {
    	//isHTML="false";
    	$.ajax({
    		url: viewerURL + "/fetchPublicKey",
    		crossDomain: true,
    		async: false,
    		success: function (data) {
    		publicKey = data;
    	}
    	});
    	publicKey = atob(publicKey);
    	client_rsa_enc.setPublic(publicKey, '10001');
    	return atob(publicKey);
    }
    */
	
	
    /*
    Uploads a document to the server and provide a unique ID as a reference to the uploaded document.
    */
    this.uploadDocument = function (viewerURL, documentUrl,password,isHTML) {
    	
    	//isHTML="false";
    	var result;
    	if( publicKey == null || client_rsa_enc.e == 0 )
    		getPublicKey(viewerURL);
    	if( isEncAllow == 'true' )
    	{
    		documentUrl = getEncryptedData(documentUrl);
        	isHTML = getEncryptedData(isHTML);
        	password = getEncryptedData(password);
    	}
    	 $.ajax({
        	 url: viewerURL + "/upload?url=" +documentUrl+"&PDFPassword="+password+"&isHTML="+isHTML+"&"+Math.random(),
            crossDomain: true,
            async: false,
            success: function (data) {
				result = data;
            }
        });
    	 
    	if( isEncAllow == 'true' )
     	{
    		result = getDecryptedData(result);
     	}
        return result;
    }
	
	
	this.isScannerInstalled = function()
	{
	try{
	 var scannerInerface = new ActiveXObject('MS_SCAN2.TWAINSCanner.1');
	 return true;
	 }
	 catch(e)
	 {
	 return false;
	 }
	}
	
	this.InstallScanner = function()
	{
		var worker = new Worker('web/downloader.js?'+Math.random());
								  worker.onmessage = function(e) {
								  	var uint8Array = new Uint8Array(e.data);
									
									var blob = new Blob([uint8Array], {type: 'application/octet-binary'});
									window.navigator.msSaveOrOpenBlob(blob, 'ScannerPlugin.exe'); 
									  }
	worker.postMessage({fileName: 'abc',url:"../scanner/ScannerPlugIn.exe", type: 'image/pdf'});
	}
    
    /*
     *  retrieve a document from CM8 on the basis of docID
     */
    
    this.uploadCM8Document = function (documentID, viewerURL,FormNum) 
    {
        var result;
        if( publicKey == null || client_rsa_enc.e == 0 )
    		getPublicKey(viewerURL);
        var repository = "icm" ;
        var actionType = "uploadDocument";
        if( isEncAllow == 'true' )
    	{
        	repository = getEncryptedData(repository);
        	actionType = getEncryptedData(actionType);
        	documentID = getEncryptedData(documentID);
        	FormNum    = getEncryptedData(FormNum);
    	}
        $.ajax({
            url: viewerURL + "/UploadCM8?repository="+repository+"&actionType="+actionType+"&documentID=" + documentID+"&FormNum="+FormNum+"&"+Math.random(),
            crossDomain: true,
            async: false,
            success: function (data) 
            {
				result = data;
            }
        });

        if( isEncAllow == 'true' )
        {
        	result = getDecryptedData(result);
        }
        if(result.indexOf("documentID") != -1)
        {
        	var obj = JSON.parse(result);
        	result =obj.documentID;
        }
        return result;
    }

    /*
    Uploads a document to the server and provide a unique ID as a reference to the uploaded document.
    */
    this.uploadSharePointDocument = function (viewerURL, documentID,isHTML) {
        var result;
        if( publicKey == null || client_rsa_enc.e == 0 )
    		getPublicKey(viewerURL);
        if( isEncAllow == 'true' )
    	{
        	isHTML = getEncryptedData(isHTML);
        	documentID = getEncryptedData(documentID);
    	}
        $.ajax({
            url: viewerURL + "/UploadSharepoint?id=" + documentID+"&"+Math.random()+"&isHTML="+isHTML,//Ankit added isHTML variable
            crossDomain: true,
            async: false,
            success: function (data) {
				result = data;
            }
        });
        if( isEncAllow == 'true' )
        {
        	result = getDecryptedData(result);
        }
        if(result.indexOf("documentID") != -1)
        {
        	var obj = JSON.parse(result);
        	result =obj.documentID;
        }
        return result;
    }
	
	 /*
    Uploads a document to the server and provide a unique ID as a reference to the uploaded document.
    */
    this.uploadSharePointDocumentByURL = function (viewerURL, documentID,isHTML, isSharePointUploadByUrl, userName,libraryName,subSiteName,folderPath) {
        var result;
        if( publicKey == null || client_rsa_enc.e == 0 )
    		getPublicKey(viewerURL);
    	if( isEncAllow == 'true' )
    	{
    		documentID = getEncryptedData(documentID);
        	isHTML = getEncryptedData(isHTML);
        	userName = getEncryptedData(userName);
        	libraryName = getEncryptedData(libraryName);
        	subSiteName = getEncryptedData(subSiteName);
        	folderPath = getEncryptedData(folderPath);
    	}
        $.ajax({
            url: viewerURL + "/UploadSharepointByUrl?id=" + documentID+"&isHTML="+isHTML+"&isSharePointUploadByUrl="+isSharePointUploadByUrl+"&userName="+userName+"&libraryName="+libraryName+"&subSiteName="+subSiteName+"&folderPath="+folderPath+"&"+Math.random(),//Ankit added isHTML variable
            crossDomain: true,
            async: false,
            success: function (data) {
				result = data;
            }
        });
        
        if( isEncAllow == 'true' )
     	{
    		result = getDecryptedData(result);
     	}
        if(result.indexOf("documentID") != -1)
        {
        	var obj = JSON.parse(result);
        	result =obj.documentID;
        }
        return result;
    }
    
    this.uploadGoogleDriveDoc = function (viewerURL, documentID,isHTML, isSharePointUploadByUrl, userName,libraryName,revision) {
        var result;
        if( publicKey == null || client_rsa_enc.e == 0 )
    		getPublicKey(viewerURL);
    	if( isEncAllow == 'true' )
    	{
    		documentID = getEncryptedData(documentID);
        	isHTML = getEncryptedData(isHTML);
        	userName = getEncryptedData(userName);
        	libraryName = getEncryptedData(libraryName);
        	subSiteName = getEncryptedData(subSiteName);
        	folderPath = getEncryptedData(folderPath);
    	}
        $.ajax({
            url: viewerURL + "/UploadByGoogleDrive?id=" + documentID+"&isHTML="+isHTML+"&isGoogleDrive=true"+"&userName="+userName+"&DriveName="+libraryName+"&revision="+revision+"&"+Math.random(),//Ankit added isHTML variable
            crossDomain: true,
            async: false,
            success: function (data) {
				result = data;
            }
        });
        
        if( isEncAllow == 'true' )
     	{
    		result = getDecryptedData(result);
     	}
        if(result.indexOf("documentID") != -1)
        {
        	var obj = JSON.parse(result);
        	result =obj.documentID;
        }
        return result;
    }
	
	this.uploadSharePointDocumentByRest = function (rest_uri, documentID, ecm_username ,milli_seconds_available, username) 
    {
		var result;
    	var service_url = rest_uri+"rest/uploadDocByID/sharepoint";
    	
    	if( publicKey == null || client_rsa_enc.e == 0 )
    		getPublicKeyByRest(rest_uri+"rest");
    	if( isEncAllow == 'true' )
    	{
    		documentID = getEncryptedData(documentID);
    		ecm_username = getEncryptedData(ecm_username);
    		username = getEncryptedData(username);
    		milli_seconds_available = getEncryptedData(milli_seconds_available);
    	}
    	var jsonData ;
    	if( ecm_username != null )
    	{
    		jsonData='{"id":"'+documentID
            +'","milli_seconds_available":"'+milli_seconds_available+'","ecm_username":"'+ecm_username
            +'","username":"'+username+'"}';
    	}
    	else
    	{
    		jsonData='{"ecm":"sharepoint","id":"'+documentID
            +'","milli_seconds_available":"'+milli_seconds_available+'","username":"'+username+'"}';
    	}
    	
    		$.ajax({
    			beforeSend: function (request)
                {
                    request.setRequestHeader("isDataEncrypted",isEncAllow);
                    if( isEncAllow == 'true' )
                    	request.setRequestHeader("publicKey","a5261939975948bb7a58dffe5ff54e65f0498f9175f5a09288810b8975871e99af3b5dd94057b0fc07535f5f97444504fa35169d461d0d30cf0192e307727c065168c788771c561a9400fb49175e9e6aa4e23fe11af69e9412dd23b0cb6684c4c2429bce139e848ab26d0829073351f4acd36074eafd036a5eb83359d2a698d3");
                },
    			 url: service_url,
        		 type: "POST",
        		 contentType: 'application/json',
        		 data:jsonData,
    	         crossDomain: true,
    	         async: false,
    	         success: function (res) 
    	         {
    			var obj = JSON.parse(res);
    			var sessionID=obj.documentID;
    			if( isEncAllow == 'true' )
    			{
    				sessionID = getDecryptedData(sessionID);
    			}
    			if(sessionID.indexOf('ERROR')!=-1)
    			{
    				alert("ID Doesn't exist");
    				document.getElementById('uploadButton').disabled=false;
    				return;
    			}
    			result = sessionID ;
    	         }
    	});
    	
    	return result ;
    }
	
	 /*
    Uploads a document to the server and provide a unique ID as a reference to the uploaded document.
    */
    this.uploadAndViewSharePointDocumentByURL = function (rest_uri, documentID, ecm_username , username) 
    {
    	var service_url = rest_uri+"rest/uploadAndViewURLByID/sharepoint";
    	var jsonData='{"id":"'+documentID+'","ecm_username":"'+ecm_username+'","username":"'+username+'"}';

    	$.ajax({
    		url: service_url,
    		type: "POST",
    		contentType: 'application/json',
    		data:jsonData,
    		success: function(res)
    		{
    		var obj = JSON.parse(res);
    		var sessionID=obj.sessionID;
    		if(sessionID.indexOf('ERROR')!=-1)
          	{
				alert("ID Doesn't exist");
				document.getElementById('uploadButton').disabled=false;
				return;
            }
      		setAnnotationStreamFromSharePoint(sessionID);
      		window.open(obj.viewerUrl); 
      		document.getElementById('uploadButton').disabled=false;	
  			document.getElementById("txtViewerId1").value = sessionID;	           		     
    		},
    		error:function()
    		{
    			document.getElementById("txtViewerId1").value="there was an error while uploading";
    		}   
    	});
    }
	
	  this.uploadFileNetDocument = function (viewerURL, documentID,isHTML,userName) {
        var result;
        if( publicKey == null || client_rsa_enc.e == 0 )
    		getPublicKey(viewerURL);
    	if( isEncAllow == 'true' )
    	{
    		documentID = getEncryptedData(documentID);
        	isHTML = getEncryptedData(isHTML);
        	userName = getEncryptedData(userName);
    	}
        $.ajax({
            url: viewerURL + "/UploadFileNet?documentID=" + documentID+"&isHTML="+isHTML+"&username="+userName+"&"+Math.random(),
            crossDomain: true,
            async: false,
            success: function (data) {
				result = data;
            }
        });
        if( isEncAllow == 'true' )
     	{
    		result = getDecryptedData(result);
     	}
        if(result.indexOf("documentID") != -1)
        {
        	var obj = JSON.parse(result);
        	result =obj.documentID;
        }
        return result;
    }
	
    this.uploadCmisDocument = function (viewerURL, documentID) {
        var result;
        if( publicKey == null || client_rsa_enc.e == 0 )
    		getPublicKey(viewerURL);
    	if( isEncAllow == 'true' )
    	{
    		documentID = getEncryptedData(documentID);
    	}
        $.ajax({
            url: viewerURL + "/UploadCmis?id=" + documentID+"&"+Math.random(),
            crossDomain: true,
            async: false,
            success: function (data) {
				result = data;
            }
        });
        if( isEncAllow == 'true' )
     	{
    		result = getDecryptedData(result);
     	}
        if(result.indexOf("documentID") != -1)
        {
        	var obj = JSON.parse(result);
        	result =obj.documentID;
        }
        return result;
    }
// Insert Document to CMIS
    this.insertCmisDocument = function (viewerURL, documentURL,objectType,folderId,data) {
        var result;
        var attributes="";
        var keys="";
        var row= data.split(",");
        for(var i=0;i<row.length-1;i++)
        	{
        		var colums= row[i].split("=");
        		attributes+=colums[0]+"="+document.getElementById(colums[0]).value+",";
        		keys+=colums[0]+",";
        	}
        $.ajax({
            url: viewerURL + "/InsertCmis?documentURL=" + documentURL+"&"+"objectType="+objectType+"&"+"folderId="+folderId+"&attributes="+attributes+"&keys="+keys+"&"+Math.random(),
            crossDomain: true,
            async: false,
            success: function (data) {
				result = data;
            }
        });
        return result;
    }
    /*
    Download document in its native format.
    */
    this.getNativeDocument = function (viewerURL, sessionId, userId, download) {
        if (!download) {
            var result;
            $.ajax({
                url: viewerURL + "/getNativeDocument?sessionId=" + sessionId + "&userId=" + userId,
                crossDomain: true,
                async: false,
                success: function (data) {
                    result = data;
                }
            });
        }
        else {
            window.open(viewerURL + "/getNativeDocument?sessionId=" + sessionId + "&userId=" + userId);
        }
        return result;
    }


    /*
    Retrieve total number of pages in the document.
    */
    this.getPageCount = function (viewerURL, sessionId, userId) {
        var result;
        $.ajax({
            url: viewerURL + "/pagecount?sessionId=" + sessionId + "&userId=" + userId,
            crossDomain: true,
            async: false,
            success: function (data) {
                result = data;
            }
        });
        return result;
    }

    
    /*
    View uploaded CM8 document in the viewer.
    */
    
    this.viewCM8Document = function (viewerURL, sessionId, userId, userName, redirect,isHTML ,isCM8) {
    	//isHTML="false";
        var result;
        //Dhananjay : CMIS Integration
        var cmisData;
        var isCmis;
        if(sessionId.indexOf("&")>-1)
        {
        	cmisData=sessionId.split("&");   
        	sessionId=cmisData[0];
        	isCmis=cmisData[1];
        }
        if( publicKey == null || client_rsa_enc.e == 0 )
    		getPublicKey(viewerURL);
    	if( isEncAllow == 'true' )
    	{
    		userId = getEncryptedData(userId);
    		userName = getEncryptedData(userName);
    		redirect = getEncryptedData(redirect);
    		isHTML = getEncryptedData(isHTML);
    		isCM8 = getEncryptedData(isCM8);
    		isCmis = getEncryptedData(isCmis);
    	}
        //Nisha : added for multi document session opening
        var multipleSessionId=sessionId.split(",");
        var length = 1;
        if(multipleSessionId.length>1)
        {
        	var newString="";
        	var count=0;
        	for(var i=0;i<multipleSessionId.length;i++)
        	{
        		if(multipleSessionId[i]=="")
        			continue;
        		newString+="sessionId"+count+"=";
        		newString+=multipleSessionId[i];
        		count++;
        		newString+="&";
        	}
        	sessionId=newString;
        	length = (multipleSessionId.length-1);
        	if( isEncAllow == 'true' )
        	{
        		length = getEncryptedData(length) ;
        	}
        	$.ajax({
        		url: viewerURL + "/view?" + sessionId +"&NoOfSessionId="+length+"&userId=" + userId + "&userName=" + userName + "&redirect=" + redirect +"&isCmis=" + isCmis+"&isHTML="+isHTML+"&isCM8="+isCM8,
        		crossDomain: true,
        		async: false,
        		success: function (data) {
        		result = data;
        	}
        	});
        }
       else
       {
    	   if( isEncAllow == 'true' )
    	   {
    		   sessionId = getEncryptedData(multipleSessionId[0]);
    		   length = getEncryptedData(length) ;
    	   }
        $.ajax({
        	url: viewerURL + "/view?sessionId=" + sessionId+"&NoOfSessionId="+length+ "&userId=" + userId + "&userName=" + userName + "&redirect=" + redirect +"&isCmis=" + isCmis+"&isHTML="+isHTML+"&isCM8="+isCM8,
            crossDomain: true,
            async: false,
            success: function (data) {
                result = data;
            }
        });
       }
       
       if( isEncAllow == 'true' )
       {
    	   result = getDecryptedData(result);
       }
       if(result.indexOf("documentID") != -1)
       {
    	   var obj = JSON.parse(result);
    	   result =obj.documentID;
       }
       //Nisha :end
        return result;

    }
    
    
    /*
    View uploaded document in the viewer.
    */
    this.viewDocument = function (viewerURL, sessionId, userId, userName, redirect,isHTML,isFileNet,viewMode,isSplitViewer) {
    	//isHTML="false";
    	var result;
    	if(!viewMode){
    		viewMode = "";
    	}
    	//Nisha : added for multi document session opening
    	var multipleSessionId=sessionId.split(",");
    	if( publicKey == null || client_rsa_enc.e == 0 )
    		getPublicKey(viewerURL);
    	//Dhananjay : CMIS Integration
    	var cmisData;
    	var isCmis;
    	if(sessionId.indexOf("&")>-1)
    	{
    		cmisData=sessionId.split("&");   
    		sessionId=cmisData[0];
    		isCmis=cmisData[1];
    	}
    	if( isEncAllow == 'true' )
    	{
    		userId = getEncryptedData(userId);
    		userName = getEncryptedData(userName);
    		redirect = getEncryptedData(redirect);
    		isHTML = getEncryptedData(isHTML);
    		isFileNet = getEncryptedData(isFileNet);
    		isCmis = getEncryptedData(isCmis);
    		viewMode = getEncryptedData(viewMode);
    	}
    	var length = 1 ;
       if(multipleSessionId.length>1)
        {
    	   var newString="sessionId0=";
    	   for(var i=0;i<multipleSessionId.length;i++)
    	   {
    		    if(multipleSessionId[i]=="")
        		continue;
    		    if( isEncAllow == 'true' )
    		    {
    		    	newString += getEncryptedData(multipleSessionId[i]);
    		    }
    		    else
    		    {
    		    	newString += multipleSessionId[i];
    		    }
    		    if(i!=(multipleSessionId.length-1))
    		    	newString+="&sessionId"+(i+1)+"=";
    	   }
    	   sessionId=newString;
    	   length = multipleSessionId.length ;
    	   if( isEncAllow == 'true' )
       	   {
    		   length = getEncryptedData(length) ;
       	   }
    	   $.ajax({
    		   url: viewerURL + "/view?" + sessionId +"&NoOfSessionId="+length+"&userId=" + userId + "&userName=" + userName + "&redirect=" + redirect +"&isCmis=" + isCmis+"&isHTML="+isHTML+"&isFileNet="+isFileNet+"&split="+isSplitViewer+"&viewMode="+viewMode,
               crossDomain: true,
               async: false,
               success: function (data) {
                   result = data;
               }
           });
        }
       else
       {
    	   if( isEncAllow == 'true' )
       	   {
    		   length = getEncryptedData(length) ;
    		   sessionId = getEncryptedData(multipleSessionId[0]);
       	   }
        $.ajax({
        	url: viewerURL + "/view?sessionId=" + sessionId+"&NoOfSessionId="+length+ "&userId=" + userId + "&userName=" + userName + "&redirect=" + redirect +"&isCmis=" + isCmis+"&isHTML="+isHTML+"&isFileNet="+isFileNet+"&split="+isSplitViewer+"&viewMode="+viewMode,
            crossDomain: true,
            async: false,
            success: function (data) {
                result = data;
            }
        });
       }
       //Nisha :end
       if( isEncAllow == 'true' )
       {
    	   result = getDecryptedData(result);
       }
       return result;

    }
    
    this.viewDocumentByRest = function (rest_uri, sessionId, userId, userName, redirect,isHTML,isFileNet,viewMode) 
    {
    	if(!viewMode){
    		viewMode = "";
    	}
    	var service_url = rest_uri+"rest/server/view";	
    	userName = userName.value ;
    	//isHTML="false";
    	var result;
    	//Nisha : added for multi document session opening
    	var multipleSessionId=sessionId.split(",");
    	if( publicKey == null || client_rsa_enc.e == 0 )
    		getPublicKeyByRest(viewerURL+"rest");
    	//Dhananjay : CMIS Integration
    	var cmisData;
    	var isCmis;
    	if(sessionId.indexOf("&")>-1)
    	{
    		cmisData=sessionId.split("&");   
    		sessionId=cmisData[0];
    		isCmis=cmisData[1];
    	}
    	if( isEncAllow == 'true' )
    	{
    		userId = getEncryptedData(userId);
    		userName = getEncryptedData(userName);
    		redirect = getEncryptedData(redirect);
    		isHTML = getEncryptedData(isHTML);
    		isFileNet = getEncryptedData(isFileNet);
    		isCmis = getEncryptedData(isCmis);
    		viewMode = getEncryptedData(viewMode);
    	}
    	var length = 1 ;
       if(multipleSessionId.length>1)
        {
    	   var newString="sessionId0=";
    	   for(var i=0;i<multipleSessionId.length;i++)
    	   {
    		    if(multipleSessionId[i]=="")
        		continue;
    		    if( isEncAllow == 'true' )
    		    {
    		    	newString += getEncryptedData(multipleSessionId[i]);
    		    }
    		    else
    		    {
    		    	newString += multipleSessionId[i];
    		    }
    		    if(i!=(multipleSessionId.length-2))
    		    	newString+="&sessionId"+(i+1)+"=";
    	   }
    	   sessionId=newString;
    	   length = (multipleSessionId.length-1) ;
    	   if( isEncAllow == 'true' )
       	   {
    		   length = getEncryptedData(length) ;
       	   }
    	   var temp = sessionId +"&NoOfSessionId="+length+"&userId=" + userId + "&userName=" + userName + "&redirect=" + redirect +"&isCmis=" + isCmis+"&isHTML="+isHTML+"&isFileNet="+isFileNet+"&viewMode="+viewMode ;
    	   var jsonData='{"value":"'+temp+'"}';
    	   $.ajax({
    		   url: service_url,
    		   type: "POST",
    	       contentType: 'application/json',
    	 	   data:jsonData,
               crossDomain: true,
               async: false,
               success: function (data) {
                   result = data;
               }
           });
        }
       else
       {
    	   if( isEncAllow == 'true' )
       	   {
    		   length = getEncryptedData(length) ;
    		   sessionId = getEncryptedData(multipleSessionId[0]);
       	   }
    	   var temp = "sessionId="+sessionId+"&NoOfSessionId="+length+ "&userId=" + userId + "&userName=" + userName + "&redirect=" + redirect +"&isCmis=" + isCmis+"&isHTML="+isHTML+"&isFileNet="+isFileNet+"&viewMode="+viewMode ;
    	   var jsonData='{"value":"'+temp+'"}';
        $.ajax({
        	url: service_url ,
        	type: "POST",
 	        contentType: 'application/json',
 	 	    data:jsonData,
            crossDomain: true,
            async: false,
            success: function (data) {
                result = data;
            }
        });
       }
       if( isEncAllow == 'true' )
       {
    	   result = getDecryptedData(result);
       }
       return result;

    }
	
	/*function viewDocumentFromRest(rest_uri, sessionId) 
    {
    	var service_url = rest_uri+"rest/viewerURL/"+sessionId;
    
    	$.ajax({
    		url: service_url,
    		type: "GET",
    		//contentType: 'application/json',
    		//data:jsonData,
    		success: function(res)
    		{
    		var obj = JSON.parse(res);
    		var viewerURL=obj.viewerUrl;
    		window.open(viewerURL); 
      		document.getElementById('uploadButton').disabled=false;	
  			document.getElementById("txtViewerId1").value = obj.sessionID;
      		           		     
    		},
    		error:function()
    		{
    			document.getElementById("txtViewerId1").value="there was an error while viewing";
    		}   
    	});
	}*/
	
    // Added by Ankit for comparsion part    
    this.compareDocuments = function (viewerURL, sessionId, userId, userName, redirect,isHTML) {
        var result;
        //Nisha : added for multi document session opening
        var multipleSessionId=sessionId.split(",");
        
       if(multipleSessionId.length>1)
        {
    	   var newString="sessionId0=";
    	   for(var i=0;i<multipleSessionId.length;i++)
    	   {
    		    if(multipleSessionId[i]=="")
        		continue;
        		newString+=multipleSessionId[i];
        		if(i!=(multipleSessionId.length-2))
        		newString+="&sessionId"+(i+1)+"=";
    	   }
    	   sessionId=newString;
    	   $.ajax({
               url: viewerURL + "/view?" + sessionId +"&NoOfSessionId="+(multipleSessionId.length-1)+"&userId=" + userId + "&userName=" + userName + "&redirect=" + redirect+"&isHTML="+isHTML,
               crossDomain: true,
               async: false,
               success: function (data) {
                   result = data;
               }
           });
        }
       else
       {
    	   
        $.ajax({
            url: viewerURL + "/view?sessionId=" + sessionId+"&NoOfSessionId="+1+ "&userId=" + userId + "&userName=" + userName + "&redirect=" + redirect+"&isHTML="+isHTML,
            crossDomain: true,
            async: false,
            success: function (data) {
                result = data;
            }
        });
       }
       var viewer=document.getElementById(this.id);
		if(viewer)
		{
			viewer.src="../web/"+result;
			viewerDocument=viewer.contentWindow.document;
		}
       //Nisha :end
		return result+"&compare=true";

    }

    /*
    Get current document status. It could be one of the following:
    DOWNLOADING
    DOWNLOADED
    CONVERTING
    UPLOADED
    DROPPED
    ERROR_DURING_CONVERSION
    UNAVAILABLE
    */
    this.getDocumentStatus = function (viewerURL, sessionId) {
    	if(sessionId.indexOf("_isBlankViewer") > -1){
    		return "DONE";
    	}
        var result;
        if( publicKey == null || client_rsa_enc.e == 0 )
    		getPublicKey(viewerURL);
        if( isEncAllow == 'true' )
    	{
        	sessionId = getEncryptedData(sessionId);
    	}
        
        $.ajax({
            url: viewerURL + "/status?sessionId=" + sessionId,
            crossDomain: true,
            async: false,
            success: function (data) {
                result = data;
            }
        });
        if( isEncAllow == 'true' )
     	{
    		result = getDecryptedData(result);
     	}
        return result;
    }

    /*
    Get thumbnail image for a page in the document at the input size. The output is a JPEG image height width adjusted keeping the aspect ratio.
    */
    this.genarateThumbnail = function (viewerURL, sessionId, userId, pageNo, width, height, download) {
        if (!download) {
            var result;
            $.ajax({
                url: viewerURL + "/thumbnail?sessionId=" + sessionId + "&userId=" + userId + "&pageNo=" + pageNo + "&width=" + width + "&height=" + height,
                crossDomain: true,
                async: false,
                success: function (data) {
                    result = data;
                }
            });
        }
        else {
            window.open(viewerURL + "/thumbnail?sessionId=" + sessionId + "&userId=" + userId + "&pageNo=" + pageNo + "&width=" + width + "&height=" + height);
        }
        return result;
    }

    /*
    Re-Order's pages of the document and returns a PDF output.
    */
    this.reorderPages = function (viewerURL, sessionId, userId, pageOrder, download) {
        if (!download) {
            var result;
            $.ajax({
                url: viewerURL + "/ReOrderPages?sessionId=" + sessionId + "&userId=" + userId + "&pageOrder=" + pageOrder,
                crossDomain: true,
                async: false,
                success: function (data) {
                    result = data;
                }
            });
        }
        else {
            window.open(viewerURL + "/ReOrderPages?sessionId=" + sessionId + "&userId=" + userId + "&pageOrder=" + pageOrder);
        }
        return result;
    }

    /*
    Exports the uploaded document out as PDF. If the document you uploaded is open in the viewer 
    then the exported PDF will preseve the rotation and reprdering changes.
    */
    this.exportDocument = function (viewerURL, sessionId, userId, download) {
        var result;
        if (!download) {
            $.ajax({
                url: viewerURL + "/export?sessionId=" + sessionId + "&userId=" + userId,
                crossDomain: true,
                async: false,
                success: function (data) {
                    result = data;
                }
            });
        }
        else {
            window.open(viewerURL + "/export?sessionId=" + sessionId + "&userId=" + userId);
        }

        return result;
    }

    /*
    UnLoad and remove document.
    */
    this.removeDocument = function (viewerURL, sessionId,isHTML, isFilenet) {
    	if(typeof(sessionId)== undefined || sessionId == null || sessionId.trim() == ""){
    		return;    		
    	}
    	if(typeof(isFilenet)== undefined || isFilenet == null){
    		isFilenet = false;
    	}
        var result;
    	if( publicKey == null || client_rsa_enc.e == 0 )
    		getPublicKey(viewerURL);
    	if( isEncAllow == 'true' )
    	{
    		sessionId = getEncryptedData(sessionId);
        	isHTML = getEncryptedData(isHTML);
    	}
    	var urlToSend = viewerURL + "/delete?sessionId=" + sessionId+"&isHTML="+isHTML;
    	if(isFilenet){
    		if( isEncAllow == 'true' )
    		isFilenet = getEncryptedData(isFilenet);
    		var urlToSend = viewerURL + "/delete?sessionId=" + sessionId+"&isHTML="+isHTML+
    		"&deleteFile="+isFilenet;
    	}
        $.ajax({
            url: urlToSend,
            crossDomain: true,
            async: false,
            success: function (data) {
                result = data;
            }
        });
        if( isEncAllow == 'true' )
     	{
    		result = getDecryptedData(result);
     	}
        return result;
    }
    this.removeDocumentByRest = function (rest_uri, sessionId) 
    {
    	var result = "null" ;
    	if(sessionId!=="")
    	{
    		if( publicKey == null || client_rsa_enc.e == 0 )
        		getPublicKeyByRest(rest_uri+"rest");
        	if( isEncAllow == 'true' )
        	{
        		sessionId = getEncryptedData(sessionId);
        	}
    		var service_url = rest_uri+"rest/remove/"+sessionId;

    		$.ajax({
    			url: service_url,
    			type: "POST",
    			//contentType: 'application/json',
    			//data:jsonData,
    			success: function(res)
    			{
    			if(res.indexOf("status") != -1)
    			{
    				var obj = JSON.parse(res);
    				res = obj.status;
    			}
    			if( isEncAllow == 'true' )
    			{
    				res = getDecryptedData(res);
    			}
    			result = res ;
    			console.log(res);
    			},
    			error:function()
    			{
    				document.getElementById("txtViewerId1").value="there was an error while deleting";
    			}   
    		});
    	}
    	return result ;
    	
    	
    	
       /* var result;
        $.ajax({
            url: viewerURL + "/delete?sessionId=" + sessionId+"&isHTML="+isHTML,
            crossDomain: true,
            async: false,
            success: function (data) {
                result = data;
            }
        });

        return result;
        */
    }
    this.dropDocument = function (viewerURL, sessionId) {
        var result;
        var userId = "&isBase64=false" ;
        if( publicKey == null || client_rsa_enc.e == 0 )
    		getPublicKey(viewerURL);
    	if( isEncAllow == 'true' )
    	{
    		sessionId = getEncryptedData(sessionId);
    		userId = getEncryptedData(userId);
    	}
        $.ajax({
            url: viewerURL + "/MSDropDocument?documents=" + sessionId + "&userId=" + userId,
            crossDomain: true,
            async: false,
            success: function (data) {
                result = data;
            }
        });
       // this.removeDocument(viewerURL, sessionId);
        if( isEncAllow == 'true' )
     	{
    		result = getDecryptedData(result);
     	}
        return result;
    }
    /*
    Extract text from the pages of PDF and office documents.
    */
    this.extractPageText = function (viewerURL, sessionId, userId, pageNo) {
        var result;
        $.ajax({
            url: viewerURL + "/extracttext?sessionId=" + sessionId + "&userId=" + userId + "&pageNo=" + pageNo,
            crossDomain: true,
            async: false,
            success: function (data) {
                result = data;
            }
        });

        return result;
    }
    
    /*
    * this.setAnnotationStream(viewerURL, annotationUrl,documentSessionId) fetch annotation stream from URL
    * */
   this.setAnnotationStreamFromSharePoint = function (viewerURL,docID, documentSessionId,isHTML){
   	var result;
   	if( publicKey == null || client_rsa_enc.e == 0 )
		getPublicKey(viewerURL);
	if( isEncAllow == 'true' )
	{
		docID = getEncryptedData(docID);
    	isHTML = getEncryptedData(isHTML);
    	documentSessionId = getEncryptedData(documentSessionId);
	}
       $.ajax({
           url: viewerURL + "/GetAnnotationFromSharePoint?docID="+docID+"&docUrlSessionId=" + documentSessionId+"&isHTML="+isHTML,
           crossDomain: true,
           async: false,
           success: function (data) {
				result = data;
           }
       });
       if( isEncAllow == 'true' )
    	{
   		result = getDecryptedData(result);
    	}
       return result;
   }
   
   
   /*
    * this.setAnnotationStream(viewerURL, annotationUrl,documentSessionId) fetch annotation stream from URL
    * */
   this.setAnnotationStreamFromSharePointByRest = function (rest_uri,docID, documentSessionId){
	   var service_url = rest_uri+"rest/getSharepointAnnotations";
	   if( publicKey == null || client_rsa_enc.e == 0 )
			getPublicKeyByRest(rest_uri+"rest");
		if( isEncAllow == 'true' )
		{
			docID = getEncryptedData(docID);
	    	documentSessionId = getEncryptedData(documentSessionId);
		}
	   var data="docID="+docID+"&docUrlSessionId=" + documentSessionId+"&isHTML=undefined";
   	   var jsonData='{"value":"'+data+'"}';

   	$.ajax({
   		url: service_url,
   		type: "POST",
   		contentType: 'application/json',
   		data:jsonData,
   	    crossDomain: true,
        async: false,
   		success: function(res)
   		{
   	        if( isEncAllow == 'true' )
 	        {
   	        	res = getDecryptedData(res);
 	        }
   		    console.log("success getSharepointAnnotations by rest with id :"+res);      		     
   		},
   		error:function()
   		{
   			console.log("error in getSharepointAnnotations by rest");
   		}   
   	});
   }
   
    /*
    * this.setAnnotationStream(viewerURL, annotationUrl,documentSessionId) fetch annotation stream from URL
    * */
   this.getAnnotationStreamFromSharePoint = function (viewerURL,documentURL, documentSessionId,userName, action){
   	var result;
       $.ajax({
           url: viewerURL + "/GetAnnotationFromSharePointAsJSON?documentURL="+documentURL+"&docUrlSessionId=" + documentSessionId+"&userName="+userName+"&action="+action,
           crossDomain: true,
           async: false,
           success: function (data) {
				result = data;
           }
       });
       return result;
   }
    
    
   /*
    * this.setAnnotationStream() for CM8
    * */
   this.setAnnotationStreamFromCM8 = function (viewerURL,docID, documentSessionId,isHTML){
   	var result;
       $.ajax({
           url: viewerURL + "/GetAnnotationFromCM8?docID="+docID+"&docUrlSessionId=" + documentSessionId+"&isHTML="+isHTML,
           crossDomain: true,
           async: false,
           success: function (data) {
				result = data;
           }
       });
       return result;
   }
    
    
    /*
     * this.setAnnotationStream(viewerURL, annotationUrl,documentSessionId) fetch annotation stream from URL
     * */
    this.setAnnotationStream = function (viewerURL, annotationUrl,documentSessionId,isHTML){
    	var result;
        $.ajax({
            url: viewerURL + "/Annotation?annurl=" + annotationUrl+"&docUrlSessionId=" + documentSessionId+"&isHTML="+isHTML,
            crossDomain: true,
            async: false,
            success: function (data) {
				result = data;
            }
        });
        return result;
    }
    this.setMarkupStream = function (viewerURL, annotationUrl,documentSessionId,isMarkup){
    	var result;
        $.ajax({
            url: viewerURL + "/Annotation?annurl=" + annotationUrl+"&docUrlSessionId=" + documentSessionId+"&isMarkup="+isMarkup+"&isHTML="+isHTML,
            crossDomain: true,
            async: false,
            success: function (data) {
				result = data;
            }
        });
        return result;
    }
    /*
     * this.getAnnotationStream(viewerURL,documentSessionId) return URL of annotation stream
     * */
    this.getAnnotationStream =function(viewerURL,documentSessionId,isHTML){
    	var result;
        $.ajax({
            url: viewerURL + "/Annotation?getUrlSessionId=" + documentSessionId+"&isHTML="+isHTML,
            crossDomain: true,
            async: false,
            success: function (data) {
				result = data;
            }
        });
        return result;
    }
    this.getMarkupStream =function(viewerURL,documentSessionId,isMarkup){
    	var result;
        $.ajax({
            url: viewerURL + "/Annotation?getUrlSessionId=" + documentSessionId+"&isMarkup="+isMarkup+"&isHTML="+isHTML,
            crossDomain: true,
            async: false,
            success: function (data) {
				result = data;
            }
        });
        return result;
    }
    
    //Ankit added for zoom in part
    this.zoom =function(option){
    	if(option=='page-fit')
    	{
    		document.getElementById(this.id).contentWindow.FitToHeight("Fit-To-Height");
    	}
    	else if(option=='page-width')
    	{
    		document.getElementById(this.id).contentWindow.FitToWidth("Fit-To-Width");
    	}
    	
    	
    }
    this.zoom =function(option){
    	if(option=='page-fit')
    	{
    		document.getElementById(this.id).contentWindow.FitToHeight("Fit-To-Height");
    	}
    	else if(option=='page-width')
    	{
    		document.getElementById(this.id).contentWindow.FitToWidth("Fit-To-Width");
    	}
    	
    	
    }
    
    this.zoomIn=function()
    {
    	document.getElementById(this.id).contentWindow.zoom('ZoomIn',true);
    }
    this.zoomOut=function()
    {
    	document.getElementById(this.id).contentWindow.zoom('ZoomOut',true);
    }
    
    
    this.rotate=function(option)
    {
    	if(option==-90)
    		document.getElementById(this.id).contentWindow.rotate('right',true);
    	else if(option==90)
    		document.getElementById(this.id).contentWindow.rotate('left',true);
    }
    this.pageNavigation=function(option)
    {
    	if(option == 1)//First Page
    		document.getElementById(this.id).contentWindow.getFirstImage();
    	else if(option == 4)//Previous Page
    		document.getElementById(this.id).contentWindow.getPreviousImage();
    	else if(option == 3)//Next Page
    		document.getElementById(this.id).contentWindow.getNextImage();
    	else if(option == 2)//Last Page
    		document.getElementById(this.id).contentWindow.getLastImage();
    }
    
    this.SetAnnotation=function(option)
    {
    	if(option == 1)//Line 
    		document.getElementById(this.id).contentWindow.addAnnotation('line');
    	else if(option == 2)//Rect
    		document.getElementById(this.id).contentWindow.addAnnotation('rect');
    	else if(option == 3)//Rect
    		document.getElementById(this.id).contentWindow.addAnnotation('fillrect');
    	else if(option == 4)//Cricle
    		document.getElementById(this.id).contentWindow.addAnnotation('circle');
    	else if(option == 5)//Cricle
    		document.getElementById(this.id).contentWindow.addAnnotation('fillcircle');
    	else if(option == 6)//Arrow
    		document.getElementById(this.id).contentWindow.addAnnotation('arrow');
    	else if(option == 7)//Highlight
    		document.getElementById(this.id).contentWindow.addAnnotation('highLight');
    	else if(option == 8)//Pen
    		document.getElementById(this.id).contentWindow.addAnnotation('pen');
    	else if(option == 9)//Text
    		document.getElementById(this.id).contentWindow.addAnnotation('text');
    	
    }
    this.thumbnail=function(option)
    {
    	if(option==1)
    		document.getElementById(this.id).contentWindow.toggleThumbnails(true);
    	else
    		document.getElementById(this.id).contentWindow.toggleThumbnails(false);
    }
    
    
    
    
    
    this.save=function()
    {
    	var currentDocument=document.getElementById(this.id).contentWindow.currentDocument;
    	if(currentDocument.isDocumentUpdated)
    	{
    		document.getElementById(this.id).contentWindow.saveServerDocument();
    	}		
    	else if(currentDocument.isAnnotationUpdate)
    	{
    		document.getElementById(this.id).contentWindow.saveServerAnnotation();
    	}   
    }
    
    this.getCurrentPage=function()
    {
    	return document.getElementById(this.id).contentWindow.currentPageNumber;
    }
    
    /*
     * this.setDragAndDropStream(viewerURL, annotationUrl,documentSessionId) fetch annotation stream from URL
     * */
    this.setDragAndDropStream = function (viewerURL, annotationUrl,documentSessionId){
    	var result;
        $.ajax({
            url: viewerURL + "/DragAndDrop?annurl=" + annotationUrl+"&docUrlSessionId=" + documentSessionId,
            crossDomain: true,
            async: false,
            success: function (data) {
				result = data;
            }
        });
        return result;
    }
    /*
     * this.getAnnotationStream(viewerURL,documentSessionId) return URL of annotation stream
     * */
    this.getDragAndDropStream =function(viewerURL,documentSessionId){
    	var result;
        $.ajax({
            url: viewerURL + "/DragAndDrop?getUrlSessionId=" + documentSessionId,
            crossDomain: true,
            async: false,
            success: function (data) {
				result = data;
            }
        });
        return result;
    }
    
    this.swapPages = function(viewerURL,sessionId,swapPages,rotation){
    	var result;
    	var rotArr="";
    	for(var i=0; i<PDFView.pages.length; i++){
    		rotArr+=PDFView.pages[i].rotation/90;
    		if(i!=PDFView.pages.length-1){
    			rotArr+=",";
    		}
    	}
        $.ajax({
            url: viewerURL + "/SwapPages?sessionId="+sessionId+"&swapPages=" + swapPages+"&rotationValues="+rotArr+"&isEmbedded="+isEmbedded,
            crossDomain: true,
            async: false,
            success: function (data) {
				result = data;
            }
        });
        return result;
    }
    //Added for CMIS
    this.getObjectType = function(viewerURL){
    	var result;
        $.ajax({
            url: viewerURL + "/GetCMISFileList?request=requestForObjectTypes",
            crossDomain: true,
            async: false,
            success: function (data) {
				result = data;
            }
        });
        return result;
    }
    
   // CMIS : get objectTypeId from root folder and parent
    this.getFolderId=function(viewerURL){
    	var result;
        $.ajax({
            url: viewerURL + "/GetCMISFileList?request=requestForRootFolderId",
            crossDomain: true,
            async: false,
            success: function (data) {
				result = data;
            }
        });
        return result;
    }
    
    //CMIS : get required Field name from object type property definition
    this.getRequiredField=function(viewerURL,objectTypeId)
    {
    	var result;
        $.ajax({
            url: viewerURL + "/GetrequiredFieldforCMIS?objectTypeId="+objectTypeId,
            crossDomain: true,
            async: false,
            success: function (data) {
				result = data;
            }
        });
        return result;
    	
    }
    
    this.openWindow=function(data,iframe_name)
	{
    	var form = document.getElementById("form1") ;
		data = data.split("?");
		if(form != null)
			document.body.removeChild(form);
		form = document.createElement("form");
		form.setAttribute("id", "form1");
		form.setAttribute("method", "post");
		form.setAttribute("action", data[0] );
		if(iframe_name!=null)
			form.setAttribute("target", iframe_name);  
		else
			form.setAttribute("target", "_blank");
		var hiddenField = document.createElement("input");              
		hiddenField.setAttribute("name", "argument");
		hiddenField.setAttribute("value", data[1]);
		form.appendChild(hiddenField);
		document.body.appendChild(form);
		form.submit();
	}
    
}
//For CMIS Testing API
function getFileList(viewerURL,objectId)
{
    
	
	if (window.XMLHttpRequest)
  	{
  		decodeDocumentHttp = new XMLHttpRequest()
  	}
  	else if (window.ActiveXObject)
  	{
  		decodeDocumentHttp = new ActiveXObject("Microsoft.XMLHTTP")
  	}
    decodeDocumentHttp.open("POST",viewerURL+"/GetCMISFileList", true);
  	decodeDocumentHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
  	decodeDocumentHttp.send("request=requestForFileList&objectTypeId="+objectId);

  	decodeDocumentHttp.onload = function(e) {
  		
			if (this.status == 200) {
    var data=decodeDocumentHttp.responseText;
    var objectIds= data.split("&&");
    var table="<table border='1'>";
    for(var i=0;i<objectIds.length-1;i++)
    {
        var objectId=objectIds[i].split(",");
		table= table+"<tr><td>"+objectId[0]+"</td><td>"+objectId[1]+"</td></tr>";
    }
    table=table+"</table>"
    document.body.innerHTML = table;
    
    
			}
		}
}
function getChildren(viewerURL,folderId)
{
	if (window.XMLHttpRequest)
  	{
  		decodeDocumentHttp = new XMLHttpRequest()
  	}
  	else if (window.ActiveXObject)
  	{
  		decodeDocumentHttp = new ActiveXObject("Microsoft.XMLHTTP")
  	}
    decodeDocumentHttp.open("POST",viewerURL+"/GetCMISFileList", true);
  	decodeDocumentHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
  	decodeDocumentHttp.send("request=requestForChildren&folderId="+folderId);
  	
  	
	decodeDocumentHttp.onload = function(e) {
  		
		if (this.status == 200) {
var data=decodeDocumentHttp.responseText;
if(data=="this is documnent not a folder or folder has not any document")
	{
		alert("this is documnent not a folder or folder has not any document");
	}
else
	{
var objectIds= data.split("&&");
var table="<table border='1'>";
for(var i=0;i<objectIds.length-1;i++)
{
    var objectId=objectIds[i].split(",");
	table= table+"<tr><td>"+objectId[0]+"</td><td>"+objectId[1]+"</td><td>"+objectId[2]+"</td><td>"+objectId[3]+"</td><td><input type='button' value='Child' onclick='getChildren(\""+viewerURL+"\",\""+objectId[3]+"\")'></td><td><input type='button' value='Parent' onclick='getParent(\""+viewerURL+"\",\""+objectId[3]+"\")'></td></tr>";
}
table=table+"</table>"
var doc = document.getElementsByTagName('body')[0];

doc.innerHTML =table;

		}
	}
	}
	
}

function getParent(viewerURL,folderId)
{
	if (window.XMLHttpRequest)
  	{
  		decodeDocumentHttp = new XMLHttpRequest()
  	}
  	else if (window.ActiveXObject)
  	{
  		decodeDocumentHttp = new ActiveXObject("Microsoft.XMLHTTP")
  	}
    decodeDocumentHttp.open("POST",viewerURL+"/GetCMISFileList", true);
  	decodeDocumentHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
  	decodeDocumentHttp.send("request=requestForParent&folderId="+folderId);
  	
  	
	decodeDocumentHttp.onload = function(e) {
  		
		if (this.status == 200) {
var data=decodeDocumentHttp.responseText;
if(data=="No parent")
{
	alert("No parent");
}
else
{
var objectIds= data.split("&&");
var table="<table border='1'>";
for(var i=0;i<objectIds.length-1;i++)
{
    var objectId=objectIds[i].split(",");
	table= table+"<tr><td>"+objectId[0]+"</td><td>"+objectId[1]+"</td><td>"+objectId[2]+"</td><td><input type='button' value='Child' onclick='getChildren(\""+viewerURL+"\",\""+objectId[1]+"\")'></td><td><input type='button' value='Parent' onclick='getParent(\""+viewerURL+"\",\""+objectId[1]+"\")'></td></tr>";
}
table=table+"</table>"
var doc = document.getElementsByTagName('body')[0];

doc.innerHTML =table;

		}
		}
	}
}
function getEncryptedData(arg)
{
	var docUrl= "";
	if(arg != null)
		arg = arg.toString();
	if(arg != null && arg.length>117)
	{
		var pieces = Math.ceil(arg.length/117) ;
		var initialIndex = 0 ;
		var finalIndex = 117 ;
		for(var j=1;j<=pieces;j++)
		{
			var temp = "";
			if(j!=pieces)
			{
				temp =arg.substring(initialIndex,finalIndex);
				temp = client_rsa_enc.encrypt(temp) ;
				docUrl += temp + "_separator_"  ;
				
			} 
			else
			{
				temp =arg.substring(initialIndex,arg.length);
				temp = client_rsa_enc.encrypt(temp) ;
   				docUrl += temp ;
			}
			initialIndex +=117 ;
			finalIndex +=117 ;
		}
		arg = docUrl ;
	}
	else
	{
		if( arg != null )
		{
			//if(!isNaN(arg))
			//	arg = arg.toString();
			arg = client_rsa_enc.encrypt(arg) ;
		}
	}
	 
	return arg;
}
function getDecryptedData(arg)
{
	if(arg==null)
		return arg ;
	var temp = arg.split("_separator_");
	arg = "";
	for(var j=0;j<temp.length;j++)
	{
		arg += client_rsa_dec.decrypt(temp[j]);
	}
	return arg;
}
/*var service_url = rest_uri+"rest/remove/"+sessionId;

    		$.ajax({
    			url: service_url,
    			type: "POST",
    			//contentType: 'application/json',
    			//data:jsonData,
    			success: function(res)
    			{
    			if(res.indexOf("status") != -1)
    			{
    				var obj = JSON.parse(res);
    				res = obj.status;
    			}
    			if( isEncAllow == 'true' )
    			{
    				res = getDecryptedData(res);
    			}
    			result = res ;
    			console.log(res);
    			},
    			error:function()
    			{
    				document.getElementById("txtViewerId1").value="there was an error while deleting";
    			}   
    		});*/
function getPublicKey(viewerURL)
{
	//isHTML="false";
	var res = null ;
	$.ajax({
		url: viewerURL + "/fetchPublicKey",
		crossDomain: true,
		async: false,
		success: function (data) {
		res = data;
	}
	});
	res = atob(res);
	publicKey = res.split("_separator_")[0];
	isEncAllow = res.split("_separator_")[1];
	client_rsa_enc.setPublic(publicKey, '10001');
	//return atob(publicKey);
}
function getPublicKeyByRest(viewerURL)
{
	var result ;
	var service_url = viewerURL+ "/server/fetchPublicKey" ;
		var jsonData='{"ecm":"sharepoint"}';
	 $.ajax({
         url: service_url ,
         type: "POST",
         contentType: 'application/json',
 		 data:jsonData,
         crossDomain: true,
         async: false,
         success: function (data) 
         {
				result = data;
         }
     });
	result = atob(result);
	publicKey = result.split("_separator_")[0];
	isEncAllow = result.split("_separator_")[1];
	client_rsa_enc.setPublic(publicKey, '10001');
}