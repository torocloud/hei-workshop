(function () {

  angular
    .module('app')
    .directive('appBlogs', appBlogs)

  appBlogs.$inject = [
    '$rootScope'
  ]

  function appBlogs ($rootScope) {
    let directive = {
      scope: true,
      restrict: 'E',
      templateUrl: '/app/views/blogs.html',
      controller: BlogsCtrl,
      controllerAs: 'blogs',
      bindToController: true,
      replace: true,
      transclude: true,
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

  BlogsCtrl.$inject = [
    '$scope',
    'api'
  ]

  function BlogsCtrl ($scope, api) {
    let blogs = this

    /**
     * @name getBlogs
     * @desc gets all blogs from the backend
     * @param
     * @returns {Object} response
     */

    api.getBlogs().then((data) => {
      blogs.alert = false
      blogs.data = data
    }, (error) => {
      blogs.alert = true
    })

  }

})()
