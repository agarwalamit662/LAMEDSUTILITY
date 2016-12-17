<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en" ng-app="classrooomApp" class="no-js" id="headid">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>My Classroom</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/static/css/app.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/static/css/rzslider.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/static/css/landing-page.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/static/css/animate.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/webjars/bootstrap/bootstrap.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/webjars/bootstrap/bootstrap.min.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/webjars/bootstrap/style.css">  
	<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/static/css/angular-material.min.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/static/css/model.css">
	<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.js"></script>
	<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular-resource.js"></script>
	<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular-route.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/static/js/app.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/static/js/controller.js"></script>
 <%--    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/static/js/download.service.js"></script> --%>
    
    
    <!-- Angular material design -->
	
  	<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular-animate.min.js"></script>
  	<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular-aria.min.js"></script>
  	<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular-messages.min.js"></script>
  	
  	<!-- Angular Material Library -->
  	<script src="https://ajax.googleapis.com/ajax/libs/angular_material/1.1.0/angular-material.min.js"></script>
	<script src="http://angular-ui.github.io/bootstrap/ui-bootstrap-tpls-0.6.0.js" type="text/javascript"></script>
    <script src="http://m-e-conroy.github.io/angular-dialog-service/javascripts/dialogs.min.js" type="text/javascript"></script>
	
	<style type="text/css">
	
	.text_color{
		color: rgb(82,71,83)
	}
	.p_color{
		color: rgb(164,164,164);
		float:left;
		width: 90%
		
	}
	.hr_color{
		color: rgb(202,193,189);
		float:left;
		width: 100%;
		margin-top:5px;
		
	}
	
	</style>
	

	
	<script>
	
	
	
	var courses = [  
   	{"Course":
   		{  
   		"id":1,
      	"name":"Colour Story",
      	"addedby":"Amit Agarwal",
      	"lessons":[  
         {  
            "lid":1,
            "title":"Lesson1",
            "desc":"Course for JS",
            "url":"https://www.youtube.com/embed/BBSE4dA4aLg"
         },
         {  
            "lid":2,
            "title":"Lesson2",
            "desc":"Course for Android",
            "url":"https://www.youtube.com/embed/vUCM_0evdQY"
         }
      ]
   	}
   	},
   	{"Course":
   		{
   		"id":2,  
      	"name":"Branding Brands",
      "addedby":"Tushar Kukreja",
      "lessons":[  
         {  
         	"lid":1,
            "title":"Android Basics",
            "desc":"Course for Android Basics",
            "url":"https://www.youtube.com/embed/_ffgSc2c5F0"
         },
         {  
            "lid":2,
            "title":"Android Advanced",
            "desc":"Course for Android Advanced",
            "url":"https://www.youtube.com/embed/_ffgSc2c5F0"
         }
      ]
   	}
   	},
   	{"Course":
   		{  
      	"id":3,
      	"name":"Illustration Much",
      	"addedby":"Amit Agarwal",
      	"lessons":[  
         {  
            "lid":1,
            "title":"Lesson1",
            "desc":"Course for JS",
            "url":"https://www.youtube.com/embed/hXh35CtnSyU"
         },
         {  
            "lid":2,
            "title":"Lesson2",
            "desc":"Course for Android",
            "url":"https://www.youtube.com/embed/AOac5x4qpxQ"
         }
      ]
   	}
   	},
   	{"Course":
   		{  
      	"id":4,
      	"name":"Toy Maker",
      "addedby":"Tushar Kukreja",
      "lessons":[  
         {  
            "lid":1,
            "title":"Android Basics",
            "desc":"Course for Android Basics",
            "url":"https://www.youtube.com/embed/fsXdJQL8sPA"
         },
         {  
            "lid":2,
            "title":"Android Advanced",
            "desc":"Course for Android Advanced",
            "url":"https://www.youtube.com/embed/WoLwSoI_73M"
         }
      ]
   	}
   	},
   	{"Course":
   		{ 
   		"id":5, 
      	"name":"Watercolor Magic",
      	"addedby":"Amit Agarwal",
      	"lessons":[  
         {  
            "lid":1,
            "title":"Lesson1",
            "desc":"Course for JS",
            "url":"https://www.youtube.com/embed/5rRjmb10Lpw"
         },
         {  
            "lid":2,
            "title":"Lesson2",
            "desc":"Course for Android",
            "url":"https://www.youtube.com/embed/BzoMBGMipBA"
         }
      ]
   	}
   	},
   	{"Course":
   		{  
   		"id":6,
      	"name":"Dream Job",
      "addedby":"Tushar Kukreja",
      "lessons":[  
         {  
            "lid":1,
            "title":"Android Basics",
            "desc":"Course for Android Basics",
            "url":"https://www.youtube.com/embed/7z1rf2kzEkc"
         },
         {  
            "lid":2,
            "title":"Android Advanced",
            "desc":"Course for Android Advanced",
            "url":"https://www.youtube.com/embed/TrPvQvbp3Cg"
         }
      ]
   	}
   	}
	];
	
	</script>
	
	
</head>

<body>



<div ng-view  id="ngviewid"></div> 
</body>
</html>
