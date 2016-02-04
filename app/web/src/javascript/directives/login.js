(function () {
  'use strict'

  angular
    .module('app')
    .directive('login', LoginDirective)

  LoginDirective.$inject = []

  function LoginDirective () {
    let directive = {}

    return directive
  }

})()
