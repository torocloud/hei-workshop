(function () {
  'use strict';

  angular
    .module('app')
    .service('context', Context)

  Context.$inject = ['$cookies']

  function Context ($cookies) {

    // Current Context Setter
    this.setCurrentUser = (userdata) => {
      this.currentUser = userdata
    }

    // Current Context Getter
    this.getCurrentUser = () => {
      return this.currentUser ?
        this.currentUser : null
    }

    return this
  }

})();
