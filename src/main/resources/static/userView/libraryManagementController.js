var mainApp = angular.module("mainApp", ['ngRoute']);

mainApp.config(function($routeProvider) {
	$routeProvider
		.when('/home', {
			templateUrl: 'index.html',
			controller: 'userController'
		})
		.when('/viewUsers', {
			templateUrl: 'userView/viewUsers.html',
            controller:'userController'
		})
		.when('/addUser', {
            templateUrl: 'userView/addUser.html',
            controller: 'userController'
        })
        .when('/editUser/:sid', {
            templateUrl: 'userView/addUser.html',
            controller: 'userController'
        })
        .when('/viewBooks', {
          	templateUrl: 'userView/viewBooks.html',
            controller:'bookController'
        })
        .when('/addBook', {
            templateUrl: 'userView/addBook.html',
            controller: 'bookController'
        })
        .when('/editBook/:bid', {
            templateUrl: 'userView/addBook.html',
            controller: 'bookController'
        })
        .when('/issueBook/:uid', {
                    templateUrl: 'userView/issueBook.html',
                    controller: 'issueBookController'
                })

//		.otherwise({
//			redirectTo: 'userView//viewUser'
//		});
});

mainApp.controller('userController', ['$scope', '$http','$location','$routeParams',userController]);

function userController($scope, $http,$location,$routeParams) {

    $scope.loadData = function (type) {
    url = (type)?'/user?type='+type:'alluser';
        $http({
            method: "GET",
            url:url ,
         }).then(function (response) {
             $scope.Users  = response.data;
        }, function (error) {
            console("Failed to load data....");
        });
    }
$scope.loadData("");
    $scope.isUpdate = function(){
        if($routeParams.sid){
            $scope.getSingleData($routeParams.sid)
        }
    }
    $scope.openPage = function(userType){
        $location.path('/viewUsers');
        $scope.loadData(userType)
    }

    $scope.User={};
    $scope.getSingleData = function (userID) {
        $http({
            method: "GET",
            url: '/user/'+userID,
         }).then(function (response) {
             userDetail = response.data;
             $scope.User.uid = userDetail.uid;
             $scope.User.firstName = userDetail.firstName;
             $scope.User.lastName = userDetail.lastName;
             $scope.User.email = userDetail.email;
             $scope.User.mobile = userDetail.mobile;
             $scope.User.type = userDetail.type;
        }, function (error) {
            console("Failed to load data....");
        });

    }

    //Add/Update operation
    $scope.save = function (User) {
        var methodIs = (User.uid)?"PUT":"POST";
        $http({
            method: methodIs,
            url: '/user',
            data: User
        }).then(function (response) {
             $scope.openPage(User.type);
        }, function (error) {
            console("Failed to Update data....");
        });
    };

    //Delete operation
    $scope.deletecustomer = function (User) {
        $http({
            method:"DELETE",
            url:'/user/'+User.uid,
        }).then(function(response){
            $scope.loadData(User.type);
        },function(error){
            console("Failed to delete :"+User)
        })
    };

    //Cler inputs
    function ClearFields() {
        $scope.User.sid = "";
        $scope.User.lastName = "";
        $scope.User.firstName = "";
        $scope.User.emil = "";
        $scope.User.phone = "";
    }

    $scope.getIdOfStudent=function(userId){
        console.log(userId);
    }

    //THis Function is not in used jut here for reference I want to makemodal wiht help of this method but this angular version its not used
    $scope.GetDetails = function (customer) {
                    $scope.modalInstance = $uibModal.open({
                        ariaLabelledBy: 'modal-title',
                        ariaDescribedBy: 'modal-body',
                        templateUrl: 'popup.htm',
                        controller: 'ModelHandlerController',
                        controllerAs: '$ctrl',
                        size: 'sm', // sm-small // md-medium // lg-large
                        resolve: {
                            Customer: function () {
                                return customer;
                            }
                        }
                    });
                }
}

