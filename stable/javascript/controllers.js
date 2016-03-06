angular
  .module('app')
  .controller('user', userController)

  userController.$inject = [
    '$scope',
    '$localStorage',
    'api'
  ]

  function userController ($scope, $localStorage, api) {
    $scope.loggedIn = $localStorage.token != undefined;

    $scope.logout = function() {
      api.setToken();
    }

    $scope.$on('token::set', function() {
      $scope.loggedIn = $localStorage.token != undefined;
    })
  }
