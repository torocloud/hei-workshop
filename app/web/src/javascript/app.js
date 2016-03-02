(function () {
  'use strict'

  angular
    .module('app', [
      'ui.router',
      'ngCookies',
      'ngResource',
      'ngSanitize'
    ])
    .run(InitStates)
    .filter('capitalize', Capitalize)

    InitStates.$inject = [
      '$rootScope',
      '$stateParams',
      '$state'
    ]

    function InitStates ($rootScope, $stateParams, $state) {
      $rootScope.$state = $state
      $rootScope.$stateParams = $stateParams
    }

    function Capitalize () {
      return (input, all) => {
        let reg = (all) ? /([^\W_]+[^\s-]*) */g : /([^\W_]+[^\s-]*)/
        return (!!input) ?
          input.replace(reg, (txt) => {
            return txt.charAt(0).toUpperCase() +txt.substr(1).toLowerCase()
        }) : ''
      }
    }
})()
