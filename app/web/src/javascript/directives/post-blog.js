(function () {

  angular
    .module('app')
    .directive('appPostBlog', appPostBlog)

  appPostBlog.$inject = [
    '$rootScope'
  ]

  function appPostBlog ($rootScope) {
    let directive = {
      scope: true,
      restrict: 'E',
      templateUrl: '/app/views/post-blog.html',
      controller: PostBlogCtrl,
      controllerAs: 'post',
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

  PostBlogCtrl.$inject = [
    '$scope',
    '$state',
    'api'
  ]

  function PostBlogCtrl ($scope, $state, api) {
    let post = this

    post.newBlog = {
      title: '',
      content: ''
    }

    /**
     * @name postBlog
     * @desc post a new blog to the backend
     * @param {Object} blog data to be submitted
     * @returns {Object} response
     */

    post.postBlog = () => {
      api.postBlog(post.newBlog).then((data) => {
        return $state.go('app.default')
      }, (error) => {
        post.alert = true
      })
    }
  }

})()
