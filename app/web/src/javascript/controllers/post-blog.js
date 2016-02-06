(function () {
  'use strict'

  angular
    .module('app')
    .controller('PostBlog', PostBlog)

  PostBlog.$inject = [
    '$scope',
    '$state',
    'api'
  ]

  function PostBlog ($scope, $state, api) {
    let post = this

    this.newBlog = {
      title: '',
      content: ''
    }

    /**
     * @name postBlog
     * @desc post a new blog to the backend
     * @param {Object} blog data to be submitted
     * @returns {Object} response
     */

    this.postBlog = () => {
      api.postBlog(this.newBlog).then((data) => {
        return $state.go('app.default')
      }, (error) => {
        this.alert = true
      })
    }


  }

})()
