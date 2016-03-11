'use strict';

(function () {

  angular.module('app', ['ngRoute', 'ngStorage', 'ngSanitize']);
})();
'use strict';

(function () {

  angular.module('app').config(AppConfig);

  AppConfig.$inject = ['$routeProvider', '$locationProvider'];

  function AppConfig($routeProvider, $locationProvider) {
    $locationProvider.html5Mode({
      enabled: true,
      requireBase: false
    });

    $routeProvider.when('/', { template: '<home></home>' }).when('/login', { template: '<login></login>' }).when('/post', { template: '<post></post>' }).when('/view/:id', { template: '<blog></blog>' });
  }
})();
'use strict';

(function () {
  angular.module('app').directive('home', home).directive('login', login).directive('post', post).directive('blog', blog);

  // home directive
  function home() {
    var directive = {
      restrict: 'E',
      templateUrl: '/app/views/home.html',
      controller: BlogsCtrl
    };

    return directive;
  }

  BlogsCtrl.$inject = ['$scope', 'api'];

  function BlogsCtrl($scope, api) {
    api.getBlogs().then(function (response) {
      $scope.blogs = response;
    }, function (error) {
      alert('Something went wrong');
    });
  }
  // end of home directive

  // login directive
  function login() {
    var directive = {
      restrict: 'E',
      templateUrl: '/app/views/login.html',
      controller: LoginCtrl
    };

    return directive;
  }

  LoginCtrl.$inject = ['$scope', '$location', 'api'];

  function LoginCtrl($scope, $location, api) {
    $scope.credentials = {
      username: '',
      password: ''
    };

    $scope.login = function () {
      api.login($scope.credentials).then(function (response) {
        api.setToken(response.token);
        $location.path('/');
      }, function (error) {
        alert('Something went wrong');
      });
    };
  }
  // end of login directive

  // post directive
  function post() {
    var directive = {
      restrict: 'E',
      templateUrl: '/app/views/post.html',
      controller: PostCtrl
    };

    return directive;
  }

  PostCtrl.$inject = ['$scope', '$location', 'api'];

  function PostCtrl($scope, $location, api) {
    $scope.blog = {
      title: '',
      content: ''
    };

    CKEDITOR.replace('postBlogEditor');

    $scope.post = function () {
      $scope.blog.content = CKEDITOR.instances.postBlogEditor.getData();
      api.post($scope.blog).then(function (response) {
        $location.path('/');
      }, function (error) {
        alert('Something went wrong');
      });
    };
  }
  // end of post directive

  // home directive
  function blog() {
    var directive = {
      restrict: 'E',
      templateUrl: '/app/views/blog.html',
      controller: BlogCtrl
    };

    return directive;
  }

  BlogCtrl.$inject = ['$scope', '$location', '$routeParams', '$timeout', 'api'];

  function BlogCtrl($scope, $location, $routeParams, $timeout, api) {
    $scope.editMode = false;

    api.getBlog($routeParams.id).then(function (response) {
      $scope.blog = response;
    }, function (error) {
      alert('Something went wrong');
      $location.path('/');
    });

    $scope.edit = function (option) {
      $scope.editMode = option;
      option == true ? $scope.unsavedBlog = angular.copy($scope.blog) : $scope.blog = $scope.unsavedBlog;

      $timeout(function () {
        CKEDITOR.replace('editBlogEditor');
      });
    };

    $scope.update = function (entry) {
      $scope.blog.content = CKEDITOR.instances.editBlogEditor.getData();
      api.post($scope.blog).then(function (response) {
        $scope.editMode = false;
      }, function (error) {
        alert('Something went wrong');
      });
    };

    $scope.delete = function () {
      api.deleteBlog($scope.blog.id).then(function (response) {
        $location.path('/');
      }, function (error) {
        alert('Something went wrong');
      });
    };
  }
  // end of home directive
})();
'use strict';

(function () {

  angular.module('app').controller('UserCtrl', UserCtrl);

  UserCtrl.$inject = ['$scope', '$localStorage', 'api'];

  console.log(document.querySelector('body[data-endpoint]').getAttribute('data-endpoint'));

  function UserCtrl($scope, $localStorage, api) {
    $scope.loggedIn = $localStorage.token != undefined;

    $scope.logout = function () {
      api.setToken();
    };

    $scope.$on('token::set', function () {
      $scope.loggedIn = $localStorage.token != undefined;
    });
  }
})();
'use strict';

angular.module('app').factory('api', ApiFactory);

ApiFactory.$inject = ['$rootScope', '$http', '$q', '$location', '$localStorage'];

function ApiFactory($rootScope, $http, $q, $location, $localStorage) {
  var api = this;
  var baseurl = document.querySelector('body[data-endpoint]').getAttribute('data-endpoint');

  var headers = {
    'Content-Type': 'application/json',
    'Authorization': $localStorage.token ? 'Bearer ' + $localStorage.token : undefined
  };

  api.login = function (credentials) {
    return $http.post(baseurl + 'login', credentials, { headers: headers }).then(handleSuccess, handleError);
  };

  api.setToken = function (token) {
    token ? $localStorage.token = token : delete $localStorage.token;
    headers.Authorization = token ? 'Bearer ' + token : undefined;
    $rootScope.$broadcast('token::set');
  };

  api.post = function (blog) {
    return $http.post(baseurl + 'blogs', blog, { headers: headers }).then(handleSuccess, handleError);
  };

  api.getBlogs = function () {
    return $http.get(baseurl + 'blogs', { headers: headers }).then(handleSuccess, handleError);
  };

  api.getBlog = function (id) {
    return $http.get(baseurl + 'blogs/' + id, { headers: headers }).then(handleSuccess, handleError);
  };

  api.deleteBlog = function (id) {
    return $http.delete(baseurl + 'blogs/' + id, { headers: headers }).then(handleSuccess, handleError);
  };

  function handleSuccess(response) {
    return response.data;
  }

  function handleError(error) {
    switch (error.status) {
      case 401:
      case -1:
        api.setToken();
        $location.path('/login');
        return $q.reject(error.status);
        break;
      default:
        return $q.reject(error.status);
    }
  }

  return this;
}