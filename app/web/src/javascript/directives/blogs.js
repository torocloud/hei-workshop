(function () {

  angular
    .module('app')
    .directive('appBlogs', appBlogs)

  appBlogs.$inject = [
    '$rootScope'
  ]

  function appBlogs ($rootScope) {
    let directive = {
      scope: {currentUser: '='},
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

    function setInitial() {
      blog.editMode    = false
      blog.viewMode    = false
      blog.getEntry    = getEntry
      blog.getEntries  = getEntries
      blog.editContent = editContent
      blog.editEntry   = editEntry
      blog.deleteEntry = deleteEntry
    }

    setInitial()

    // Check whether the state is not an entry view
    // then fetch entries from server
    if ($state.current.name === 'app.view') {
      blog.viewMode = true
      blog.getEntry($stateParams.id)
    } else {
      blog.viewMode = false
    }

    blog.getEntries()

    function getEntries() {
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
          $state.go('app.default')
        }
      )
    }

    function editContent () {
      blog.editMode = true
      $timeout(function () {
        CKEDITOR.replace('editBlogEditor')
      })
    }

    function editEntry (entry) {
      entry.content = CKEDITOR.instances.editBlogEditor.getData()
      blog.editMode = false

      api.postBlog(entry).then(
        (data) => {
          blog.alert = {
            success: true,
            message: 'Successfully saved'
          }
          blog.entry = data
          getEntries()

          $timeout(function () {
            blog.alert = null
          }, 1500)
        },
        (error) => {
          blog.alert = {
            success: false,
            message: `Something Went Wrong... ${error}`
          }
        }
      )
    }

    function deleteEntry () {
      api.deleteBlog($state.params.id).then(
        (data) => {
          blog.alert = {
            success: true,
            message: 'Successfully deleted'
          }

          $timeout(function () {
            blog.alert = null
            $state.go('app.default')
          }, 1500)
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
