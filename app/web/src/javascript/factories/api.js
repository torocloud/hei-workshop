(function () {
  'use strict'

  angular
    .module('app')
    .factory('api', ApiFactory)

  ApiFactory.$inject = [
    '$rootScope',
    '$http',
    '$q',
    '$state',
    'context'
  ]

  function ApiFactory ($rootScope, $http, $q, $state, userContext) {
    let baseurl = 'http://127.0.0.1:8080/api/'
    let user = userContext.getCurrentUser()
    let headers = {
      'Content-Type': 'application/json',
      'Authorization': ( user && user.token) ?
        `Bearer ${user.token}` : undefined
    }

    this.login = (credentials) =>
      execute( $http.post(`${baseurl}login`, credentials, {headers}) )

    this.getBlogs = () =>
      execute( $http.get(`${baseurl}blogs`, {headers}) )

    this.postBlog = (newBlog) =>
      execute( $http.post(`${baseurl}blogs`, newBlog, {headers}) )

    this.getBlog = (blogId) =>
      execute( $http.get(`${baseurl}blogs/${blogId}`, {headers}) )

    this.deleteBlog = (blogId) =>
      execute( $http.delete(`${baseurl}blogs/${blogId}`, {headers}) )

    function execute(request) {
      return ( request.then(handleSuccess, handleError) )
    }

    function handleSuccess(response) {
      return response.data
    }

    function handleError(error) {
      switch (error.status) {
        case 401:
          $state.go('app.login')
          return $q.reject(error)
          break
        case -1:
          $state.go('app.logout')
          return $q.reject(error)
          break
        default:
          return $q.reject(error)
      }
    }

    // notifies ApiFactory to update `user` and `Authorization` on login/logout
    $rootScope.$on('auth::setUser', () => {
      user = userContext.getCurrentUser()
      headers.Authorization = (user && user.token) ?
        `Bearer ${user.token}` : undefined
    })

    return this
  }

})()
