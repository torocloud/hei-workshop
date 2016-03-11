(function () {

  angular
    .module('app')
    .controller('UserCtrl', UserCtrl)

    UserCtrl.$inject = [
      '$scope',
      '$localStorage',
      'api'
    ]

    function UserCtrl ($scope, $localStorage, api) {
      $scope.loggedIn = $localStorage.token != undefined;

      $scope.logout = function() {
        api.setToken();
      }

      $scope.$on('token::set', function() {
        $scope.loggedIn = $localStorage.token != undefined;
      })
    }

})()
