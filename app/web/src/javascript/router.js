(function () {
  'use strict';

  angular
    .module('app')
    .config(AppConfig)

  AppConfig.$inject = [
    '$routerProvider',
    '$locationProvider'
  ]

  function AppConfig ($routerProvider, $locationProvider) {

    $locationProvider
      .html5Mode(false)
      .hashPrefix('!')
  }

})();
