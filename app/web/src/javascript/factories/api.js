(function () {
  'use strict'

  angular
    .module('app')
    .factory('api', ApiFactory)

  ApiFactory.$inject = [
    '$http'
  ]

  function ApiFactory ($http) {
    let baseurl = 'http://127.0.0.1:8080/api/'
    let headers = {
      'Content-Type': 'application/json'
    }

    this.login = (credentials) => {
      return $http.post(`${baseurl}login`, credentials, {headers})
    }

    return this
  }

})()
