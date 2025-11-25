var mainApp = angular.module("mainApp", ['ngRoute']);

mainApp.config(function($routeProvider) {
	$routeProvider
		.when('/home', {
			templateUrl: 'home.html',
			controller: 'StudentController'
		})
		.when('/viewStudents', {
			templateUrl: 'viewStudents.html',
			controller: 'StudentController'
		})
		.when('/addStudent', {
          			templateUrl: 'addStudent.html',
          			controller: 'StudentController'
          		})
          		.when('/editStudent/:sid', {
                          			templateUrl: 'addStudent.html',
                          			controller: 'StudentController'
                          		})
		.otherwise({
			redirectTo: '/home'
		});
});



//AngularJS module
//var app = angular.module('app', []);

//AngularJS controller
mainApp.controller('StudentController', ['$scope', '$http','$location','$routeParams',StudentController]);

//AngularJS controller method
function StudentController($scope, $http,$location,$routeParams) {

    $scope.loadData = function () {
        $http({
            method: "GET",
            url: '/student',
         }).then(function (response) {
             $scope.Students  = response.data;
        }, function (error) {
            console("Failed to load data....");
        });
    }

    $scope.isUpdate = function(){
        if($routeParams.sid){
            $scope.getSingleData($routeParams.sid)
        }
    }
    $scope.openPage = function(){
        //$window.location.href = '/'+page+'.html';
        //$state.go('home');
        $location.path('/viewStudents');
    }

    $scope.Student={};
    $scope.getSingleData = function (studentID) {
    $scope.mahesh="Mahesh";
        $http({
            method: "GET",
            url: '/student'+studentID,
         }).then(function (response) {
             studentDetail = response.data;
             $scope.Student.sid = studentDetail.sid;
             $scope.Student.firstName = studentDetail.firstName;
             $scope.Student.lastName = studentDetail.lastName;
             $scope.Student.email = studentDetail.email;
             $scope.Student.mobile = studentDetail.mobile;
        }, function (error) {
            console("Failed to load data....");
        });

    }


    //Inser operation
    $scope.add = function (Student) {
        $http({
            method: "POST",
            url: '/student',
            data: Student
        }).then(function (response) {
            $scope.openPage();
        }, function (error) {
            console("Failed to insert data....");
        });
    };


    //Edit/Update operation
    $scope.save = function (Student) {
        $http({
            method: "PUT",
            url: '/student',
            data: Student
        }).then(function (response) {
             $scope.openPage();
        }, function (error) {
            console("Failed to Update data....");
        });
    };

    //Delete operation
    $scope.deletecustomer = function (Student) {
        $http({
            method:"DELETE",
            url:'/student/'+Student,
        }).then(function(response){
            $scope.loadData();
        },function(error){
            console("Failed to delete :"+Student)
        })
    };

    //Cler inputs
    function ClearFields() {
        $scope.Student.sid = "";
        $scope.Student.lastName = "";
        $scope.Student.firstName = "";
        $scope.Student.emil = "";
        $scope.Student.phone = "";
    }    
}