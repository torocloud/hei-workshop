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
      controllerAs: 'blog',
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
    '$state',
    '$stateParams',
    '$timeout',
    'api'
  ]

  function BlogsCtrl ($scope, $state, $stateParams, $timeout, api) {
    let blog = this

    blog.editMode   = false
    blog.getEntry   = getEntry
    blog.getEntries = getEntries
    blog.editEntry  = editEntry

    // Check whether the state is not an entry view
    // then fetch entries from server
    if ($state.current.name !== 'app.default.blog') {
      blog.getEntries()
    } else {
      blog.getEntry($stateParams.id)
    }

    $scope.$watch('blog.entries', (value) => {
      if (!value) { return }
      blog.entries = value
    })

    $scope.$watch('blog.entry', (value) => {
      if (!value) { return }
      blog.entry = value
    })


    function getEntries () {
      api.getBlogs().then(
        (data) => {
          blog.entries = data
        },
        (error) => {
          blog.alert = {}
        }
      )
    }


    function getEntry (id) {
      api.getBlog(id).then(
        (data) => {
          blog.entry = data
        },
        (error) => {

        }
      )
    }

    function editEntry (content) {
      blog.editMode = false
      api.postBlog(content).then(
        (data) => {
          blog.alert = {
            success: true,
            message: 'Successfully saved'
          }
          blog.entry = data

          $timeout(function () {
            blog.alert = null
          }, 2000)
        },
        (error) => {
          blog.alert = {
            success: false,
            message: `Something Went Wrong... ${error}`
          }
        }
      )
    }


  }

})()
