angular
  .module('app')
  .controller('user', userController)

  userController.$inject = [
    '$scope',
    '$localStorage'
  ]

  function userController ($scope, $localStorage) {
    $scope.loggedIn = $localStorage.token != undefined;

    $scope.logout = function() {
      delete $localStorage.token;
      $scope.loggedIn = false;
    }

    $scope.$on('token::set', function() {
      $scope.loggedIn = $localStorage.token != undefined;
    })
  }
