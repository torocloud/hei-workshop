(function () {
  'use strict'

  angular
    .module('app')
    .controller('GetBlog', GetBlog)

  GetBlog.$inject = [
    '$scope',
    '$state',
    'api'
  ]

  function GetBlog ($scope, $state, api) {
    let view = this
    this.blogId = $state.params.id

    /**
     * @name getBlog
     * @desc gets a blog from the backend
     * @param {Number} blog ID
     * @returns {Object} response
     */

    api.getBlog(this.blogId).then((data) => {
      this.blog = data
    }, (error) => {
      return $state.go('app.default')
    })

    /**
     * @name deleteBlog
     * @desc deletes a blog from the backend
     * @param {Object} blog ID
     * @returns {Object} response
     */

    this.deleteBlog = () => {
      api.deleteBlog(this.blogId).then((data) => {
        return $state.go('app.default')
      }, (error) => {
        view.alert = true
      })
    }

  }

})()
