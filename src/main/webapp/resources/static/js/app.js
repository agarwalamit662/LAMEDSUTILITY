var myApp= angular.module('classrooomApp',['ngRoute','ngResource','ui.bootstrap','dialogs']);
	myApp.config(['$routeProvider',
	  function ($routeProvider) {
	        $routeProvider.
	        when('/course/:param',{
	        
                templateUrl: "/utility/resources/static/views/courses.html",
                controller: 'courseController'
            }
            )
            .when('/lesson/:courseid/:lessonid',{
                templateUrl: '/utility/resources/static/views/lessonadd.html',
                controller: 'lessonAddController'
            })
            .when('/fileUpload/',{
                templateUrl: '/utility/resources/static/views/fileUpload.html',
                controller: 'fileUploadController'
            })
            .when('/',{
                templateUrl: '/utility/resources/static/views/lessonadd.html',
                controller: 'lessonAddController'
            })
            .when('/download/',{
                templateUrl: '/utility/resources/static/views/download.html',
                controller: 'downloadSampleCtrl'
            })
            .when('/fileUploadCheck',{
            	redirectTo: '/'
            })
            .otherwise(
                { redirectTo: '/'}
            );
	  }]);

myApp.config(function($sceDelegateProvider) {
		  $sceDelegateProvider.resourceUrlWhitelist([
		    'self',
		    'https://www.youtube.com/**'
		  ]);
});	
	