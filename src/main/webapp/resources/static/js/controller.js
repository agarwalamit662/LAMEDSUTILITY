myApp.controller("courseController", function($scope, $routeParams,$location) {
    	
    	$scope.courseId = $routeParams.param;
    	$scope.coursesClass = courses;
    	
    	$scope.courseName = courses[$scope.courseId-1].Course.name;
    	
    	
    	$scope.lessonClass = courses[$scope.courseId-1].Course.lessons;
    	
    	$scope.getIframeSrc = function(src) {
    	      return src;
    	 };
    	
    	
    	$scope.lessonId = courses[$scope.courseId-1].Course.lessons.length+1;
    	
    	
		var myEl = angular.element( document.querySelector( '#headid' ) );
		myEl.attr('style',"background:rgb(242,242,246);");
		myEl = angular.element( document.querySelector( '#ngviewid' ) );
		myEl.attr('style',"background:rgb(242,242,246); width:100%; height:100%;");
		
		
		$scope.add = function(cid,lid){
			$scope.coursesClass = courses;
			var path = "/lesson/"+cid+"/"+lid;
	    	$location.path(path);
	    	
	  	};
	  	
	  	$scope.back = function(){
			$scope.coursesClass = courses;
			var path = "/";
	    	$location.path(path);
	    	
	  	};
		
	})
	
	myApp.controller('lessonAddController', function($scope, $routeParams,$location, $http,downloadService) {
		$scope.headingTitle = "Lesson Add Info";
		
		
		var myEl = angular.element( document.querySelector( '#headid' ) );
		myEl.attr('style',"background:rgb(242,242,246);");
		myEl = angular.element( document.querySelector( '#ngviewid' ) );
		myEl.attr('style',"background:rgb(242,242,246); width:100%; height:100%;");
		
		$scope.showForSSP = true;
		$scope.showForWKP = false;
		$scope.showForSSPCCD = false;
		
		$scope.showSSP = function(){
			$scope.showForSSP = true;
			$scope.showForSSPCCD = false;
			$scope.showForWKP = false;
			$scope.showDownload = false;
  			$scope.showException = false;
		}
		$scope.showWKP = function(){
			$scope.showForSSP = false;
			$scope.showForSSPCCD = false;
			$scope.showForWKP = true;
			$scope.showDownload = false;
  			$scope.showException = false;
		}
		$scope.showSSPCCD = function(){
			$scope.showForSSP = false;
			$scope.showForSSPCCD = true;
			$scope.showForWKP = false;
			$scope.showDownload = false;
  			$scope.showException = false;
		}
		


        $scope.setTitle = function(fileInput) {

        var file=fileInput.value;
        var filename = file.replace(/^.*[\\\/]/, '');
        
        /*var path = (window.URL || window.webkitURL).createObjectURL(fileInput);
        console.log('path', path);
        alert(path);*/
        
        var title = filename.substr(0, filename.lastIndexOf('.'));
        var myEl = angular.element( document.querySelector( '#title' ) );
        myEl.val(title);
        myEl.focus();
        
    };

    
        $scope.uploadFile=function(){
        	
        	$scope.showDownload = false;
	    		$scope.showException = false;
        	
             var formData=new FormData();
             formData.append("file",file.files[0]);
             $http.post('fileUploadCheck', formData, {
                 transformRequest: function(data, headersGetterFunction) {
                     return data;
                 },
                 transformResponse: angular.identity,
                 headers: { 'Content-Type': undefined }
                 }).success(function(response) {                       
                     //alert("Success ... " + response);
                     
                     $scope.output = JSON.parse(response);
       		    	
       		    	console.log($scope.output.filePath);
       		    	console.log($scope.output.utilSuccessfull);
       		    	console.log($scope.output.exceptions);
       		    	
       		    	
       		    	if($scope.output.utilSuccessfull == 'Y'){
       		    		alert("Success");
       		    		$scope.showDownload = true;
       		    		$scope.showException = false;
       		  		
       		    		var filePath = $scope.output.filePath;
       		    		var myEl = angular.element( document.querySelector( '#successfull' ) );
       		    		myEl.val('<a href = "" ng-click="download('+filePath+')" >Utility Successful. Click this link to download</a>');
       		    	}
       		    	else{
       		    		
       		    		alert("Exceptions");
       		    		$scope.showDownload = false;
       		    		$scope.showException = true;
       		    		
       		    		var exc = angular.element( document.querySelector( '#exceptions' ) );
       		    		exc.val($scope.output.exceptions);
       		    		
       		    	}
                     
                     
                 }).error(function(response) {
                	 
                     alert("Error ... " + response);
                     $scope.showDownload = false;
    		    		$scope.showException = true;
                 });
		
        }
        
		$scope.showDownload = false;
		$scope.showException = false;
		
    	$scope.sspUtility = {pageIds : '',
    					errorMessages : '',
    					displayTexts : '',
    					refTables : ''};
    	
    	$scope.sspccdUtility = {tableName : ''};
  		
    	$scope.responseOutput = {filePath : '',
    			utilSuccessfull : '',
    			exceptions : '',
				};
    	
  
  		$scope.save = function(){
  			
  			$scope.showDownload = false;
  			$scope.showException = false;
  			console.log($scope.sspUtility.pageIds);
  			console.log($scope.sspUtility.errorMessages);
  			console.log($scope.sspUtility.displayTexts);
  			console.log($scope.sspUtility.refTables);
  			
  			var parameter = JSON.stringify($scope.sspUtility);
  			console.log(parameter);
  			$http({
  		        'url' : 'ssputility',
  		        'method' : 'POST',
  		        'headers': {'Content-Type' : 'application/json'},
  		        'data' : parameter,
  		        'transformResponse': angular.identity
  		    }).success(function(response){
  		    	var ab = {"abc":"def",
  		    				"edf":"ged"};
  		    	
  		    	
  		    	$scope.output = JSON.parse(response);
  		    	
  		    	console.log($scope.output.filePath);
  		    	console.log($scope.output.utilSuccessfull);
  		    	console.log($scope.output.exceptions);
  		    	
  		    	
  		    	if($scope.output.utilSuccessfull == 'Y'){
  		    		alert("Success");
  		    		$scope.showDownload = true;
  		    		$scope.showException = false;
  		  		
  		    		var filePath = $scope.output.filePath;
  		    		var myEl = angular.element( document.querySelector( '#successfull' ) );
  		    		myEl.val('<a href = "" ng-click="download('+filePath+')" >Utility Successful. Click this link to download</a>');
  		    	}
  		    	else{
  		    		
  		    		alert("Exceptions");
  		    		$scope.showDownload = false;
  		    		$scope.showException = true;
  		    		
  		    		var exc = angular.element( document.querySelector( '#exceptions' ) );
  		    		exc.val($scope.output.exceptions);
  		    		
  		    	}
  		    	/*$scope.responseOutput.filePath = response.filePath;
  		    	$scope.responseOutput.utilSuccessfull = response.utilSuccessfull;
  		    	$scope.responseOutput.exceptions = response.exceptions;*/
  		    	
  		        //$scope.marketForm.texts.push({'text' : data.text});
  		    })
  		    /*$http.post('ssputility', parameter).
  		    success(function(data, status, headers, config) {
  		        // this callback will be called asynchronously
  		        // when the response is available
  		        console.log(data);
  		      }).
  		      error(function(data, status, headers, config) {
  		        // called asynchronously if an error occurs
  		        // or server returns response with an error status.
  		      });*/
  			
  			/*var formData=new FormData();
            formData.append("utility",file.files[0]);
            $http.post('fileUploadCheck', formData, {
                transformRequest: function(data, headersGetterFunction) {
                    return data;
                },
                transformResponse: angular.identity,
                headers: { 'Content-Type': undefined }
                }).success(function(response) {                       
                    alert("Success ... " + response);
                    alert("Success ... " + response.data);
                }).error(function(response) {
               	 
                    alert("Error ... " + response);
                });*/
  			
    	
  		};
  		
  		$scope.saveCCD = function(){
  			
  			$scope.showDownload = false;
  			$scope.showException = false;
  			console.log($scope.sspccdUtility.tableName);
  			
  			var parameter = JSON.stringify($scope.sspccdUtility);
  			console.log(parameter);
  			$http({
  		        'url' : 'ssputilityccd',
  		        'method' : 'POST',
  		        'headers': {'Content-Type' : 'application/json'},
  		        'data' : parameter,
  		        'transformResponse': angular.identity
  		    }).success(function(response){
  		    	var ab = {"abc":"def",
  		    				"edf":"ged"};
  		    	
  		    	
  		    	$scope.output = JSON.parse(response);
  		    	
  		    	console.log($scope.output.filePath);
  		    	console.log($scope.output.utilSuccessfull);
  		    	console.log($scope.output.exceptions);
  		    	
  		    	
  		    	if($scope.output.utilSuccessfull == 'Y'){
  		    		alert("Success");
  		    		$scope.showDownload = true;
  		    		$scope.showException = false;
  		  		
  		    		var filePath = $scope.output.filePath;
  		    		var myEl = angular.element( document.querySelector( '#successfull' ) );
  		    		myEl.val('<a href = "" ng-click="download('+filePath+')" >Utility Successful. Click this link to download</a>');
  		    	}
  		    	else{
  		    		
  		    		alert("Exceptions");
  		    		$scope.showDownload = false;
  		    		$scope.showException = true;
  		    		
  		    		var exc = angular.element( document.querySelector( '#exceptions' ) );
  		    		exc.val($scope.output.exceptions);
  		    		
  		    	}
  		    	/*$scope.responseOutput.filePath = response.filePath;
  		    	$scope.responseOutput.utilSuccessfull = response.utilSuccessfull;
  		    	$scope.responseOutput.exceptions = response.exceptions;*/
  		    	
  		        //$scope.marketForm.texts.push({'text' : data.text});
  		    })
  		    /*$http.post('ssputility', parameter).
  		    success(function(data, status, headers, config) {
  		        // this callback will be called asynchronously
  		        // when the response is available
  		        console.log(data);
  		      }).
  		      error(function(data, status, headers, config) {
  		        // called asynchronously if an error occurs
  		        // or server returns response with an error status.
  		      });*/
  			
  			/*var formData=new FormData();
            formData.append("utility",file.files[0]);
            $http.post('fileUploadCheck', formData, {
                transformRequest: function(data, headersGetterFunction) {
                    return data;
                },
                transformResponse: angular.identity,
                headers: { 'Content-Type': undefined }
                }).success(function(response) {                       
                    alert("Success ... " + response);
                    alert("Success ... " + response.data);
                }).error(function(response) {
               	 
                    alert("Error ... " + response);
                });*/
  			
    	
  		};
  		
  		
  		$scope.download = function(fileName) {
            downloadService.download($scope.output.filePath)
                .then(function(success) {
                    console.log('success : ' + success);
                }, function(error) {
                    console.log('error : ' + error);
                });
        };
    	
	});
	
	myApp.controller('homeController', function($scope,$rootScope,$timeout,$dialogs) {
		
		var myEl = angular.element( document.querySelector( '#headid' ) );
		
		myEl.removeAttr('style');
		
		myEl = angular.element( document.querySelector( '#ngviewid' ) );
		myEl.attr('style',"width:100%; height:100%;");
		
		$scope.headingTitle = "Head";
		$scope.coursesClass = courses;
		
		$scope.launch = function(which){
    var dlg = null;
    switch(which){
        
      // Error Dialog
      // Create Your Own Dialog
      case 'create':
        dlg = $dialogs.create('/dialogs/addCourse.html','addCourseController',{},{key: false,back: 'static'});
        dlg.result.then(function(classroom){
          $scope.title = classroom.title;
          $scope.description = classroom.description;
          
          
          
          var temp = {"Course":
   			{  
   			"id":courses.length+1,
      		"name":$scope.title,
      		"addedby":$scope.description,
      		"lessons":[]
   			}
   			};
   			
   			courses.push(temp);
   			$scope.coursesClass = courses;     
          
        },function(){
          $scope.title = 'You decided not to enter in your name, that makes me sad.';
          $scope.description = 'No Description';
        });
        
        break;
    }; // end switch
  }; // end launch
		
	}); 
	
	myApp.controller('addCourseController',function($scope,$modalInstance,data){
  	$scope.classroom = {title : '',
  						description : ''};
	$scope.titlePlaceholder = 'Please Select Your Desired Title';
	$scope.descPlaceholder = 'Enter what\'\ the purpose of this class if anyone has doubts';
	$scope.cancel = function(){
    $modalInstance.dismiss('canceled');  
  }; // end cancel
  
  $scope.save = function(){
    $modalInstance.close($scope.classroom);
  }; // end save
  
  $scope.hitEnter = function(evt){
  	
    if(angular.equals(evt.keyCode,13) && !(angular.equals($scope.classroom.title,null) || angular.equals($scope.classroom.title,''))
    && !(angular.equals($scope.classroom.description,null) || angular.equals($scope.classroom.description,'')))
				$scope.save();
  }; // end hitEnter
}) // end addCourseController
.run(['$templateCache',function($templateCache){
  $templateCache.put('/dialogs/addCourse.html','<div class="modal"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><h4 class="modal-title" style="float: left;"">Add Class</h4><button type="button" class="btn btn-default" ng-click="cancel()" style="float: right; border: medium none; background: transparent none repeat scroll 0% 0%;">X</button></div><div class="modal-body"><ng-form name="createclasssDialog" novalidate role="form"><div class="form-group input-group-lg" ng-class="{true: \'has-error\'}[createclasssDialog.titleclass.$dirty && createclasssDialog.titleclass.$invalid && createclasssDialog.desclass.$dirty && createclasssDialog.desclass.$invalid]"><label class="control-label" for="titleclass">Title</label><input type="text" class="form-control" name="titleclass" id="titleclass" placeholder = {{titlePlaceholder}} ng-model="classroom.title" ng-keyup="hitEnter($event)" required><label class="control-label" for="desclass">Description</label><input type="textarea" class="form-control" style="height:66px;" name="desclass" id="desclass" placeholder = "{{descPlaceholder}}" ng-model="classroom.description" ng-keyup="hitEnter($event)" required></div></ng-form></div><div class="modal-footer"><button type="button" class="btn btn-default" style="border-radius:14px;" ng-click="cancel()">Cancel</button><button type="button" class="btn btn-primary" style="border-radius:14px; background-color:rgb(121, 134, 203);" ng-click="save()" ng-disabled="(createclasssDialog.$dirty && createclasssDialog.$invalid) || createclasssDialog.$pristine">Create</button></div></div></div></div>');

	
}]); // end run / module
	
	myApp.controller('fileUploadController', function($scope, $http) {

	    $scope.document = {};

	        $scope.setTitle = function(fileInput) {

	        var file=fileInput.value;
	        var filename = file.replace(/^.*[\\\/]/, '');
	        var title = filename.substr(0, filename.lastIndexOf('.'));
	        var myEl = angular.element( document.querySelector( '#title' ) );
	        myEl.val(title);
	        myEl.focus();
	        /*$("#title").val(title);
	        $("#title").focus();*/
	        $scope.document.title=title;
	    };

	    $scope.downloadFile = function () {                   
	        $http({method: 'GET', url: 'download'}).
	         success(function(result) {
	                        console.log("ok");
	                        alert("OK");
	          }).
	     error(function(data, status, headers, config) {
	            console.log("oops");
	            alert("NOT OK");
	         });
	 };
	    
	        $scope.uploadFile=function(){
	             var formData=new FormData();
	             formData.append("file",file.files[0]);
	             $http.post('fileUploadCheck', formData, {
	                 transformRequest: function(data, headersGetterFunction) {
	                     return data;
	                 },
	                 transformResponse: angular.identity,
	                 headers: { 'Content-Type': undefined }
	                 }).success(function(response) {                       
	                     alert("Success ... " + response);
	                     alert("Success ... " + response.data);
	                 }).error(function(response) {
	                	 
	                     alert("Error ... " + response);
	                 });
	             
	         
	                   /*$http({
	                  method: 'POST',
	                  url: '/serverApp/rest/newDocument',
	                  headers: { 'Content-Type': 'multipart/form-data'},
	                  data:  formData
	                })
	                .success(function(data, status) {                       
	                    alert("Success ... " + status);
	                })
	                .error(function(data, status) {
	                    alert("Error ... " + status);
	                });*/
	      };
	});	
	
	myApp.controller('downloadSampleCtrl', function($scope,downloadService) {
        $scope.download = function(fileName) {
            downloadService.download(fileName)
                .then(function(success) {
                    console.log('success : ' + success);
                }, function(error) {
                    console.log('error : ' + error);
                });
        };
    });
	
	myApp.service('downloadService', ['$q', '$timeout', '$window',
	                                           function($q, $timeout, $window) {
	                                               return {
	                                                   download: function(name) {

	                                                       var defer = $q.defer();

	                                                       $timeout(function() {
	                                                               $window.location = 'download?name=' + name;

	                                                           }, 1000)
	                                                           .then(function() {
	                                                               defer.resolve('success');
	                                                           }, function() {
	                                                               defer.reject('error');
	                                                           });
	                                                       return defer.promise;
	                                                   }
	                                               };
	                                           }
	                                       ]);
	
	
	