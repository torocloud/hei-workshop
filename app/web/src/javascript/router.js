(function () {
  'use strict'

  angular
    .module('app')
    .config(AppConfig)

  AppConfig.$inject = [
    '$stateProvider',
    '$locationProvider',
    '$urlRouterProvider'
  ]

  function AppConfig ($stateProvider, $locationProvider, $urlRouterProvider) {
    let views    = '/app/views/'

    $locationProvider.html5Mode({
      enabled: true,
      requireBase: false
    })

    $urlRouterProvider.rule(($injector, $location) => {
      let path = $location.url()

      if (path[path.length - 1] === '/' || path.indexOf('/?') > -1) {
        return
      }

      if (path.indexOf('?') > -1) {
        return path.replace('?', '/?')
      }

      return `${path}/`
    })


    $stateProvider
      .state('app', {
        url: '/',
        abstract: true,
        data: {private: false},
        views: {
          '@': {
            template: `
              <header ui-view="header" style="padding-top: 45px;"></header>
              <div ui-view="content" role="main"></div>
              <footer ui-view="footer"></footer>
            `
          }
        }
      })
      .state('app.default', {
        url: '',
        cache: false,
        data: {private: false},
        views: {
          'header@app': {template: '<app-header current-user="auth.currentUser.username"/>'},
          'content@app': {templateUrl: `${views}index.html`}
        }
      })

      .state('app.login', {
        url: 'admin/login',
        cache: false,
        data: {private: false},
        views: {
          'content@app': {template: '<user-login ng-model="auth.user"/>'}
        }
      })

      .state('app.logout', {
        url: 'logout/',
        cache: false,
        data: {private: false},
        views: {
          'content@app': {
            template: '',
            controller: [
              '$cookies',
              '$state',
              '$timeout',
              'context',
              ($cookies, $state, $timeout, userContext) => {
                $cookies.remove('username', {path: '/'})
                $cookies.remove('sessionId', {path: '/'})
                $cookies.remove('loginSucceeded', {path: '/'})
                userContext.setCurrentUser(null)
                $timeout(() => {
                  $state.go('app.login', {reload: true})
                }, 1000, true)
            }]
          }
        }
      })

      .state('app.profile', {
        url: 'profile',
        cache: false,
        data: {private: false},
        views: {
          'header@app': {template: '<app-header current-user="auth.currentUser.username"/>'},
          'content@app': {template: '{{ auth.currentUser.username }}'}
        }
      })
  }

})()
