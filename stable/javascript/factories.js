angular
  .module('app')
  .factory('api', ApiFactory)

  ApiFactory.$inject = [
    '$rootScope',
    '$http',
    '$q',
    '$localStorage'
  ]

  function ApiFactory ( $rootScope, $http, $q, $localStorage ) {
    var baseurl = 'https://hei-workshop.herokuapp.com/api/';
    var headers = {
      'Content-Type': 'application/json',
      'Authorization': ( $localStorage.token ) ?
        'Bearer ' + $localStorage.token : undefined
    }

    this.login = function( credentials ) {
      return $http.post( baseurl + 'login', credentials, {headers} )
      .then( handleSuccess, handleError );
    }

    this.setToken = function( token ) {
      $localStorage.token = token;
      headers.Authorization = 'Bearer ' + token;
      $rootScope.$broadcast('token::set');
    }

    this.post = function( blog ) {
      return $http.post( baseurl + 'blogs', blog, {headers} )
      .then( handleSuccess, handleError );
    }

    this.getBlogs = function() {
      return $http.get( baseurl + 'blogs', {headers} )
        .then( handleSuccess, handleError );
    }

    this.getBlog = function( id ) {
      return $http.get( baseurl + 'blogs/' + id, {headers} )
        .then( handleSuccess, handleError );
    }

    this.deleteBlog = function( id ) {
      return $http.delete( baseurl + 'blogs/' + id, {headers} )
        .then( handleSuccess, handleError );
    }

    function handleSuccess(response) {
      return response.data;
    }

    function handleError(error) {
      return $q.reject( error.status );
    }

    return this
  }
