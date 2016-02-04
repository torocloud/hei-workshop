(function () {
  'use strict'

  angular
    .module('app')
    .factory('api', ApiFactory)

  ApiFactory.$inject = [
    '$scope',
    'context'
  ]

  function ApiFactory ($scope, context) {
  }

})()
