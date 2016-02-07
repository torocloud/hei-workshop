(function () {
  'use strict'

  angular
    .module('app')
    .controller('BlogsCtrl', BlogsCtrl)

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
      this.alert = false
      this.data = data
    }, (error) => {
      this.alert = true
    })

  }

})()
