+function () {

  Reveal.initialize({
    controls: false,
    progress: true,
    history: true,
    center: true,
    transition: 'convex',

    // Optional reveal.js plugins
    dependencies: [{
      src: '/controldeck/reveal.js/lib/js/classList.js',
      condition: function() {
        return !document.body.classList;
      }
    }, {
      src: '/controldeck/reveal.js/plugin/markdown/marked.js',
      condition: function() {
        return !!document.querySelector('[data-markdown]');
      }
    }, {
      src: '/controldeck/reveal.js/plugin/markdown/markdown.js',
      condition: function() {
        return !!document.querySelector('[data-markdown]');
      }
    }, {
      src: '/controldeck/reveal.js/plugin/highlight/highlight.js',
      async: true,
      callback: function() {
        hljs.initHighlightingOnLoad();
      }
    }, {
      src: '/controldeck/reveal.js/plugin/zoom-js/zoom.js',
      async: true
    }, {
      src: '/controldeck/reveal.js/plugin/notes/notes.js',
      async: true
    }]
  })

  function blurBg () {
    if (Reveal.isFirstSlide()) {
      console.log('first')
      return document
        .getElementById('body')
        .classList.add('blur')
    }

    if (!Reveal.isFirstSlide()){
      return document
        .getElementById('body')
        .classList.remove('blur')
    }
  }

  setTimeout(function () { blurBg() }, 100)

  Reveal.addEventListener('slidechanged', function (event) {
    blurBg()
  });

}();
