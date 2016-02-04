(function () {
  'use strict'

  angular
    .module('app')
    .config(AppConfig)

  AppConfig.$inject = [
    '$routeProvider',
    '$locationProvider'
  ]

  function AppConfig ($routeProvider, $locationProvider) {

    $locationProvider
      .html5Mode(false)
      .hashPrefix('!')

  }

})()
