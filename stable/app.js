angular
  .module('app', [
    'ngRoute',
    'ngStorage'
  ])
  .config(AppConfig)

AppConfig.$inject = [
  '$routeProvider',
  '$locationProvider'
]

function AppConfig ($routeProvider, $locationProvider) {
  $locationProvider.html5Mode({
    enabled: true,
    requireBase: false
  });

  $routeProvider
    .when('/', {
      template: '<home></home>'
    })

    .when('/login', {
      template: '<login></login>'
    })

    .when('/post', {
      template: '<post></post>'
    })

    .when('/view/:id', {
      template: '<blog></blog>'
    })
}
