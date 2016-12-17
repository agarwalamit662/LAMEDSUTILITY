/*version=1.2*/
isEmbedded = true; isViewerRunningOnCloud = false, repositoryName = 'filesystem', isViewerRunningOnCloud = false;
var isToolkit=true;
function MSTViewer(url,_sessionId) {
    this.viewerUrl = url;
	this._sessionId = _sessionId;
    var firstCall = true;
    var jsLoadCount = 0;
    var jsTotalCount = 0;
    var isViewerLoaded = false;
    var getViewerElement = function getViewerElementById(id) {
        var elem = document.getElementById(id);
        if (elem == null) {
            try {
                if (document.getElementById("ieViewerFrame") != null) {
                    elem = document.getElementById("ieViewerFrame").contentWindow.document.getElementById(id);
                }
            }
            catch (msg) {
            }
        }

        return elem;
    }
    var coreScriptLoader = function coreLoader() {
        var eViewerUrl = url + "/web/compatibility.js";

        var jsElm = document.createElement("script");
        jsElm.type = "application/javascript";
        jsElm.src = eViewerUrl;
        jsElm.addEventListener('load', function () {
            utilScriptLoader(); //scriptLoader();
        });

        //if (document.getElementById("ieViewerFrame") != null)
        //    document.getElementById("ieViewerFrame").contentWindow.document.getElementsByTagName("head")[0].appendChild(jsElm);
        //else
        document.getElementsByTagName("head")[0].appendChild(jsElm);
    }

    this.isViewerInitialized = function () {
        if (jsTotalCount == jsLoadCount && isViewerLoaded)
            return true;
        else
            return false;
    }

    var utilScriptLoader = function utilLoadScripts() {
        var eViewerUrl = url + "/src/shared/util.js";

        var jsElm = document.createElement("script");
        jsElm.type = "application/javascript";
        jsElm.src = eViewerUrl;
        jsElm.addEventListener('load', function () {
            objScriptLoader(); //scriptLoader();
        });

        //if (document.getElementById("ieViewerFrame") != null)
        //    document.getElementById("ieViewerFrame").contentWindow.document.getElementsByTagName("head")[0].appendChild(jsElm);
        //else
        document.getElementsByTagName("head")[0].appendChild(jsElm);
    }

    var objScriptLoader = function objLoadScripts() {
        var eViewerUrl = url + "//src/core/obj.js";

        var jsElm = document.createElement("script");
        jsElm.type = "application/javascript";
        jsElm.src = eViewerUrl;
        jsElm.addEventListener('load', function () {
            scriptLoader();
        });

        document.getElementsByTagName("head")[0].appendChild(jsElm);
    }

    var scriptLoader = function loadScripts() {
        if (isViewerLoaded == false) {
            setTimeout(function () { scriptLoader() }, 200); return;
        }
        var eViewerUrl = url + "";
        var files = [
             		eViewerUrl + '/external/webL10n/l10n.js',
                     eViewerUrl + '/src/display/font_loader.js',
                     eViewerUrl + '/src/display/api.js',
                     eViewerUrl + '/src/display/canvas.js',
                     eViewerUrl + '/src/shared/function.js',
                     eViewerUrl + '/src/core/charsets.js',
                     eViewerUrl + '/src/shared/annotation.js',
                     eViewerUrl + '/src/shared/colorspace.js',
                     eViewerUrl + '/src/core/crypto.js',
                     eViewerUrl + '/src/core/evaluator.js',
                     eViewerUrl + '/src/core/fonts.js',
                     eViewerUrl + '/src/core/glyphlist.js',
                     eViewerUrl + '/src/core/image.js',
                     eViewerUrl + '/src/core/metrics.js',
                     eViewerUrl + '/src/core/parser.js',
                     eViewerUrl + '/src/shared/pattern.js',
                     eViewerUrl + '/src/core/stream.js',
                     eViewerUrl + '/src/core/worker.js',
                     eViewerUrl + '/external/jpgjs/jpg.js',
                     eViewerUrl + '/src/core/jpx.js',
                     eViewerUrl + '/src/core/jbig2.js',
                     eViewerUrl + '/src/core/bidi.js',
                     eViewerUrl + '/src/core/cidmaps.js',
                     eViewerUrl + '/src/MSBrowseDetect.js',
                     eViewerUrl + '/src/MSTEvent.js',
                     eViewerUrl + '/web/debugger.js',
                     eViewerUrl + '/web/viewer.js',
                     eViewerUrl + '/web/base64.js',
                     eViewerUrl + '/web/menu.js',
                     eViewerUrl + '/src/MSTPreferences.js',
                     eViewerUrl + '/src/MSCustomizeGUI.js',
                     eViewerUrl + '/src/DragAndDrop/DragAndDrop.js',
                     eViewerUrl + '/web/pdf_find_bar.js',
                     eViewerUrl + '/web/pdf_find_controller.js',
                     eViewerUrl + '/web/ui_utils.js',
                     eViewerUrl + '/web/presentation_mode.js',
                     eViewerUrl + '/web/default_preferences.js',
                     eViewerUrl + '/web/preferences.js',
                     eViewerUrl + '/web/page_view.js',
                     eViewerUrl + '/web/thumbnail_view.js',
                     eViewerUrl + '/web/text_layer_builder.js',
                     eViewerUrl + '/web/settings.js',
                     eViewerUrl + '/web/MSTTreeView.js',//added for tree view support
                     eViewerUrl + '/src/MSTAnnotations/MSTBookmark.js',//added for Boksmarks
                     eViewerUrl + '/web/jquery.rightClick.js',	//added for Drag and Drop
                     eViewerUrl + '/web/jquery-1.4.2.min.js',
                     eViewerUrl + '/src/MSTAnnotations/jquery-ui.min.js',
             		eViewerUrl + '/src/MSTAnnotations/tabber.js',
             		eViewerUrl + '/src/MSTAnnotations/MSBrowseDetect.js',
            		eViewerUrl + '/src/MSTAnnotations/MSTAnnotation.js',
            		eViewerUrl + '/src/MSTAnnotations/MSTRectAnn.js',
            		eViewerUrl + '/src/MSTAnnotations/MSTLineAnn.js',
            		eViewerUrl + '/src/MSTAnnotations/MSTCircleAnn.js',
            		eViewerUrl + '/src/MSTAnnotations/MSTArrowAnn.js',
            		eViewerUrl + '/src/MSTAnnotations/MSTPenAnn.js',
            		eViewerUrl + '/src/MSTAnnotations/MSTHighlightAnn.js',
            		eViewerUrl + '/src/MSTAnnotations/MSTTextAnn.js',
            		eViewerUrl + '/src/MSTAnnotations/MSTStampAnn.js',
            		eViewerUrl + '/src/MSTAnnotations/MSTStampDialogFrames.js',
            		eViewerUrl + '/src/MSTAnnotations/MSTAnnotationImageOperations.js',
            		eViewerUrl + '/src/MSTAnnotations/MSTCopyPasteAnnotation.js',
            		eViewerUrl + '/src/MSTAnnotations/MSTAnnotationEvents.js',
            		eViewerUrl + '/src/MSTAnnotations/MSTPropertyEditing.js',
            		eViewerUrl + '/src/MSTAnnotations/MSTZoomAnnotations.js',
            		eViewerUrl + '/src/MSTAnnotations/MSTRotateAnnotations.js',
            		eViewerUrl + '/src/MSTAnnotations/MSTSaveAnnotations.js',
            		eViewerUrl + '/src/MSTAnnotations/MSTLoadAnnotations.js',
            		eViewerUrl + '/src/MSTAnnotations/MSTPolygonAnn.js',
            		eViewerUrl + '/src/MSTAnnotations/MSTPolyLineAnn.js',
            		eViewerUrl + '/src/MSTAnnotations/MSTPrintDocument.js',
            		eViewerUrl + '/src/MSTAnnotations/MSTUserAnnotationLayers.js',
            		eViewerUrl + '/src/MSTAnnotations/MSTMovePropertyToolbar.js',
            		eViewerUrl + '/src/MSTAnnotations/MSTDragAndDropAnnotations.js',
            		eViewerUrl + '/src/MSTAnnotations/MSTDrawRedactionAsRect.js',
            		eViewerUrl + '/src/MSTAnnotations/MSTHandleAnnInSplitView.js',
            		eViewerUrl + '/src/MSCustomizeGUI.js',
            		eViewerUrl + '/src/MSTAnnotations/MSTSetAnnotationAsDefault.js',
            		eViewerUrl + '/src/MSTAnnotations/MSTBookmark.js',
            		eViewerUrl + '/src/MSTAnnotations/MSTWaterMark.js',
            		eViewerUrl + '/src/redaction.js'];

        jsTotalCount = files.length;

        for (var i = 0; i < files.length; i++)
            js(files[i]);

        var files = [eViewerUrl + '/web/viewer.css'
 		            , eViewerUrl + '/web/jquery.contextMenu.css', eViewerUrl + '/web/viewer_files/css3menu7/style.css', eViewerUrl + '/web/viewer_files/css3menu8/style.css', eViewerUrl + '/web/viewer.css',
 		            eViewerUrl + '/web/AudioVideo.css', eViewerUrl + '/src/MSTAnnotations/jquery-u.css', eViewerUrl + '/web/viewer_files/stampCSS/newStampCSS.css', eViewerUrl + '/web/viewer_files/stampCSS/printDocAndAnn.css',
 		            eViewerUrl + '/web/viewer_files/stampCSS/AnnPropToolBar.css', eViewerUrl + '/src/MSTPreferences.js', eViewerUrl + '/web/viewer_files/stampCSS/Prefereces.css',
 		            eViewerUrl + '/src/MetaData/MetaData.css', eViewerUrl + '/web/viewer_files/stampCSS/bookmark.css', eViewerUrl + '/web/viewer_files/stampCSS/watermark.css'];
         for (var i = 0; i < files.length; i++)
             css(files[i]);
    }

    var js = function loadJS(file) {
        var jsElm = document.createElement("script");
        jsElm.type = "application/javascript";
        jsElm.src = file;
        jsElm.addEventListener('load', function () {
            jsLoadCount = jsLoadCount + 1;
        });
        document.getElementsByTagName("head")[0].appendChild(jsElm);
    }

    var worker_loader = function loadWorkerLoader(text) {
        var jsElm = document.createElement("script");
        jsElm.type = "application/javascript";
        jsElm.text = text;
        document.getElementsByTagName("head")[0].appendChild(jsElm);
    }

    var css = function loadCSS(file, parent) {
        var fileref = document.createElement("link");
        fileref.setAttribute("rel", "stylesheet");
        fileref.setAttribute("type", "text/css");
        fileref.setAttribute("href", file);
        //if (file == url + "/web/viewer_files/stampCSS/watermark.css")
        //		  {
        //			  fileref.addEventListener('load', function() {
        //				  isViewerLoaded=true;
        //				  
        //			  });
        //		  }
        try{
        	if (parent.tagName == "IFRAME")
        		parent.contentWindow.document.getElementsByTagName("head")[0].appendChild(fileref);
        	else
        		document.getElementsByTagName("head")[0].appendChild(fileref);
        }
        catch(err){
        	document.getElementsByTagName("head")[0].appendChild(fileref);
        }
    }

    coreScriptLoader();
    //scriptLoader();

    var loadDocumentInternal = function initMSTViewer(sessionId) {
        worker_loader("PDFJS.workerSrc = '" + url + "/src/worker_loader.js'");

        if (!(jsTotalCount == jsLoadCount)) {
            setTimeout(function () { loadDocumentInternal(sessionId) }, 200); return;
        }

        //try {
        //    if (firstCall)
        //        draw();
        //}
        //catch (e) {
        //    alert(e);
        //}
        if (!firstCall)
            PDFView.pageRotationsValue = [];
        firstCall = false;
        //addButtons();

        var repository = "filesystem";
        if (repository == "filesystem") {
            var fileMimeType1 = "image/pdf";
            var nameOfFile1 = sessionId + ".pdf";
            var isViewerRunningOnCloud = "false";
            var base64 = "disabled";
            if (base64 == 'disabled')
                nameOfFile1 = Base64.encode(nameOfFile1);
            localStorage.fileName = nameOfFile1;
            selectedFileName = nameOfFile1;
            openFileSystemFiles(fileMimeType1, nameOfFile1, isViewerRunningOnCloud);
        }

    }

    function openFileSystemFiles(fileMimeType1, nameOfFile1, isViewerRunningOnCloud) {
    	var userName='Anonymous';
    	g_StrViewerUrl=viewer.viewerUrl;
    	if (window.XMLHttpRequest)
      	{
      		decodeDocumentHttp = new XMLHttpRequest()
      	}
      	else if (window.ActiveXObject)
      	{
      		decodeDocumentHttp = new ActiveXObject("Microsoft.XMLHTTP")
      	}


      	var	name=nameOfFile1;
      	
      	decodeDocumentHttp.open("POST",viewer.viewerUrl+"/MSOpenWorklistFile", true);
      	decodeDocumentHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
      	decodeDocumentHttp.send("&filenametoget="+name+"&fileMimeType="+fileMimeType1);
      	
      	decodeDocumentHttp.onload = function(e) {
      		
      					if (this.status == 200) {
      		
      					var stt=decodeDocumentHttp.responseText;
    					var fileName=Base64.decode(nameOfFile1); 
      					
      					var sttt=fileName.lastIndexOf(".");
      				
      					var nFileN=fileName.substring(sttt);
      					var extn=nFileN;
      					
      					//if(!isViewerRunningOnCloud)
      					{
      						if(navigator.platform=="iPad")
      						{
      							windowLocation="iPadviewer.html?"+stt+"&"+extn+"&"+repositoryName+"&&&&"+userName+"&"+TrimString(name)+"&&&admin&";
      						}
      						else
      						{
      							if(repositoryName==undefined)
      								repositoryName='filesystem';
      							windowLocation="viewer.jsp?"+stt+"&"+extn+"&"+repositoryName+"&&&&"+userName+"&"+TrimString(name)+"&&&admin&";
      						 }
      					}
      					loadHTML5Viewer();
      					
      					initialize('viewerContainer');
      					
      					sessionID = viewer._sessionId; //sessionId.value;
      					var isAPICall="<%= isAPICall %>";
      				  	//if(isAPICall !="null")
      				  	{
      				  		isCallForComplete=true;
      				  		crateCallForCompleteDocument();
      	  				}
      		}
      	}
    }

    function addButtons() {
        var buttons = ['print', 'fullscreen', '#viewFind', 'sidebarToggle', 'viewThumbnail', 'viewOutline',
	              'previous', 'next', '.zoomIn', '.zoomOut', 'fullscreen', 'openFile', 'fileInput', 'download',
	              'pageNumber', 'scaleSelect', 'first_page', 'last_page', 'page_rotate_ccw', 'page_rotate_cw',
	              'downloadDocLink', 'downloadDoc', 'errorWrapper', 'Hide_Comments', 'numPages', 'rightPopUp', 'brightEditField', 'contrastEditField'
	              , 'viewBookmark', 'blockUI', 'errorMessage', 'errorClose', 'divclass1', '', '', 'multiUserNameList', 'divIFrameBack',
	              '', 'annAnchor1', 'File_div', 'borderClsChooserAnchor', 'annotationInfoDiv', 'findNext', 'blockBackContentDiv'];

        var strUIElements = "";
        for (var i = 0; i < buttons.length; i++) {
            strUIElements += addButton(buttons[i]);
        }

        strUIElements += addTextField('customScaleOption');
        strUIElements += addTextField('findInput');
        strUIElements += addCheckBox('hideThumb');
        strUIElements += addCheckBox('Hide_Annotation');
        strUIElements += addCheckBox('showOrHideAnn');
        //strUIElements += addCheckBox('textButton');
        return strUIElements;
    }

    function addButton(id) {
        var button = "<span id='" + id + "' style=display:none></span>"; // document.createElement("span");
        if(id=="numPages"){
        	button = "<span id='" + id + "' style=display:none;visibility:hidden></span>";
        }
        //button.style.display = 'none';
        //button.id = id;
        return button;
    }

    function addTextField(id) {
        var button = "<input type='text' id='" + id + "' style=display:none></input>"; // document.createElement("text");
        //   button.style.display = 'none';
        //   button.id = id;
        //  document.body.appendChild(button);
        return button;
    }
    function addCheckBox(id) {
        var button = "<input type='check' id='" + id + "' style=display:none></input>"; //document.createElement("checkbox");
        // button.style.display = 'none';
        // button.id = id;
        // document.body.appendChild(button);
        return button;
    }


    this.initialize = function (left, top, width, height, position, parent) {
        this.viewerLeft = left;
        this.viewerTop = top;
        this.viewerHeight = height;
        this.viewerWidth = width;
        var content = "<div style='position: absolute; left: 50px; top: -36px; margin-right: 10px;'><input type='text' readonly='readonly' class='extractingGUI2015'title='Fit to Height' alt='Submit'style='margin-left: 10px; width: 5px; height: 20px; background-position: -302px -118px;'onClick=fitToHeight();onMouseOver=onOver(this);onMouseOut=onOut(this);> <input type='text' readonly='readonly'class='extractingGUI2015' title='Fit to Window' alt='Submit'style='margin-left: 10px; width: 5px; height: 20px; background-position: -252px -118px;'onClick=fitToWidth();onMouseOver=onOver(this);onMouseOut=onOut(this);> <input type='text' readonly='readonly'class='extractingGUI2015' title='Zoom In' alt='Submit'style='margin-left: 10px; width: 5px; height: 20px; background-position: -103px -9px;'onClick=ZoomIn();onMouseOver=onOver(this);onMouseOut=onOut(this);><input type='text' readonly='readonly' class='extractingGUI2015'title='Zoom Out' alt='Submit'style='margin-left: 10px; width: 5px; height: 20px; background-position: -126px -9px;'onClick=ZoomOut();onMouseOver=onOver(this);onMouseOut=onOut(this);><input type='text' readonly='readonly' class='extractingGUI2015'title='Rotate Clockwise' alt='Submit'style='margin-left: 10px; width: 5px; height: 20px; background-position: -203px -9px;'onClick=rotateClockButton();onMouseOver=onOver(this);onMouseOut=onOut(this);> <input type='text' readonly='readonly'class='extractingGUI2015' title='Rotate Anticlockwise' alt='Submit'style='margin-left: 10px; width: 5px; height: 20px; background-position: -178px -9px;'onClick=rotateAntiClockButton();onMouseOver=onOver(this);onMouseOut=onOut(this);> <input type='text' readonly='readonly'class='extractingGUI2015' title='Print' alt='Submit'style='margin-left: 10px; width: 5px; height: 20px; background-position: -67px -39px;'onClick=print();onMouseOver=onOver(this);onMouseOut=onOut(this);><input type='text' id='pageNumber'style='width: 30px; text-align: center; height: 20px; background-color: #F8F8F8; border:1px solid #CFCFCF;'onkeydown=return checkPageInputValue(event);onKeyUp=checkPageInput(event);value='1' size='4' min='1' tabindex='7'></div><div id='outerContainer' class='loadingInProgress'><div id='sidebarContainer'><div id='sidebarContent'><div id='thumbnailView'></div><div id='outlineView' class='hidden'></div></div></div>  <!-- sidebarContainer --><div id='mainContainer'><div id='viewerContainer' tabindex='0' onmousemove='mouse_Dragged(event)' onmousedown='try{ mouse_Pressed(event); } catch(exp){ }' onmouseup='try { mouse_Released(event); } catch(exp) { }' ondblclick='try{ mouse_Double(event) } catch(exp) { }' ><div id='viewer'></div></div></div> <!-- mainContainer --><div id='loadingBox'><div id='loading'></div><div id='loadingBar'><div class='progress'></div></div></div><select id='scaleSelect' onChange='PDFView.parseScale(this.value);' style='visibility: hidden;' title='Zoom' oncontextmenu='return false;' tabindex='10' data-l10n-id='zoom'><option id='pageAutoOption' value='auto' selected='selected' data-l10n-id='page_scale_auto'>Automatic Zoom</option><option id='pageActualOption' value='page-actual' data-l10n-id='page_scale_actual'>Actual Size</option><option id='pageFitOption' value='page-fit' data-l10n-id='page_scale_fit'>Fit Page</option><option id='pageWidthOption' value='page-width' data-l10n-id='page_scale_width'>Full Width</option><option id='customScaleOption' value='custom'></option><option value='0.5'>50%</option><option value='0.75'>75%</option><option value='1'>100%</option><option value='1.25'>125%</option><option value='1.5'>150%</option><option value='2'>200%</option></select></div> <!-- outerContainer -->";
        var viewer_div = document.createElement("div");
        viewer_div.id = "MSTViewer_innerViewer";
        viewer_div.style.left = "" + left + "px";
        viewer_div.style.top = "" + (top) + "px";
        viewer_div.style.width = "" + (width) + "px";
        viewer_div.style.height = "" + (height) + "px";
        viewer_div.style.position = position;
        viewer_div.style.backgroundColor = '#333333';
        viewer_div.innerHTML += content;
        viewer_div.innerHTML += addButtons();

        var viewer_out = document.createElement("div");
        viewer_out.appendChild(viewer_div);

        if (parent.tagName == "IFRAME")
            parent.contentWindow.document.write(viewer_out.innerHTML)
        else
            parent.appendChild(viewer_div);

        //coreScriptLoader();

        var eViewerUrl = url + "";
        var files = [
            //eViewerUrl + '/ToolKit/toolkit.css',
            eViewerUrl + '/web/viewer.css',
            eViewerUrl + '/web/menu.css',
            eViewerUrl + '/web/viewer_files/css3menu7/style.css',
            eViewerUrl + '/web/viewer_files/stampCSS/newStampCSS.css',//for annotation module
            eViewerUrl + '/web/viewer_files/css3menu8/style.css'];

        for (var i = 0; i < files.length; i++)
            css(files[i], parent);

        addStrikeOutDiv();
        isViewerLoaded = true;
		
		var styleqwerty = document.createElement('style');
        styleqwerty.type = 'text/css';
        styleqwerty.innerHTML = ".extractingGUI2015 {color:#F00;background-image: url('" + eViewerUrl+"/web/gui2015/icons.png');background-repeat: no-repeat;border: none;padding: 3px;width:5px;font-size: 1em;padding-left:20px;cursor:pointer;background-color:#F8F8F8  ;border:1px solid #F8F8F8;}.extractingGUI2015:hover{border: 1px solid #adadad  ;}#viewer{border:1px solid #808080  ;}#viewerContainer {top: 1px !important;color:red;}#outerContainer{background-color:#808080;}";

    	document.getElementsByTagName('head')[0].appendChild(styleqwerty);
		
        //addStrikeOutDiv();
    };
    this.loadDocument = function (_sessionId) {
		this._sessionId = _sessionId;
        setTimeout(function () { loadDocumentInternal(_sessionId) }, 500);

    };

    this.zoomIn = function () {
        PDFView.zoomIn();
    }
    this.zoomOut = function () {
        PDFView.zoomOut();
    }
    this.zoom = function (scale) {
        customZoom(scale);
    }
    this.rotate = function rotatePage(code) {
        if (code == 90)
        	PDFView.rotatePages(90);
        else
        	PDFView.rotatePages(-90);
    }
    this.closeDocument = function close(sessionId) {
        var eViewerHTML = new eViewerHTML5();
        eViewerHTML.dropDocument(viewer.viewerUrl, sessionId);
        PDFView.pages = null;
        //var content = "<div id='outerContainer' class='loadingInProgress'><div id='sidebarContainer'><div id='toolbarSidebar'><div class='splitToolbarButton toggled'><button id='viewThumbnail' class='toolbarButton group toggled' title='Show Thumbnails' tabindex='2' data-l10n-id='thumbs'><span data-l10n-id='thumbs_label'>Thumbnails</span></button><button id='viewOutline' class='toolbarButton group' title='Show Document Outline' tabindex='3' data-l10n-id='outline'><span data-l10n-id='outline_label'>Document Outline</span></button></div></div><div id='sidebarContent'><div id='thumbnailView'></div><div id='outlineView' class='hidden'></div></div></div>  <!-- sidebarContainer --><div id='mainContainer'><div id='viewerContainer' tabindex='0'><div id='viewer'></div></div></div> <!-- mainContainer --><div id='loadingBox'><div id='loading'></div><div id='loadingBar'><div class='progress'></div></div></div><select id='scaleSelect' onChange='PDFView.parseScale(this.value);' style='visibility: hidden;' title='Zoom' oncontextmenu='return false;' tabindex='10' data-l10n-id='zoom'><option id='pageAutoOption' value='auto' selected='selected' data-l10n-id='page_scale_auto'>Automatic Zoom</option><option id='pageActualOption' value='page-actual' data-l10n-id='page_scale_actual'>Actual Size</option><option id='pageFitOption' value='page-fit' data-l10n-id='page_scale_fit'>Fit Page</option><option id='pageWidthOption' value='page-width' data-l10n-id='page_scale_width'>Full Width</option><option id='customScaleOption' value='custom'></option><option value='0.5'>50%</option><option value='0.75'>75%</option><option value='1'>100%</option><option value='1.25'>125%</option><option value='1.5'>150%</option><option value='2'>200%</option></select></div> <!-- outerContainer -->";
        //MSTViewer_innerViewer.innerHTML = content;
        PDFView.pageRotationsValue = [];
    }

    this.thumbnail = function thumbnails(code) {
        if (code == "1")
            displayThumb(true);
        else
            displayThumb(false);
    }

    this.rubberBandZoon = function () {
        addRubberBandZoom();
    }

    this.pageNavigation = function navigate(code) {
        if (code == 1)
            PDFView.page = 1;
        else if (code == 3)
            PDFView.page = PDFView.page + 1;
        else if (code == 4)
            PDFView.page = PDFView.page - 1;
        else if (code == 2)
            PDFView.page = PDFView.pdfDocument.numPages;

    }

    function addStrikeOutDiv() {
        var content = "<div id='NoteDialog' style='visibility:hidden;position:absolute;background-color:#CCCCCC; z-index: 25000; width: 240px' >  <table bordercolor='#CCCCCC'  border='4'  id=''>  <tbody>  <tr style='' onMouseDown='dragStart(event, this.id)'>  <td class='tx_ tx_nosel' style='padding-bottom: 0px; margin: 0px; padding-left: 0px; padding-right: 0px; font-family: Arial,Verdana,Geneva,Helvetica,sans-serif; color: black; font-size: 8pt; padding-top: 0px; khtmluserselect: none; mozuserselect: none; select: none;' unselectable='on' onMouseDown='noteDialogPress(event)' onMouseMove='noteDialogMove(event)' onMouseUp='noteDialogRelease()'>  	<table class='tx_' style=' width: 100%; border-collapse: separate; font-family: Arial,Verdana,Geneva,Helvetica,sans-serif; color: black; font-size: 8pt;' cellSpacing='0' cellPadding='0' >  		<tbody>  		    <tr style=''>  			   <td class='tx_' vAlign='top' style='padding-bottom: 0px;  margin: 0px; padding-left: 0px; width: 4px; padding-right: 0px; font-family: Arial,Verdana,Geneva,Helvetica,sans-serif; color: black; font-size: 8pt; padding-top: 0px; cursor: pointer; khtmluserselect: none;' onClick=\"showColors('colorTable');\" >  			   <img src="+url+"/web/resources/colors.jpg width='20' height='20'>  			   </td>  			     			   <td class='tx_' vAlign='top' style='padding-bottom: 0px;  margin: 0px; padding-left: 2px; width: 6px; padding-right: 0px; font-family: Arial,Verdana,Geneva,Helvetica,sans-serif; color: black; font-size: 8pt; padding-top: 0px; cursor: pointer; khtmluserselect: none;' onClick=\"showColors('colors');\" id='strike' >  			  <img src="+url+"/web/resources/strike.jpg width='20' height='20' id='strikeimg'>  			   </td>  			    			    <td class='tx_' vAlign='top' style='padding-bottom: 0px;  margin: 0px; padding-left: 10px;  padding-right: 0px; padding-top:5px; font-family: Arial,Verdana,Geneva,Helvetica,sans-serif; color: black; font-size: 8pt;  text-align:justify; height:70% ' height='71%' id='textPart'>  			   sdasdsad...  			   </td>  			     			  <td class='tx_' vAlign='top' style='padding-bottom: 0px;  margin: 0px; padding-left: 0px; width: 4px; padding-right: 0px; font-family: Arial,Verdana,Geneva,Helvetica,sans-serif; color: black; font-size: 8pt; padding-top: 0px; cursor: pointer; khtmluserselect: none;' onClick=\"update(\'Delete\',event)\" >  			  <img src="+url+"/web/resources/delete.gif>  			  </td>  			</tr>  		</tbody>  	</table>    </td>  </tr>  	  	    <tr style=''>     <td style=' padding-left: 2px; padding-right: 2px;' >     <div class='tx_' style='width: 100%; font-family: Arial,Verdana,Geneva,Helvetica,sans-serif; height: 100%; color: black; font-size: 8pt;' >     <textarea class='tx_' style='border-bottom: rgb(253,253,255) 1px solid;  padding-bottom: 0px; line-height: 14px; background-color: rgb(255,255,204); MARGIN-TOP: 0px; padding-left: 0px; width: 100%; padding-right: 0px; font-family: Arial,Verdana,Geneva,Helvetica,sans-serif; margin-bottom: 0px; height: 100%; color: #000000; font-size: 11px; border-top: rgb(181,181,196) 1px solid; border-right: rgb(253,253,255) 1px solid; padding-top: 0px; resize:none; ' rows='6' id='textarea'>     </textarea>     </div>     </td>  </tr>            	  	  	  	  <tr>  	<td styrle='padding-bottom: 0px; margin: 0px; padding-left: 0px; padding-right: 0px; font-family: Arial,Verdana,Geneva,Helvetica,sans-serif; color: black; font-size: 8pt; padding-top: 0px; khtmluserselect: none; mozuserselect: none; select: none;'>  	<div class='tx_ tx_nosel' style='border-bottom: rgb(253,253,255) 1px solid; border-left: rgb(181,181,196) 1px solid;  background-color: rgb(229,229,244);  font-family: Arial,Verdana,Geneva,Helvetica,sans-serif; height: 55px; color: black; font-size: 8pt; border-top: rgb(181,181,196) 1px solid; border-right: rgb(253,253,255) 1px solid; khtmluserselect: none; mozuserselect: none; select: none; overflow: auto; width: 225px;  ' unselectable='on' id='tags'>  		  		<span class='tx_ tx_nosel' style=' margin: 2px; padding-left: 2px; padding-right: 2px; display: block; font-family: Arial,Verdana,Geneva,Helvetica,sans-serif; float: left; color: #606060; font-size: 8pt; cursor: pointer; khtmluserselect: none; mozuserselect: none; select: none; cssfloat: left;'  unselectable='on' onClick='selectTag(event)' id='tag1' >  		correction  		</span>  		  		<span class='tx_ tx_nosel' style=' margin: 2px; padding-left: 2px; padding-right: 2px; display: block; font-family: Arial,Verdana,Geneva,Helvetica,sans-serif; float: left; color: #606060; font-size: 8pt; cursor: pointer; khtmluserselect: none; mozuserselect: none; select: none; cssfloat: left;'  unselectable='on' onClick='selectTag(event)' id='tag2' >  		suggestion  		</span>  		  		<span class='tx_ tx_nosel' style=' margin: 2px; padding-left: 2px; padding-right: 2px; display: block; font-family: Arial,Verdana,Geneva,Helvetica,sans-serif; float: left; color: #606060; font-size: 8pt; cursor: pointer; khtmluserselect: none; mozuserselect: none; select: none; cssfloat: left;'  unselectable='on' onClick='selectTag(event)' id='tag3' >  		fixed  		</span>  	  		<span class='tx_ tx_nosel' style='margin: 2px; padding-left: 2px; padding-right: 2px; display: block; font-family: Arial,Verdana,Geneva,Helvetica,sans-serif; float: left; color: #606060; font-size: 8pt; cursor: pointer; khtmluserselect: none; mozuserselect: none; select: none; cssfloat: left;'  unselectable='on' onClick='selectTag(event)' id='tag4' >  		important  		</span>  	</div>  	</td>  </tr>    <tr>     <td class='tx_ tx_nosel' style='padding-bottom: 0px; margin: 0px; padding-left: 0px; padding-right: 0px; font-family: Arial,Verdana,Geneva,Helvetica,sans-serif; color: black; font-size: 8pt; vertical-align: top; padding-top: 0px; khtmluserselect: none; mozuserselect: none; select: none;' unselectable='on'>  	  	<div class='tx_ tx_nosel' style='z-index: auto; position: relative; display: block; font-family: Arial,Verdana,Geneva,Helvetica,sans-serif;  color: black; font-size: 8pt; khtmluserselect: none; mozuserselect: none; select: none;' unselectable='on'/>  			<table width='100%' class='tx_' style=' width: 100%; border-collapse: separate; font-family: Arial,Verdana,Geneva,Helvetica,sans-serif; color: black; font-size: 8pt;' cellSpacing='0' cellPadding='0' >  				<tbody>  					<tr>  						<td width='98%' >  							<input propdescname='noname' title='Enter new tags here ' style='border-bottom: rgb(253,253,255) 1px solid; border-left: rgb(181,181,196) 1px solid; padding-bottom: 2px; background-color: rgb(229,229,244); MARGIN: 0px; padding-left: 4px; width: 98%; color: rgb(94,94,103); FONT-SIZE: 8pt; border-top: rgb(181,181,196) 1px solid; border-right: rgb(253,253,255) 1px solid; padding-top: 2px;' value='New tag' id='newTag' onClick='this.value='''/>  						</td>  						<td align='left' class='tx_ tx_nosel' style='padding-bottom: 0px; margin: 0px; padding-left: 0px; padding-right: 0px; font-family: Arial,Verdana,Geneva,Helvetica,sans-serif; color: black; font-size: 8pt; padding-top: 0px; khtmluserselect: none; mozuserselect: none; select: none;' unselectable='on'>  							<div class='tx_ tx_nosel' style='border-bottom: rgb(181,181,196) 1px solid; text-align: center; border-left: rgb(229,229,244) 1px solid; padding-bottom: 1px; background-color: rgb(205,205,220); MARGIN: 0px; padding-left: 4px; padding-right: 4px; display: block; font-family: Arial,Verdana,Geneva,Helvetica,sans-serif; color: black; font-size: 8pt; border-top: rgb(229,229,244) 1px solid; cursor: pointer; border-right: rgb(181,181,196) 1px solid; padding-top: 1px; khtmluserselect: none; mozuserselect: none; select: none;' onClick='addNewTag()'>  								<div class='tx_ tx_nosel' style='position: relative; width: 13px; display: block; font-family: Arial,Verdana,Geneva,Helvetica,sans-serif; height: 13px; color: black; font-size: 8pt; overflow: hidden; top: 0px; left: 0px; khtmluserselect: none; mozuserselect: none; select: none;'>  									<img src="+url+"/web/resources/droplist.jpg height='20'/>  								</div>  							</div>  						</td>  					</tr>  				</tbody>  			</table>  		</div>  	</td>  </tr>  			      <tr style='background-color: rgb(229,229,244);'>  	<td>  	<table class='tx_' style=' width: 100%; border-collapse: separate; font-family: Arial, Verdana, Geneva, Helvetica, sans-serif; color: black; font-size: 8pt;' cellSpacing='0' cellPadding='0'>  	   <tbody>  	   	<tr>  			<td>  			<div title='Save the note and tags (Ctrl+Enter)' class='tx_ tx_nosel' style='border-bottom: rgb(181,181,196) 1px solid; text-align: center; border-left: rgb(229,229,244) 1px solid; padding-bottom: 1px; background-color: rgb(205,205,220); MARGIN: 0px; padding-left: 4px; padding-right: 4px; display: block; font-family: Arial,Verdana,Geneva,Helvetica,sans-serif; color: #000000; font-size: 8pt; border-top: rgb(229,229,244) 1px solid; cursor: pointer; border-right: rgb(181,181,196) 1px solid; padding-top: 1px; khtmluserselect: none; mozuserselect: none; select: none;' unselectable='on' onClick='addComment()' >  			Save  			</div>  			</td>  			<td style='text-align:center'>  				<div id='cancelnote' title='Cancel this note' class='tx_ tx_nosel' style='border-bottom: rgb(181,181,196) 1px solid; text-align: center; border-left: rgb(229,229,244) 1px solid; padding-bottom: 1px; background-color: rgb(205,205,220); MARGIN: 0px; padding-left: 4px; padding-right: 4px; display: block; font-family: Arial,Verdana,Geneva,Helvetica,sans-serif; color: #000000; font-size: 8pt; border-top: rgb(229,229,244) 1px solid; cursor: pointer; border-right: rgb(181,181,196) 1px solid; padding-top: 1px; khtmluserselect: none; mozuserselect: none; select: none;' unselectable='on' onClick=\"update('Delete',event);\">  				Cancel  				</div>  			</td>  		</tr>  	   </tbody>  	   </table>  	</td>  </tr>          </tbody>  </table>  </div>"
        var strike = document.createElement("div");
        strike.innerHTML += content;
        document.body.appendChild(strike);

        content = "<div style=' visibility:hidden;position:absolute; z-index: 25000' id='colors' >  <table class='tx_' style='background-color: #f0f0f0; width: 100%; border-collapse: separate; font-family: Arial,Verdana,Geneva,Helvetica,sans-serif; color: black; font-size: 8pt; width:70px' cellSpacing='2' cellPadding='1'  >  	<tbody>  		  		<tr>  			<td >  				<div style='border:thin solid #ffffff; text-align: left; background-color: #f0f0f0; padding-left: 4px; display: block; font-family: Arial,Verdana,Geneva,Helvetica,sans-serif; color: #000000; font-size: 8pt; cursor: pointer; khtmluserselect: none; mozuserselect: none; select: none;' unselectable='on' onClick=\"update('Strike',event);\" onMouseOver='focus1(this);' onMouseOut='unfocus1(this);'>  					Strikeout  				</div>  			</td>  		</tr>  		  		<tr>  			<td >  				<div class='tx_ tx_nosel' style='border:thin solid #ffffff; text-align: left; background-color: #f0f0f0; padding-left: 4px; display: block; font-family: Arial,Verdana,Geneva,Helvetica,sans-serif; color: #000000; font-size: 8pt; cursor: pointer; khtmluserselect: none; mozuserselect: none; select: none;' unselectable='on' onClick=\"update('Yellow',event);\" onMouseOver='focus1(this);' onMouseOut='unfocus1(this);' >  					Highlight  				</div>  			</td>  		</tr>  		  		<tr>  			<td >  				<div  style='border:thin solid #ffffff; background-color: rgb(255,255,101); khtmluserselect: none; mozuserselect: none; select: none; height:15px; cursor: pointer; khtmluserselect: none; ' unselectable='on' onClick=\"update('Yellow',event);\" onMouseOver='focus1(this);' onMouseOut='unfocus1(this);' />   			</td>  		</tr>  		<tr>  			<td >  				<div  style=' background-color: rgb(101,255,101); khtmluserselect: none; mozuserselect: none; select: none; height:15px; cursor: pointer; khtmluserselect: none;' unselectable='on' onClick=\"update('Green',event);\" onMouseOver='focus1(this);' onMouseOut='unfocus1(this);' />  			</td>  		</tr>  		<tr>  			<td >  				<div style='height:15px; background-color: rgb(101,255,255); khtmluserselect: none; mozuserselect: none; select: none; cursor: pointer; khtmluserselect: none;' unselectable='on' onClick=\"update('Blue',event);\"  onMouseOver='focus1(this);' onMouseOut='unfocus1(this);'/>  			</td>  		</tr>  		<tr>  			<td >  				<div style='height:15px; background-color: #CC3300; khtmluserselect: none; mozuserselect: none; select: none; cursor: pointer; khtmluserselect: none;' unselectable='on' onClick=\"update('Red',event);\"  onMouseOver='focus1(this);' onMouseOut='unfocus1(this);'/>  			</td>  		</tr>  	</tbody></table>  </div>";
        strike = document.createElement("div");
        strike.innerHTML += content;
        document.body.appendChild(strike);

        content = "<div style=' visibility:hidden;position:absolute; z-index: 25000 ' id='colorTable' >  <table class='tx_' style='background-color: #f0f0f0; width: 100%; border-collapse: separate; font-family: Arial,Verdana,Geneva,Helvetica,sans-serif; color: black; font-size: 8pt; width:70px' cellSpacing='2' cellPadding='1'  >  	<tbody>  		<tr>  			<td >  				<div  onClick=\"changeColor('#FFFFCC')\" style='background-color: #FFFFCC; khtmluserselect: none; mozuserselect: none; select: none; height:15px; cursor: pointer; khtmluserselect: none; ' unselectable='on'  />   			</td>  			<td >  				<div  onClick=\"changeColor('#66FFFF')\" style='background-color:#66FFFF; khtmluserselect: none; mozuserselect: none; select: none; height:15px; cursor: pointer; khtmluserselect: none; ' unselectable='on'  />   			</td>  			<td >  				<div  onClick=\"changeColor('#999999')\" style='background-color: #999999; khtmluserselect: none; mozuserselect: none; select: none; height:15px; cursor: pointer; khtmluserselect: none; ' unselectable='on'  />   			</td>  		</tr>  		<tr>  			<td >  				<div  onClick=\"changeColor('#FF99CC')\" style='background-color: #FF99CC; khtmluserselect: none; mozuserselect: none; select: none; height:15px; cursor: pointer; khtmluserselect: none;' unselectable='on'  />  			</td>  			<td >  				<div  onClick=\"changeColor('#CCCC99')\" style='background-color:#CCCC99; khtmluserselect: none; mozuserselect: none; select: none; height:15px; cursor: pointer; khtmluserselect: none;' unselectable='on'  />  			</td>  			<td >  				<div  onClick=\"changeColor('#9999FF')\" style='background-color:#9999FF; khtmluserselect: none; mozuserselect: none; select: none; height:15px; cursor: pointer; khtmluserselect: none;' unselectable='on'  />  			</td>  		</tr>  		<tr>  			<td >  				<div onClick=\"changeColor('#FF99FF')\" style='height:15px; background-color: #FF99FF; khtmluserselect: none; mozuserselect: none; select: none; cursor: pointer; khtmluserselect: none;' unselectable='on'   />  			</td>  			<td >  				<div  onClick=\"changeColor('#996699')\" style='background-color: #996699; khtmluserselect: none; mozuserselect: none; select: none; height:15px; cursor: pointer; khtmluserselect: none;' unselectable='on'  />  			</td>  			<td >  				<div  onClick=\"changeColor('#00FF99')\" style='background-color:#00FF99;  khtmluserselect: none; mozuserselect: none; select: none; height:15px; cursor: pointer; khtmluserselect: none;' unselectable='on'  />  			</td>  		</tr>  	  	</tbody>  </table>  </div>";
        strike = document.createElement("div");
        strike.innerHTML += content;
        document.body.appendChild(strike);

        content = "<div class='BlogTitle' style='visibility: hidden; position: absolute; width=0px; height: 0px; background-color: #c0c9c9; border: thin;' id='searchFrame'>  <div id='searchTitle' style='position: absolute; width: 250px; height: 20px; background-color: rgb(1,91,238);' onMouseDown=\"dragStart(event, 'searchFrame');\">  <center><span style='font-size: 14px; color: white;'>   Advanced Search</span></center>  <img id='closeSearch' src="+url+"/web/resources/delete.gif style='width: 20px; margin-left: 230px; position: absolute; top: 0px;' onMouseMove='focusImage(this)' onMouseOut='unFocusImage(this)' onClick='closeSearchWindow();'/>  </div>    <TABLE CELLSPACING='0' cellpadding='5' border='0' style='margin-top: 30px; margin-left: 10px; position: absolute; color: black;'>    <TR><TD VALIGN='top' align='left' nowrap                style='font-family:Arial; font-size:12px; margin-top: 100px'>      <label for='strSearch' accesskey='n'>Fi<u>n</u>d:</label><br>      <INPUT TYPE=TEXT SIZE=40 NAME='strSearch' id='strSearch'  style='width:150px;' onKeyPress='submit(event)'><br><br>      <INPUT TYPE=Checkbox SIZE=40 NAME=bMatchCase id='bMatchCase'>      <label for='bMatchCase'>Match case</label><br>      <INPUT TYPE=Checkbox SIZE=40 NAME=bMatchWord ID='bMatchWord'>      <label for='bMatchWord'>Match whole word only</label>     </td>    <td rowspan='2' valign='top'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;      <!-- button name='btnFind' accesskey='f' onClick='findtext();'          style='width:75px; height:22px; font-family:Tahoma;                 font-size:11px; margin-top:15px'><u>F</u>ind Next</button-->       <input type='button' id='btnSearch' value='Search' onClick='findtext();' style='width:60px; height:22px; font-family:Tahoma; font-size:11px; position: absolute; top: 15px; left: 170px;'/>   <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;    <button name='btnCancel' onClick='resetSearch();' style='width:60px; height:22px; font-family:Tahoma;font-size:11px; position: absolute; top: 45px; left: 170px;'>Reset</button>    <br>    </td>   </tr>  </table>  <span id='span' style=' visibility: hidden; color: red; font-size: 12px; font-family: sans-serif; position: absolute; top: 150px; margin-left: 10px; color: red; width: 240px; overflow: visible;' >Results</span>  </div></div>"
        strike = document.createElement("div");
        strike.innerHTML += content;
        document.body.appendChild(strike);

        /*content = "<div id='div' style='background-color: rgb(219,219,219); width: 240px; height: 320px;visibility: hidden; overflow: auto; position: absolute; top: 170px; margin-left: 10px'/>";
        strike = document.createElement("div");
        strike.innerHTML += content;
        searchFrame.appendChild(strike);*/

        content = "<div id='annTool' style=' display:none;' class='annTool'>  			<div id='stampHeader' class='stampHeader'>  				<font id='stampText' class='stampText'>Stamp</font>  			</div>  			<div id='stampPropertyTag' class='stampPropertyTag'>  				 <font id='stampPropertyText' class='stampPropertyText'>Stamp Properties</font>  			</div>  			<div id='stampTag' class='stampTag'>  				 <font id='stampPropertyText' class='stampPropertyText'>Stamp</font>  			</div>  			  			<div id='tableContainingDiv' class='tableContainingDiv'>  				<div id='totalStamps' class='totalStamps'>  					<table width='100%' id='tablePadd' cellpadding='0' cellspacing='0' >  						<tr >  							<td id='stampPreview' onClick='fn_cellClick(this)'> Stamp</td>  						</tr>  					</table>  				</div>  				<div id='newStampAndDelButton' class='newStampAndDelButton'>  					<br><br>  					<button id='newStampId' style='float:right;' type='button' onClick='newStampDiv()' >New Stamp</button>  					<br>  					<br>  					<button style='float:right;' type='button' onClick='deleteAddedStamps();'>Delete</button>  				</div>  			</div>  			<div id='stampPreviewDiv' class='stampPreviewDiv'>  				<div id='stampPreviewText' class='stampPreviewText'>  					<font id='stampPropertyText' class='stampPropertyText'>Stamp-Preview</font>  				</div>  				<div id='prevCanvasConatiningDiv' class='prevCanvasConatiningDiv'>  					<canvas id='previewCanavs' class='previewCanavs' height='50' width='120' style='' ></canvas>  				</div>  			</div>  			<div id='okORCancle' style='margin-top: 2px;'>  				<button style=' float: right;' type='button' onClick='closeStamp();'>Cancel</button>  				<button style=' float: right;' type='button' onClick='stampOK();'>Ok</button>  			</div>  		</div>";
        strike = document.createElement("div");
        strike.innerHTML += content;
        document.body.appendChild(strike);

        content = "<div id='newAnnTool' style=' display:none;' class='newAnnTool'>  			<div id='newStampHeader' class='newStampHeader'>  				<font id='newStampText' class='newStampText'>New Stamp</font>  			</div>  			<div id='newStampProp' class='newStampProp'>  				<div id='newStampPropTag' class='newStampPropTag'>  					<font class='newStampPropFont'>New Stamp Properties</font>  				</div>  				  				<div id='newStampSetting' class='newStampSetting'>  						<div >  							<div class='newStampTxtClrTag' >  								<font class='newStampTxtClrFont'>Text-Color</font>  							</div>  							<div id='newStampTextColor' class='newStampTextColor' onClick='activeNewStampTextClrChooser();' ></div>  								<div class='newStampColorChooser' id='newStampClrChooser'>  									<div style='background: black;margin-top:1px; margin-left:4px; width: 81px; height:14px;' onClick=\"addTextColor('black');\"></div>  									<div style='background: red; margin-top:1px;margin-left:4px; width: 81px; height:14px; ' onClick=\"addTextColor('red');\"></div>  									<div style='background: green; margin-top:1px; margin-left:4px; width: 81px; height:14px;' onClick=\"addTextColor('green');\"></div>  									<div style='background: blue; margin-top:1px; margin-left:4px; width: 81px; height:14px; ' onClick=\"addTextColor('blue');\"></div>  									<div style='background: yellow; margin-top:1px; margin-left:4px; width: 81px; height:14px;' onClick=\"addTextColor('yellow');\"></div>  								</div>  							  						</div>  						<div >  							<div class='newStampBorderTag' >  								<font class='newStampTxtClrFont'>Border-Width</font>  							</div>  							<div class='newStampBorderInput' >  								<input type='text' style='width:100px;height: 19px;' value='4' id='stampBorderWidth'>  							</div>  						</div>  						<!--<div >Border-Width<input id='stampBorderWidth' type='text' value='4' id='borderWidth' style='margin-left:30px; width: 90px;'></div>-->  				</div>  				<div id='newStampFillSetting' class='newStampFillSetting'>  					<div >  							<div class='newStampBgClrTag' >  								<font class='newStampTxtClrFont'>Background-Color</font>  							</div>  							<div id='newStampBgColor' onClick='avtiveStampBgClrChooser();' class='newStampTextColor' ></div>  							<div class='newStampColorChooser' id='newStampBgClrChooser'>  										<div style='background: black;margin-top:1px; margin-left:4px; width: 81px; height:14px;' onClick=\"addStampBgColor('black');\"></div>  									<div style='background: red; margin-top:1px;margin-left:4px; width: 81px; height:14px; ' onClick=\"addStampBgColor('red');\" ></div>  									<div style='background: green; margin-top:1px; margin-left:4px; width: 81px; height:14px;' onClick=\"addStampBgColor('green');\"></div>  									<div style='background: blue; margin-top:1px; margin-left:4px; width: 81px; height:14px; ' onClick=\"addStampBgColor('blue');\"></div>  									<div style='background: yellow; margin-top:1px; margin-left:4px; width: 81px; height:14px;' onClick=\"addStampBgColor('yellow');\"></div>  							</div>  						<div >  							<div class='newStampTransTag' >  								<font class='newStampTxtClrFont'>Transparent</font>  							</div>  							<div class='newStampBorderInput' >  								<input type='checkbox' style='margin-left: 85px;width: 15px; height: 15px;' name='annTransparent' id='stampTransparent'>  							</div>  						</div>  				</div>  			</div>  				<div id='selectTextOrImg' class='selectTextOrImg'>  					<div id='textOrImageTag' class='textOrImageTag'>  						<font class='newStampTxtClrFont'>Text/Image</font>  					</div>  					<div id='selectText' class='selectText'>  						<div>  							<div>  								<input onChange='enableOrDisable();' type='radio' name='textOrImg' checked='checked' id='textButton'/>Text  								<input onMouseDown='checkForClrChooser();' type='text'id='textStamp' style='margin-top:6px; width: 220px;float: right;'/>  							</div>  							<br>  						</div>  						<br>  						  					</div>  					<div id='selectImage' class='selectImage'>  						<div>  							<input onChange='enableOrDisable();' type='radio' name='textOrImg' id='imgButton'/>Image  							<input onMouseDown='checkForClrChooser();' type='text'id='newFilePath' style='margin-left:210px;margin-top:6px; width: 180px;'/>  							<input type='button' id='newImageChooser' name='imageChooser' value='......' onclick='popUpImageChooser();'>  							<!--<input type='file' name='file' id='file' accept='image/*' / style='margin-top:6px;float: right;color:'>  						--></div>  					</div>  				</div>  				<div id='newStampOkCanncle' class='newStampOkCanncle'>  					<button style=' float: right;' type='button' onClick='clearDiv();'>Cancel</button>  					<button style=' float: right;' type='button' onClick='stampOK1();'>Ok</button>  				</div>  		</div>  		</div>";
        strike = document.createElement("div");
        strike.innerHTML += content;
        document.body.appendChild(strike);

        content = "<div id='newImageStampChooser' style=' display:none;' class='newImageStampChooser'>  			<div id='stampHeader' class='stampHeader'>  				<font id='stampText' class='stampText'>Select Stamp Image</font>  			</div>  			  			<div id='stampImgFileName' style='width:99.5%;height:10%;margin: 1px'>  				<div class='stampImgFileName' >  					<font class='stampImgFontStyle'>File Name</font>  				</div>  				<input type='file' name='file' id='file' accept='image/*' / style='margin-top:6px;float: right;color:'>  			</div>  			<div id='stampImgPrevAndResize' style='width:99.5%;height:60%;margin: 1px'>  				<div id='stampImgPreview' class='stampImgPreview' style='width:35.5%;height:94%;float: left;margin: 1%'>  					<fieldset style='border-radius: 5px 5px 5px 5px;width:96%;height:94%;margin: 1%;'>  						<legend >  							Preview  						</legend>  						<canvas id='stampImgChooserpreview' width='175%' height='165%' style='border:1px solid red;margin: 2%;'>  						</canvas>  					</fieldset>  				</div>  				  				<div id='stampImgResize' class='stampImgResize' style='width:59.5%;height:94%;float:left;margin: 1%'>  					<fieldset style='width:96%;border-radius: 5px 5px 5px 5px;height:94%;margin: 0.5%;'>  						<legend >  							Resize  						</legend>  						<div style='width:97%;height:30%;margin:1%'>  							<div class='stampmResize' >  								<font class='stampResizeFont'>Width</font>  							</div>  							<input id='stampImgChooserWidth' onkeyup='checkForAspectRatio(event);' type='text' style='margin: 4% 3.5% 3.5%;width:30%;' />  						</div>  						<div style='width:97%;height:30%;margin:1%'>  							<div class='stampmResize' >  								<font class='stampResizeFont'>Height</font>  							</div>  							<input id='stampImgChooserHeight' onkeyup='checkForAspectRatio(event);' type='text' style='margin: 4% 3.5% 3.5%;width:30%;' />  						</div>  						<div style='width:97%;height:30%;margin:1%'>  							<input id='maintainAspectRation' onclick='maintainAspectRatio();' type='checkbox' style='margin: 4% 3.5% 3.5%;width:30%;float: left' />  							<div class='stampmResize' >  								<font class='stampResizeFont'>Maintain Aspect Ratio</font>  							</div>  						</div>  					</fieldset>  				</div>  			  			</div>  			<div id='StampImageReset' style='width:99.5%;height:10%;margin: 1px'>  				<input onclick='resetImageDimension();' style='margin:1% 70%;' type='button' value='Reset to original Size'/>  			</div>  			<div id='StampImageFinalDecision' style='width:99.5%;height:10%;margin: 1px'>  				<input type='button' onclick='cancelStampImgChooser();' value='Cancel' style='float: right;margin: 1%;'>  				<input type='button' onclick='okStampImgChooser();' value='Ok' style='float: right;margin: 1%;'>  			</div>  		  		</div>"
        strike = document.createElement("div");
        strike.innerHTML += content;
        document.body.appendChild(strike);

        content = "<div id='printingDialog' style=' display:none;height:148px' class='printingDialog'>  			<div id='printCaption' class='printCaption'>  				<div id='printIcon' style='border-right:1px solid #6D6A68; margin-left:2px;width:25px;height:20px;float:left;'>  					<img style='height: 18px;width: 22px;' src="+url+"/web/print1.png>  				</div>  				<div align='center' style='border-right:1px solid #6D6A68;;height:20px;width:195px;margin-left:26px;'>  					<font style='color: white;font-family: Book Antiqua;font-weight: bolder;margin-left: 3px;font-size: 16px;'>Print Document </font>  				</div>  				<div id='deleteIcon' style='margin-left:228px;margin-top:-20px;width:15px;height:20px;' onClick='cancelPrint();'>  					<img style='height: 20px;width: 18px;' src="+url+"/web/close.gif>  				</div>  			</div>  			<div id='changeAttribute1' style='height:127px;background-color: #F2F1F0'>  				<div id='printStart' style='height:25px;width100%;'>  					<label style='font-family: Constantia;font-size: 14px;margin-left: 4px;'>Start Page</label>  					<div id='printStartPageChooser' style='height:25px;width:120px;margin-left: 125px;margin-top: -18px;'>  						<input type='text' id='printStartPage' style='margin-top: 2px;width: 100%;' onKeyPress='return checkForValidKey(event);' />  					</div>  				</div>  				<div id='printEnd' style='height:25px;width100%; margin-top: 5px;'>  					<label style='font-family: Constantia;font-size: 14px;margin-left: 4px;'>End Page</label>  					<div id='printEndPageChooser' class='printEndPageChooser' style='height:25px;width:120px;margin-left: 125px;margin-top: -18px;' >  						<input type='text' id='printEndPage' style='margin-top: 2px;width: 100%;' onKeyPress='return checkForValidKey(event);' />  					</div>  				</div>  				  				<div id='printingOptions' class='printingOptions' style='height:25px'>  				  					<div id='chooseForAnnStrikeHigh' class='printingOptionForAnnStrHigh'>  						<input id='isAnnToPrint' type='checkbox' style='float: left;margin: 4px;' checked='checked'>  						<label style='font-family: Constantia;font-size: 14px;margin-left: 80px;'>Annotation Printing</label>  					</div>  					<div id='chooseForStrike' class='printingOptionForAnnStrHigh' style='display:none;visibility:hidden;'>  						<input id='isStrikeToPrint' type='checkbox' style='float: left;margin: 4px;' checked='checked'>  						<label style='font-family: Constantia;font-size: 14px;margin-left: 91px;'>StrikeOut Printing</label>					  					</div>  					<div id='chooseForStrike' class='printingOptionForAnnStrHigh' style='display:none;visibility:hidden;'>  						<input id='isHighToPrint' type='checkbox' style='float: left;margin: 4px;' checked='checked'>  						<label style='font-family: Constantia;font-size: 14px;margin-left: 90px;'>Highlight Printing</label>					  					</div>  				</div>  			  				  				<div id='applyOkOrCancle' style='height:25px;'>  					<input style='margin-top:6px; margin-left:65px;width: 60px;' type='button' value='Ok' onClick='doPrint();'>  					 <input style='width: 60px;' type='button' value='Cancel' onClick='cancelPrint();'>  				</div>  			</div>  		</div>";
        strike = document.createElement("div");
        strike.innerHTML += content;
        document.body.appendChild(strike);
        
        var newUrl = url.split('(').join('%28');
        newUrl = newUrl.split(')').join('%29');
        content="<div id='editingToolbar' style=' display:none;' class='editingToolbar'>  			<div id='lapCaption' class='lapCaption' onMouseDown=\"dragStart(event,'editingToolbar')\">  				<div id='editIcon' style='border-right:1px solid #6D6A68; margin-left:2px;width:25px;height:20px;float:left;'>  					<img style='height: 18px;width: 16px;' src="+url+"/web/the_customize_toolbar_icon.png>  				</div>  				<div id='captionText'  align='center'   					style='border-right:1px solid #6D6A68;height: 20px;width: 195px;font-weight: bolder;font-family: Book Antiqua; font-size:16px;color: white;float:left;'>  					Properties : Annotation  				</div>  				<div id='deleteIcon' style='margin-left:228px;width:15px;height:20px;'  onclick='closePropToolBar();'>  					<img style='height: 20px;width: 18px;' src="+url+"/web/close.gif>  				</div>  			</div>  			<div id='changeAttribute' style='height:213px;background-color: #F2F1F0;'>  				<div id='widthAttribute' style='height:25px;width100%;'>  					<label style='font-family: Book Antiqua;font-size: 14px;margin-left: 4px;' onMouseDown='checkForBorderChooser();'>Border Width</label>  					<div id='widthChooser' style='height:16px;width:120px;margin-left: 125px;margin-top: -16px;border: 1px solid #6D6A68;border-radius: 5px 5px 5px 5px;'>  						<div id='widthChooserFinal' style='width:80%;height: 75%;float:left; margin: 1px;background:url("+newUrl+"/web/borderWidth1.png) no-repeat center center' onClick='activeBorderWidthChooser();'>  						</div>  						<div align='right' style='width:15%; float: right;height: 75%;margin-top: 2px;background:url("+newUrl+"/web/annBorDD12.png) no-repeat center center' onClick='activeBorderWidthChooser();'>  						</div>  						<div id='borderWidthSelectionList' class='borderWidthSelectionList' >  							<div id='borderWidthSelectionContainer1' class='borderWidthSelectionContainer' onClick='updateAnnBorderWidth(1)'>  								<div class='borderWidthSelectionPX'  align='center'>1px</div>  								<div  class='borderWidthSelectionPXImg' style='background:url("+newUrl+"/web/borderWidth1.png) no-repeat center center '>  								</div>  							</div>  							<div id='borderWidthSelectionContainer2' class='borderWidthSelectionContainer' onClick='updateAnnBorderWidth(2)'>  								<div class='borderWidthSelectionPX'  align='center'>2px</div>  								<div  class='borderWidthSelectionPXImg' style='background:url("+newUrl+"/web/borderWidth2.png) no-repeat center center '>  								</div>  							</div>  							<div id='borderWidthSelectionContainer3' class='borderWidthSelectionContainer' onClick='updateAnnBorderWidth(3)'>  								<div class='borderWidthSelectionPX'  align='center'>3px</div>  								<div  class='borderWidthSelectionPXImg' style='background:url("+newUrl+"/web/borderWidth3.png) no-repeat center center '>  								</div>  							</div>  							<div id='borderWidthSelectionContainer4' class='borderWidthSelectionContainer' onClick='updateAnnBorderWidth(4)'>  								<div class='borderWidthSelectionPX'  align='center'>4px</div>  								<div  class='borderWidthSelectionPXImg' style='background:url("+newUrl+"/web/borderWidth4.png) no-repeat center center '>  								</div>  							</div>  							<div id='borderWidthSelectionContainer5' class='borderWidthSelectionContainer' onClick='updateAnnBorderWidth(5)'>  								<div class='borderWidthSelectionPX'  align='center'>5px</div>  								<div  class='borderWidthSelectionPXImg' style='background:url("+newUrl+"/web/borderWidth5.png) no-repeat center center '>  								</div>  							</div>  							<div id='borderWidthSelectionContainer6' class='borderWidthSelectionContainer' onClick='updateAnnBorderWidth(6)'>  								<div class='borderWidthSelectionPX'  align='center'>6px</div>  								<div  class='borderWidthSelectionPXImg' style='background:url("+newUrl+"/web/borderWidth6.png) no-repeat center center '>  								</div>  							</div>     							<div id='borderWidthSelectionContainer7' class='borderWidthSelectionContainer' onClick='updateAnnBorderWidth(7)'>  								<div class='borderWidthSelectionPX'  align='center'>7px</div>  								<div  class='borderWidthSelectionPXImg' style='background:url("+newUrl+"/web/borderWidth7.png) no-repeat center center '>  								</div>  							</div>  							<div id='borderWidthSelectionContainer8' class='borderWidthSelectionContainer' onClick='updateAnnBorderWidth(8)'>  								<div class='borderWidthSelectionPX'  align='center'>8px</div>  								<div  class='borderWidthSelectionPXImg' style='background:url("+newUrl+"/web/borderWidth8.png) no-repeat center center '>  								</div>  							</div>  							<div id='borderWidthSelectionContainer9' class='borderWidthSelectionContainer' onClick='updateAnnBorderWidth(9)'>  								<div class='borderWidthSelectionPX'  align='center'>9px</div>  								<div  class='borderWidthSelectionPXImg' style='background:url("+newUrl+"/web/borderWidth9.png) no-repeat center center '>  								</div>  							</div>  							<div id='borderWidthSelectionContainer10' class='borderWidthSelectionContainer' onClick='updateAnnBorderWidth(10)'>  								<div class='borderWidthSelectionPX'  align='center'>10px</div>  								<div  class='borderWidthSelectionPXImg' style='background:url("+newUrl+"/web/borderWidth10.png) no-repeat center center '>  								</div>  							</div>  							<div id='borderWidthSelectionContainer11' class='borderWidthSelectionContainer' onClick='updateAnnBorderWidth(11)'>  								<div class='borderWidthSelectionPX'  align='center'>11px</div>  								<div  class='borderWidthSelectionPXImg' style='background:url("+newUrl+"/web/borderWidth11.png) no-repeat center center '>  								</div>  							</div>  							<div id='borderWidthSelectionContainer12' class='borderWidthSelectionContainer' onClick='updateAnnBorderWidth(12)'>  								<div class='borderWidthSelectionPX'  align='center'>12px</div>  								<div  class='borderWidthSelectionPXImg' style='background:url("+newUrl+"/web/borderWidth12.png) no-repeat center center '>  								</div>  							</div>    							<div id='borderWidthSelectionContainer13' class='borderWidthSelectionContainer' onClick='updateAnnBorderWidth(13)'>  								<div class='borderWidthSelectionPX'  align='center'>13px</div>  								<div  class='borderWidthSelectionPXImg' style='background:url("+newUrl+"/web/borderWidth13.png) no-repeat center center '>  								</div>  							</div>  							<div id='borderWidthSelectionContainer14' class='borderWidthSelectionContainer' onClick='updateAnnBorderWidth(14)'>  								<div class='borderWidthSelectionPX'  align='center'>14px</div>  								<div  class='borderWidthSelectionPXImg' style='background:url("+newUrl+"/web/borderWidth14.png) no-repeat center center '>  								</div>  							</div>  							<div id='borderWidthSelectionContainer15' class='borderWidthSelectionContainer' onClick='updateAnnBorderWidth(15)'>  								<div class='borderWidthSelectionPX'  align='center'>15px</div>  								<div  class='borderWidthSelectionPXImg' style='background:url("+newUrl+"/web/borderWidth15.png) no-repeat center center '>  								</div>  							</div>  							<div id='borderWidthSelectionContainer16' class='borderWidthSelectionContainer' onClick='updateAnnBorderWidth(16)'>  								<div class='borderWidthSelectionPX'  align='center'>16px</div>  								<div  class='borderWidthSelectionPXImg' style='background:url("+newUrl+"/web/borderWidth16.png) no-repeat center center '>  								</div>  							</div>  							<div id='borderWidthSelectionContainer17' class='borderWidthSelectionContainer' onClick='updateAnnBorderWidth(17)'>  								<div class='borderWidthSelectionPX'  align='center'>17px</div>  								<div  class='borderWidthSelectionPXImg' style='background:url("+newUrl+"/web/borderWidth17.png) no-repeat center center '>  								</div>  							</div>  							<div id='borderWidthSelectionContainer18' class='borderWidthSelectionContainer' onClick='updateAnnBorderWidth(18)'>  								<div class='borderWidthSelectionPX'  align='center'>18px</div>  								<div  class='borderWidthSelectionPXImg' style='background:url("+newUrl+"/web/borderWidth18.png) no-repeat center center '>  								</div>  							</div>    							<div id='borderWidthSelectionContainer19' class='borderWidthSelectionContainer' onClick='updateAnnBorderWidth(19)'>  								<div class='borderWidthSelectionPX'  align='center'>19px</div>  								<div  class='borderWidthSelectionPXImg' style='background:url("+newUrl+"/web/borderWidth19.png) no-repeat center center '>  								</div>  							</div>    							<div id='borderWidthSelectionContainer20' class='borderWidthSelectionContainer' onClick='updateAnnBorderWidth(20)'>  								<div class='borderWidthSelectionPX'  align='center'>20px</div>  								<div  class='borderWidthSelectionPXImg' style='background:url("+newUrl+"/web/borderWidth20.png) no-repeat center center '>  								</div>  							</div>    						</div>  					</div>  				</div>  				<div id='backgroundAttribute' style='height:25px;width100%;'  onmousedown='checkForBorderChooser();'>  					<label style='font-family: Book Antiqua;font-size: 14px;margin-left: 4px;'>Background Color</label>  					<div id='bgColorChooser' style='height:25px;width:120px;margin-left: 125px;margin-top: -18px;'>  						<div class='bgColorChooser' style='background-color: red;' onClick=\"updateAnnBgColor('red');\"></div>  						<div class='bgColorChooser' style='background-color: yellow;' onClick=\"updateAnnBgColor('yellow');\"></div>  						<div class='bgColorChooser' style='background-color: green;' onClick=\"updateAnnBgColor('green');\"></div>  						<div class='bgColorChooser' style='background-color: blue;' onClick=\"updateAnnBgColor('blue');\"></div>  						<div class='bgColorChooser' style='background-color:;' onClick=\"updateAnnBgColor('noFill');\"></div>  					</div>  				</div>  				<div id='transparency' style='height:25px; '  onmousedown='checkForBorderChooser();'>  					<label style='font-family: Book Antiqua;font-size: 14px;margin-left: 4px;'>Transparency</label>  					<div id='opacitySlider' style='width:112px;height:15px; margin-left: 125px;margin-top: -12px;'>  					</div>  				</div>  				<div id='textColor' style='height:25px;width100%; margin-top: 5px;'  onmousedown='checkForBorderChooser();'>  					<label style='font-family: Book Antiqua;font-size: 14px;margin-left: 4px;'>Text Color</label>  					<div id='textColorChooser' style='height:25px;width:120px;margin-left: 125px;margin-top: -16px;'>  						<div class='bgColorChooser' style='background-color: red;' onClick=\"updateAnnTextColor('red');\"></div>  						<div class='bgColorChooser' style='background-color: yellow;' onClick=\"updateAnnTextColor('yellow');\"></div>  						<div class='bgColorChooser' style='background-color: green;' onClick=\"updateAnnTextColor('green');\"></div>  						<div class='bgColorChooser' style='background-color: blue;' onClick=\"updateAnnTextColor('blue');\"></div>  						<div class='bgColorChooser' style='background-color:black;' onClick=\"updateAnnTextColor('black');\"></div>					  					</div>  				</div>  				<div id='textFontSize' style='height:25px;width100%; margin-top: 5px;'  onmousedown='checkForBorderChooser();'>  					<label style='font-family: Book Antiqua;font-size: 14px;margin-left: 4px;'>Text Font-Size</label>  					<div id='textFontChooser' style='height:25px;width:120px;margin-left: 125px;margin-top: -16px;'>  						<select id='fontSizeList' style='width:100%' id='fontSizeList' onchange='changeTextSizeOfAnn(this.value);' name='fontSizeList' title='Font Size'>  							<option onMouseDown='updateTextFontSize(8)' value='0'>8pt</option>  							<option onMouseDown='updateTextFontSize(10);' value='1'>10pt</option>  							<option onMouseDown='updateTextFontSize(12);' value='2'>12pt</option>  							<option onMouseDown='updateTextFontSize(14);' value='3'>14pt</option>  							<option onMouseDown='updateTextFontSize(18);' value='4'>18pt</option>  							<option onMouseDown='updateTextFontSize(24);' value='5'>24pt</option>  							<option onMouseDown='updateTextFontSize(36);' value='6'>36pt</option>  							<option onMouseDown='updateTextFontSize(46);' value='7'>46pt</option>  							<option onMouseDown='updateTextFontSize(50);' value='8'>50pt</option>  						</select>  					</div>  				</div>  				<div id='textFontFace' style='height:25px;width100%; margin-top: 5px;'  onmousedown='checkForBorderChooser();'>  					<label style='font-family: Book Antiqua;font-size: 14px;margin-left: 4px;'>Text FontFace</label>  					<div id='textFontChooser' style='height:25px;width:120px;margin-left: 125px;margin-top: -16px;'>  						<select id='fontFaceList' style='width: 100%;margin-top: -2px;' onchange='changeTextFontOfAnn(this.value);'>  							<option onMouseDown='updateTextFontFace('Aharoni');' value='0'>Aharoni</option>  							<option onMouseDown='updateTextFontFace('Angsana New');' value='1'>Angsana New</option>  							<option onMouseDown='updateTextFontFace('Arabic Transparent');' value='2'>Arabic Transparent</option>  							<option onMouseDown='updateTextFontFace('Arial');' value='3'>Arial</option>  							<option onMouseDown='updateTextFontFace('Arial Black');' value='4'>Arial Black</option>  							<option onMouseDown='updateTextFontFace('Arail Narrow');' value='5'>Arail Narrow</option>  							<option onMouseDown='updateTextFontFace('Batang');' value='6'>Batang</option>  							<option onMouseDown='updateTextFontFace('BatangChe');' value='7'>BatangChe</option>  							<option onMouseDown='updateTextFontFace('Book Antigua');' value='8'>Book Antigua</option>  							<option onMouseDown='updateTextFontFace('Bookman Old Style');' value='9'>Bookman Old Style</option>  							<option onMouseDown='updateTextFontFace('Browallia New');' value='10'>Browallia New</option>  							<option onMouseDown='updateTextFontFace('Comic Sans MS');' value='11'>Comic Sans MS</option>  							<option onMouseDown='updateTextFontFace('Courier New');' value='12'>Courier New</option>  							<option onMouseDown='updateTextFontFace('Dialog');' value='13'>Dialog</option>  							<option onMouseDown='updateTextFontFace('Gautami');' value='14'>Gautami</option>  							<option onMouseDown='updateTextFontFace('Gulim');' value='15'>Gulim</option>  							<option onMouseDown='updateTextFontFace('Impact');' value='16'>Impact</option>  							<option onMouseDown='updateTextFontFace('MS Outlook');' value='17'>MS Outlook</option>  							<option onMouseDown='updateTextFontFace('Raavi');' value='18'>Raavi</option>  							<option onMouseDown='updateTextFontFace('Symbol');' value='19'>Symbol</option>  							<option onMouseDown='updateTextFontFace('Times New Roman');' value='00'>Times New Roman</option>  							<option onMouseDown='updateTextFontFace('Traditional Arabic');' value='21'>Traditional Arabic</option>  							<option onMouseDown='updateTextFontFace('Webdings');' value='22'>Webdings</option>  							<option onMouseDown='updateTextFontFace('Wingdings');' value='23'>Wingdings</option>  						</select>					  					</div>  				</div>  				<div id='applyOkOrCancle' style='height:35px;margin-top: 5px;'  onmousedown='checkForBorderChooser();'>  						<input style='margin-top:6px; margin-left:8px;width: 60px;' type='button' value='Ok' onClick='okPropToolBar();'>  						<input style='width: 60px;' type='button' value='Cancel' onClick='closePropToolBar();'>  						<input id='setAnnDefault' style='width: 110px;' type='button' value='Set as Default' onClick='setAnnotationAsDafaultFromDialog(event);'>  						  			</div>  		</div>  	  	<!-- End Porperty toolbar -->          </div>";
        strike = document.createElement("div");
        strike.innerHTML += content;
        document.body.appendChild(strike);
        
        content="<div id='borderColorList' class='borderColorList'>  			<div class='borderColors' style='float:left;background:red;' onClick=\"updateAnnBorderColor('red');\"></div>  			<div class='borderColors' style='float:left;background:yellow' onClick=\"updateAnnBorderColor('yellow');\"></div>  			<div class='borderColors' style='float:left;background:green;' onClick=\"updateAnnBorderColor('green');\"></div>  			<div class='borderColors' style='float:left; background:blue;' onClick=\"updateAnnBorderColor('blue');\"></div>  			<div class='borderColors' style='float:left;background:black;' onClick=\"updateAnnBorderColor('black');\"></div>  		</div>";
        strike = document.createElement("div");
        strike.innerHTML += content;
        document.body.appendChild(strike);
        
        content = "<div id='printBackSideDiv' style='position:absolute; display: none;z-index: 12000;background-color: #182738;height: 100%;opacity:0.6;left:0px;top: 0px;text-align: center;-webkit-touch-callout: none;-webkit-user-select: none;-khtml-user-select: none;-moz-user-select: none;-ms-user-select: none;user-select: none;' ><font id='printingText' style='position:absolute; font-size: 70px;font-variant: small-caps; opacity:1;color: rgb(251, 252, 250);font-weight: bolder;'>Printing....</font></div>";
		strike = document.createElement("div");
		strike.innerHTML += content;
		document.body.appendChild(strike);
        
    }

    this.searchText = function () {
        openSearchWindow();
    }
    this.printDoc = function () {
        printDialogDisplay();
    }
    this.save = function () {
        changeColorOnClick();
    }
    this.SetAnnotation = function (type) {
        if (type == 1)
        	addAnnotationModule('line');
//            addAnnotation('line');
        else if (type == 2)
        	addAnnotationModule('rectangle');
//            addAnnotation('rectangle');
        else if (type == 3)
        	addAnnotationModule('circle');
//            addAnnotation('circle');
        else if (type == 4)
        	addAnnotationModule('arrow');
//            addAnnotation('arrow');
        else if (type == 5)
        	addAnnotationModule('highlighter');
//            addAnnotation('highlighter');
        else if (type == 6)
        	addAnnotationModule('pen');
//            addAnnotation('pen');
        else if (type == 7)
        	addAnnotationModule('text');
//            addAnnotation('text');
        else if (type == 8)
        	addAnnotationModule('stamp');
//            addAnnotation('stamp');
        else if (type == 18)
        	addAnnotationModule('polyLine');
//            addAnnotation('polyLine');
        else if (type == 19)
        	addAnnotationModule('polygon');
//            addAnnotation('polygon');
        else if (type == 20)
        	addAnnotationModule('scratchOut');
//            addAnnotation('scratchOut');
        else if (type == 21)
        	addAnnotationModule('crossProduct');
//            addAnnotation('crossProduct');
        else if (type == 22)
        	addAnnotationModule('cloud');
//            addAnnotation('cloud');
        else if (type == 23)
        	addAnnotationModule('arc');
//            addAnnotation('arc');
        else if (type == 24)
        	addAnnotationModule('angle');
//            addAnnotation('angle');
        else if (type == 25)
        	addAnnotationModule('ruler');
//            addAnnotation('ruler');
        else if (type == 9)
            redactionSelect('15');
        
        anntBar.style.visibility='hidden'
    }
    this.splitDocument = function(){
    	SplitDocument();
    }
    this.swapPages = function(){
//    	var eViewerHTML = new eViewerHTML5();
    	var eViewerHTML = new eViewer();
        eViewerHTML.swapPages(this.viewerUrl,sessionId.value,swapOrder.value);
        swapAnnotation(swapOrder.value);
        
        var worker = new Worker('downloader.js?'+Math.random());

		worker.onmessage = function(e) {
			lastAccesedPage="page="+currentPageNumber;
			PDFView.treeInitialize = false;
			isCallForComplete=true;
			var uint8Array = new Uint8Array(e.data);
			
			
			PDFView.isRotated = true;
			PDFView.isPageRefresh = true;
			
			PDFView.open(uint8Array, 0);
			removeBlockedDiv();
			viewer.scrollTop=0;
			isCompleleDocumentCall=true;
			var imgProgress = document.getElementById("imgProgress");
			if (imgProgress) imgProgress.style.display = "none";

			var numPages = document.getElementById("numPages");
			if (numPages) numPages.style.display = "";
		}
		
		if(sessionID.indexOf(',')!=-1)
			worker.postMessage({fileName: 'abc',url:TrimString("../temp/../temp/"+fileID+".pdf"), type: 'image/pdf'});				
		else
			worker.postMessage({fileName: 'abc',url:TrimString("../temp/../temp/"+sessionID+""+".pdf"), type: 'image/pdf'});
    }
}
function mouse_Dragged(e) 
{
	
} 