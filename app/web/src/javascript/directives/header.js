(function () {
  'use strict';

  angular
    .module('app')
    .directive('header', HeaderDirective)

  HeaderDirective.$inject = []

  function HeaderDirective () {
    let directive = {}

    return directive
  }

})();
