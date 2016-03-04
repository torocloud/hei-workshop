(function () {
  'use strict'

  angular
    .module('app')
    .directive('appFooter', FooterDirective)

  FooterDirective.$inject = [
    '$rootScope'
  ]

  function FooterDirective ($rootScope) {
    let views = '/app/views/'
    let directive = {
      scope: {currentUser: '='},
      restrict: 'E',
      replace: true,
      transclude: true,
      templateUrl: `${views}footer.html`,
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
