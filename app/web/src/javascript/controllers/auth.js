(function () {
  'use strict'

  angular
    .module('app')
    .controller('AuthCtrl', AuthController)

  AuthController.$inject = [
    '$scope',
    '$state',
    '$rootScope',
    'context'
  ]

  function AuthController ($scope, $state, $rootScope, userContext) {
    let auth = this

    auth.currentUser = userContext.getCurrentUser()

    $rootScope.$on('$stateChangeSuccess', () => {
      (userContext.getCurrentUser() == null &&
        $state.current.data.private) &&
        $state.go('app.default', {reload: true})
    })
  }

})()
