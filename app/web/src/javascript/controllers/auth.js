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

    // notifies the AuthController to update `currentUser` on login/logout
    $rootScope.$on('auth::setUser', () => {
      auth.currentUser = userContext.getCurrentUser()
    })
  }

})()
