(function () {
  'use strict'

  angular
    .module('app', [
      'ui.router',
      'ngCookies',
      'ngResource'
    ])
    .run(InitStates)

    InitStates.$inject = [
      '$rootScope',
      '$stateParams',
      '$state'
    ]

    function InitStates ($rootScope, $stateParams, $state) {
      $rootScope.$state = $state
      $rootScope.$stateParams = $stateParams
    }
})()
