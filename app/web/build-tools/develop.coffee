'use strict'

options = {}

fs          = require 'fs'
path        = require 'path'
gulp        = require 'gulp'
util        = require 'gulp-util'
sass        = require 'gulp-sass'
shell       = require 'shelljs'
babel       = require 'gulp-babel'
concat      = require 'gulp-concat'
minify      = require 'gulp-cssnano'
uglify      = require 'gulp-uglify'
eslint      = require 'gulp-eslint'
rename      = require 'gulp-rename'
htmlmin     = require 'gulp-minify-html'
imagemin    = require 'gulp-imagemin'
pngquant    = require 'imagemin-pngquant'
sourcemap   = require 'gulp-sourcemaps'
coffeelint  = require 'gulp-coffeelint'
replace     = require 'gulp-html-replace'

pkg = require path.join(__dirname, '../package.json')


options.angular = [
  './node_modules/angular/angular.js'
  './node_modules/angular-route/angular-route.js'
  './node_modules/angular-sanitize/angular-sanitize.js'
  './node_modules/ngstorage/ngStorage.js'
]


options.app = [
  './src/javascript/app.js'
  './src/javascript/router.js'
  './src/javascript/directives.js'
  './src/javascript/controllers.js'
  './src/javascript/factories.js'
]

options.sass = {
  outputStyle: 'expanded'
  includePaths: [
    './src/sass'
  ]
}

options.babel = {
  presets: ['es2015']
}


###
# @name sass-build
# @desc
###
gulp.task 'sass:build', () ->
  return gulp.src "./src/sass/workshop.scss"
    .pipe sass(options.sass)
    .pipe rename(basename: pkg.name)
    .pipe gulp.dest('./public/css')
    .pipe sourcemap.init()
    .pipe rename (extname: '.min.css')
    .pipe sourcemap.write('.')
    .pipe gulp.dest('./public/css')

###
# @name task: eslint
# @desc
#   task that lints our javascript files making
#   sure we comply to the team's coding standards
###
gulp.task 'eslint', () ->
  return gulp.src ['./src/javascript/**/*.js']
    .pipe eslint()
    .pipe eslint.format()
    .pipe eslint.failAfterError()


###
# @name build-angular
# @desc
###
gulp.task 'app:angular', () ->
  return gulp.src options.angular
    .pipe sourcemap.init()
    .pipe concat('angular.js')
    .pipe gulp.dest './public/app'
    .pipe rename extname: '.min.js'
    .pipe sourcemap.write('.')
    .pipe gulp.dest './public/app'


###
# @name build-app
# @desc task that transpiles src es6 syntax js to es5 via babel
###
gulp.task 'app:build', () ->
  min  = if util.env.minify then '.min' else ''
  gulp.src [
    'src/javascript/views/**/*.html'
    '!src/javascript/views/index.html'
  ]
  .pipe htmlmin(empty: true)
  .pipe gulp.dest('./public/app/views')

  return gulp.src options.app
    .pipe sourcemap.init()
    .pipe babel(options.babel)
    .pipe concat("#{pkg.name}#{min}.js")
    .pipe gulp.dest('./public/app')
    .pipe rename(extname: '.min.js')
    .pipe sourcemap.write('.')
    .pipe gulp.dest('./public/app')

###
# @name build-html
###
gulp.task 'html:resource', () ->
  min  = if util.env.minify then '.min' else ''
  prod = if util.env.local then 'http://127.0.0.1:8080/api/' else 'https://hei-workshop.herokuapp.com/api/'
  proc = {}

  proc.api =
    src: "#{prod}"
    tpl: "<body data-endpoint=\"%s\">"

  proc.css =
    src: [
      [
        "https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.2/css/bootstrap.min.css"
        "/css/#{pkg.name}#{min}.css"
      ]
    ]
    tpl: "
      <link rel=\"stylesheet\" href=\"%s\">\n
        <link rel=\"stylesheet\" href=\"%s\">"

  proc.js  =
    src: [
      [
        "https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"
        "https://cdn.jsdelivr.net/tether/1.2.0/tether.min.js"
        "https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.2/js/bootstrap.min.js"
        "https://cdn.ckeditor.com/4.5.7/standard/ckeditor.js"
        "/app/angular#{min}.js"
        "/app/#{pkg.name}#{min}.js"
      ]
    ]
    tpl: "
      <script src=\"%s\"></script>\n
        <script src=\"%s\"></script>\n
        <script src=\"%s\"></script>\n
        <script src=\"%s\"></script>\n
        <script src=\"%s\"></script>\n
        <script src=\"%s\"></script>"

  return gulp.src "./src/javascript/views/index.html"
    .pipe replace(proc)
    .pipe gulp.dest('./public/')

###
# @name develop
# @desc task that watches for file changes on specific tasks
# @param --app or --sass
###
gulp.task 'develop', () ->
  if util.env.app
    return gulp.watch [
      './src/javascript/**/*.js'
      './src/javascript/**/*.html'
    ], [
      'app-angular'
      'app-build'
    ]

  if util.env.sass
    return gulp.watch [
      './src/sass/**/*.scss'
    ], ['sass-build']


gulp.task 'default', [
  'app:build'
  'app:angular'
  'sass:build'
  'html:resource'
]
