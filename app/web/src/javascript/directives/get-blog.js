(function () {

  angular
    .module('app')
    .directive('appGetBlog', appGetBlog)

  appGetBlog.$inject = [
    '$rootScope'
  ]

  function appGetBlog ($rootScope) {
    let directive = {
      scope: true,
      restrict: 'E',
      templateUrl: '/app/views/blog.html',
      controller: BlogCtrl,
      controllerAs: 'view',
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

  BlogCtrl.$inject = [
    '$scope',
    '$state',
    'api'
  ]

  function BlogCtrl ($scope, $state, api) {
    let view = this
    view.blogId = $state.params.id

    /**
     * @name getBlog
     * @desc gets a blog from the backend
     * @param {Number} blog ID
     * @returns {Object} response
     */

    api.getBlog(view.blogId).then((data) => {
      view.blog = data
    }, (error) => {
      return $state.go('app.default')
    })

    /**
     * @name deleteBlog
     * @desc deletes a blog from the backend
     * @param {Object} blog ID
     * @returns {Object} response
     */

    view.deleteBlog = () => {
      api.deleteBlog(view.blogId).then((data) => {
        return $state.go('app.default')
      }, (error) => {
        view.alert = true
      })
    }
  }

})()
