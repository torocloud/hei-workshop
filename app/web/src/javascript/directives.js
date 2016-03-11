(function () {
  angular
    .module('app')
    .directive('home', home)
    .directive('login', login)
    .directive('post', post)
    .directive('blog', blog)

  // home directive
    function home () {
      var directive = {
        restrict: 'E',
        templateUrl: '/app/views/home.html',
        controller: BlogsCtrl
      }

      return directive
    }

    BlogsCtrl.$inject = [
      '$scope',
      'api'
    ]

    function BlogsCtrl ($scope, api) {
      api.getBlogs()
        .then( function(response) {
          $scope.blogs = response;
        }, function (error) {
          alert('Something went wrong');
        });
    }
  // end of home directive

  // login directive
    function login () {
      var directive = {
        restrict: 'E',
        templateUrl: '/app/views/login.html',
        controller: LoginCtrl
      }

      return directive
    }

    LoginCtrl.$inject = [
      '$scope',
      '$location',
      'api'
    ]

    function LoginCtrl ($scope, $location, api) {
      $scope.credentials = {
        username: '',
        password: ''
      }

      $scope.login = function () {
        api.login( $scope.credentials )
          .then( function(response) {
            api.setToken( response.token );
            $location.path( '/' );
          }, function (error) {
            alert('Something went wrong');
          });
      }
    }
  // end of login directive

  // post directive
    function post () {
      var directive = {
        restrict: 'E',
        templateUrl: '/app/views/post.html',
        controller: PostCtrl
      }

      return directive
    }

    PostCtrl.$inject = [
      '$scope',
      '$location',
      'api'
    ]

    function PostCtrl ($scope, $location, api) {
      $scope.blog = {
        title: '',
        content: ''
      }

      CKEDITOR.replace('postBlogEditor')

      $scope.post = function () {
        $scope.blog.content = CKEDITOR.instances.postBlogEditor.getData()
        api.post( $scope.blog )
          .then( function(response) {
            $location.path( '/' );
          }, function (error) {
            alert('Something went wrong');
          });
      }
    }
  // end of post directive

  // home directive
    function blog () {
      var directive = {
        restrict: 'E',
        templateUrl: '/app/views/blog.html',
        controller: BlogCtrl
      }

      return directive
    }

    BlogCtrl.$inject = [
      '$scope',
      '$location',
      '$routeParams',
      '$timeout',
      'api'
    ]

    function BlogCtrl ($scope, $location, $routeParams, $timeout, api) {
      $scope.editMode = false;

      api.getBlog( $routeParams.id )
        .then( function(response) {
          $scope.blog = response;
        }, function (error) {
          alert('Something went wrong');
          $location.path( '/' );
        });

      $scope.edit = function (option) {
        $scope.editMode = option;
        ( option == true ) ?
          $scope.unsavedBlog = angular.copy( $scope.blog ) : $scope.blog = $scope.unsavedBlog

        $timeout(function () {
          CKEDITOR.replace('editBlogEditor')
        })
      }

      $scope.update = function (entry) {
        $scope.blog.content = CKEDITOR.instances.editBlogEditor.getData()
        api.post( $scope.blog )
          .then( function(response) {
            $scope.editMode = false;
          }, function (error) {
            alert('Something went wrong');
          });
      }

      $scope.delete = function () {
        api.deleteBlog( $scope.blog.id )
          .then( function(response) {
            $location.path( '/' );
          }, function (error) {
            alert('Something went wrong');
          });
      }
    }
  // end of home directive
})()
