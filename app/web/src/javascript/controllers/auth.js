(function () {
  'use strict'

  angular
    .module('app')
    .controller('AuthCtrl', AuthController)

  AuthController.$inject = [
    '$scope',
    'context'
  ]

  function AuthController ($scope, context) {
  }

})()
