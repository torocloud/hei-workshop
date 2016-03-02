(function () {

  angular
    .module('app')
    .directive('userLogin', UserLogin)

  UserLogin.$inject = [
    '$rootScope'
  ]

  function UserLogin ($rootScope) {
    let directive = {
      scope: {ngModel: '='},
      restrict: 'E',
      replace: true,
      transclude: true,
      templateUrl: '/app/views/login.html',
      controllerAs: 'login',
      bindToController: true,
      controller: UserLoginController,
      link: (scope, element, attrs) => {
        $rootScope.$on('$stateChangeStart', () => {
          element.remove()
          element.parent().empty()
          scope.$destroy()
        })
      }
    }

    return directive
  }


  UserLoginController.$inject = [
    '$rootScope',
    '$state',
    'api',
    'context'
  ]

  function UserLoginController ($rootScope, $state, api, userContext) {
    let login = this

    login.alert     = false
    login.initLogin = initLogin

    /**
     * @name initialize login
     * @desc
     *  function that sets the context of the current user via
     *  app service:userContext & redirects user to dashboard.
     *  this function also performs login form validation
     * @param {Boolean} isValid  utilizes angularjs form validation
     */
    function initLogin (isValid) {
      /**
       * @name initLogin:userLogin internal fn
       * @desc calls on to backend to log the user in and sets the
       * context of the current user
       * @param {String} redirect: a name of an available route
       * @returns {Boolean}
       */
      this.userLogin = (redirect) => {
        api.login(login.ngModel).then((data) => {
          if ( data.hasOwnProperty('token') ) {
            userContext.setCurrentUser(Object.assign({
              username: login.ngModel.username
            }, data))
            $rootScope.$emit('auth::setUser')
            return $state.go(redirect)
          }
        }, (error) =>
          this.userInvalid()
        )
        return true
      }

      /**
       * @name initLogin:userInvalid
       * @desc flags the ui for invalid fields before submission
       * @param
       * @returns {Boolean}
       */
      this.userInvalid = () => {
        login.alert = true
        return false
      }

      return isValid ? this.userLogin('app.default') : this.userInvalid()
    }
  }

})()
