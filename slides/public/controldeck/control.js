+function () {
  'use strict';

  var socket = io.connect()

  socket.on('connect', function () {
    console.info('Connected: Listening for Events.')
  })

  socket.on('disconnect', function () {
    console.info('Disconnected: Stopped Listening for Events.')
  })

  $(document).on('keydown', function (e) {
    $('[data-key='+ e.keyCode +']').addClass('active')
    socket.emit('key down', {
      keyCode: e.keyCode,
      shiftKey: e.shiftKey,
      altKey: e.altKey,
      ctrlKey: e.ctrlKey,
      metaKey: e.metaKey
    })
  })

  $(document).on('keyup', function (e) {
    $('[data-key='+ e.keyCode +']').removeClass('active')
  })

  $('body').on('click', function(e) {
    e.preventDefault();
      if ($(this).attr('data-key')) {
        socket.send($(this).attr('data-key'));
      }
      else if ($(this).attr('data-goto')) {
        socket.send('goto:'+$(this).attr('data-goto'));
      }
      else if ($(this).attr('data-command')) {
    		socket.send($(this).attr('data-command'));
      }
  });

    socket.on('flowtime minimap complete', function(data){
        var minimap = $('<div class="minimap ft-default-progress"></div>');
        $('body').append(minimap);
        minimap.append(data.dom);
        var ftThumbs = document.querySelectorAll('.ft-page-thumb');
        $('body').on(press,'.ft-page-thumb', function(e) {
            e.preventDefault();
            for (var i = 0; i < ftThumbs.length; i++) {
                ftThumbs[i].classList.remove('actual');
            }
            e.target.classList.add('actual');
            var s = e.target.getAttribute('data-section').replace('__', '');
            var p = e.target.getAttribute('data-page').replace('__', '');
            socket.emit("navigate", { section: Number(s), page: Number(p) });
            console.log("e.target", s, p);
        });

        socket.on('navigate', function(data){
            for (var i = 0; i < ftThumbs.length; i++) {
                ftThumbs[i].classList.remove('actual');
            }
            var actualThumb = document.querySelector('.ft-page-thumb[data-section=__' + data.section + '][data-page=__' + data.page + ']');
            actualThumb.classList.add('actual');
        });
    });

}();
