angular
  .module('app')
  .factory('api', ApiFactory)

  ApiFactory.$inject = [
    '$rootScope',
    '$http',
    '$q',
    '$location',
    '$localStorage'
  ]

  function ApiFactory ( $rootScope, $http, $q, $location, $localStorage ) {
    var api = this;
    var baseurl = document
      .querySelector('body[data-endpoint]')
      .getAttribute('data-endpoint')

    var headers = {
      'Content-Type': 'application/json',
      'Authorization': ( $localStorage.token ) ?
        'Bearer ' + $localStorage.token : undefined
    }

    api.login = function( credentials ) {
      return $http.post( baseurl + 'login', credentials, {headers} )
        .then( handleSuccess, handleError );
    }

    api.setToken = function( token ) {
      ( token ) ?
        $localStorage.token = token : delete $localStorage.token
      headers.Authorization = ( token ) ?
        'Bearer ' + token : undefined
      $rootScope.$broadcast('token::set');
    }

    api.post = function( blog ) {
      return $http.post( baseurl + 'blogs', blog, {headers} )
        .then( handleSuccess, handleError );
    }

    api.getBlogs = function() {
      return $http.get( baseurl + 'blogs', {headers} )
        .then( handleSuccess, handleError );
    }

    api.getBlog = function( id ) {
      return $http.get( baseurl + 'blogs/' + id, {headers} )
        .then( handleSuccess, handleError );
    }

    api.deleteBlog = function( id ) {
      return $http.delete( baseurl + 'blogs/' + id, {headers} )
        .then( handleSuccess, handleError );
    }

    function handleSuccess(response) {
      return response.data;
    }

    function handleError(error) {
      switch( error.status ) {
        case 401:
        case -1:
          api.setToken();
          $location.path( '/login' )
          return $q.reject( error.status );
          break;
        default:
          return $q.reject( error.status );
      }

    }

    return this
  }