mainApp.controller('bookController', ['$scope', '$http','$location','$routeParams',bookController]);

function bookController($scope, $http,$location,$routeParams) {

    $scope.bookissue = function(bookId){
        $userId  = $routeParams.uid
    }

//$scope.getUserId();
    $scope.loadBooks = function () {
        $http({
            method: "GET",
            url:'book' ,
         }).then(function (response) {
             $scope.Books  = response.data;
        }, function (error) {
            console("Failed to load data....");
        });
    }
    $scope.loadBooks();

    $scope.openViewBookPage = function(){
        $location.path('/viewBooks');
        $scope.loadBooks()
    }
    //Add/Update operation
        $scope.save = function (Book) {
            var methodIs = (Book.bid)?"PUT":"POST";
            $http({
                method: methodIs,
                url: '/book',
                data: Book
            }).then(function (response) {
                 $scope.openViewBookPage();
            }, function (error) {
                console("Failed to Update data....");
            });
        };

        $scope.isUpdate = function(){
                if($routeParams.bid){
                    $scope.getSingleData($routeParams.bid)
                }
            }
            $scope.Book={};
            $scope.getSingleData = function (bookID) {
                $http({
                    method: "GET",
                    url: '/book/'+bookID,
                 }).then(function (response) {
                     bookDetail = response.data;
                     $scope.Book.bid = bookDetail.bid;
                     $scope.Book.bookName = bookDetail.bookName;
                     $scope.Book.author = bookDetail.author;
                     $scope.Book.totalCopy = bookDetail.totalCopy;
                     $scope.Book.availableCopy = bookDetail.availableCopy;
                }, function (error) {
                    console("Failed to load data....");
                });

            }

                //Delete operation
                $scope.deleteBook = function (Book) {
                    $http({
                        method:"DELETE",
                        url:'/book/'+Book.bid,
                    }).then(function(response){
                        $scope.loadBooks();
                    },function(error){
                        console("Failed to delete :"+Book)
                    })
                };
}

mainApp.controller('issueBookController', ['$scope', '$http','$location','$routeParams',issueBookController]);

function issueBookController($scope, $http,$location,$routeParams) {

        $scope.getStudentDetail = function(){
            $http({
                method: "GET",
                url: '/user/'+$routeParams.uid,
             }).then(function (response) {
                 userDetail = response.data;
                 $scope.userfullName = userDetail.firstName+" "+userDetail.lastName;
            }, function (error) {
                console("Failed to load data....");
            });
        }

    $scope.loadBooks = function () {
        $http({
            method: "GET",
            url:'book' ,
         }).then(function (response) {
             $scope.Books  = response.data;
        }, function (error) {
            console("Failed to load data....");
        });
    }
    $scope.loadBooks();

    $scope.bookissue = function(book){
        if(book.availableCopy==0){
            return;
        }
        studentId  = $routeParams.uid;
        issueBookObj={};
        issueBookObj.sid=studentId;
        issueBookObj.bid=book.bid;
        issueBookObj.issueBy=1;
        issueBookObj.active=0;
        issueBookObj.availableCopy=book.availableCopy-1;
        $http({
            method: "POST",
            url:'issueBook' ,
            data:issueBookObj
        }).then(function (response) {
            $scope.loadBooks();
        }, function (error) {
            console("Failed to load data....");
        });
    }

    $scope.booksubmit = function(book){
            if(book.availableCopy==book.totalCopy){
                return;
            }
            studentId  = $routeParams.uid;
            issueBookObj={};
            issueBookObj.sid=studentId;
            issueBookObj.bid=book.bid;
            issueBookObj.submitBy=17;
            issueBookObj.active=1;
            issueBookObj.availableCopy=book.availableCopy+1;
            $http({
                method: "PUT",
                url:'submitBook' ,
                data:issueBookObj
            }).then(function (response) {
                $scope.loadBooks();
            }, function (error) {
                console("Failed to load data....");
            });
        }
  }
