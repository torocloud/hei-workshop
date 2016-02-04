(function () {
  'use strict'

  angular
    .module('app')
    .service('context', Context)

  Context.$inject = ['$cookies']

  function Context ($cookies) {
    return {
      currentSession: null,
      setCookies (context) {
        for (var obj in context) {
          $cookies.put(obj, context[obj], {path: '/'})
        }
      },
      setCurrentUser (data) {
        return (data && Object.keys(data).length > 0) ?
          this.setCookies(data) : null
      },
      getCurrentUser () {
        return (Object.keys($cookies.getAll()).length > 0) ?
          Object.assign({}, {
            username: $cookies.get('username'),
            token: $cookies.get('token'),
          }) : null
      }
    }
  }

})()
