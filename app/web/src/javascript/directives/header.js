(function () {
  'use strict'

  angular
    .module('app')
    .directive('appHeader', HeaderDirective)

  HeaderDirective.$inject = [
    '$rootScope'
  ]

  function HeaderDirective ($rootScope) {
    let views = '/app/views/'
    let directive = {
      scope: {currentUser: '='},
      restrict: 'E',
      replace: true,
      transclude: true,
      templateUrl: `${views}header.html`,
      link: (scope, element, attrs) => {
        $rootScope.$on('$stateChangeStart', () => {
          element.remove()
          element.parent().empty()
          scope.$destroy()
        })
      }
    }

    return directive
  }


})()
