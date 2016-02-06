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

options.angular = [
  './bower_components/angular/angular.js'
  './bower_components/angular-cookies/angular-cookies.js'
  './bower_components/angular-resource/angular-resource.js'
  './bower_components/angular-ui-router/release/angular-ui-router.js'
]


options.app = [
  './src/javascript/app.js'
  './src/javascript/router.js'
  './src/javascript/controllers/auth.js'
  './src/javascript/directives/blogs.js'
  './src/javascript/directives/post-blog.js'
  './src/javascript/directives/get-blog.js'
  './src/javascript/directives/header.js'
  './src/javascript/directives/login.js'
  './src/javascript/factories/api.js'
  './src/javascript/services/user.js'
]

options.sass = {
  outputStyle: 'expanded'
  includePaths: [
    './src/sass/bootstrap'
    './src/sass/bourbon'
  ]
}

options.babel = {
  presets: ['es2015']
}

###
# @name sass-install
# @desc
###
gulp.task 'sass-install', () ->

  bootstrap = () ->
    return gulp.src './node_modules/bootstrap/scss/**/*.scss'
      .pipe gulp.dest './src/sass/bootstrap'

  bourbon = () ->
    return gulp.src './node_modules/bourbon/app/assets/stylesheets/**/*.scss'
      .pipe gulp.dest './src/sass/bourbon'

  if util.env.bootstrap
    bootstrap()

  if util.env.bourbon
    bourbon()

  if util.env._.length <= 1
    bootstrap()
    bourbon()


###
# @name sass-build
# @desc
###
gulp.task 'sass-build', () ->
  return gulp.src './src/sass/workshop.scss'
    .pipe sass(options.sass)
    .pipe gulp.dest './public/css'
    .pipe sourcemap.init()
    .pipe rename extname: '.min.css'
    .pipe sourcemap.write('.')
    .pipe gulp.dest './public/css'

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
# @name task: coffee-lint
# @desc
#   task that lints our coffeescript files making
#  sure we comply to the team's coding standards
###
gulp.task 'coffeelint', () ->
  return gulp.src './build/**/*.coffee'
    .pipe coffeelint()
    .pipe coffeelint.reporter('coffeelint-stylish')
    .pipe coffeelint.reporter('fail')


###
# @name build-angular
# @desc
###
gulp.task 'app-angular', () ->
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
gulp.task 'app-build', () ->
  gulp.src './src/javascript/views/**/*.html'
    .pipe htmlmin(empty: true)
    .pipe gulp.dest('./public/app/views')

  return gulp.src options.app
    .pipe sourcemap.init()
    .pipe babel(options.babel)
    .pipe concat('workshop.js')
    .pipe gulp.dest('./public/app')
    .pipe rename(extname: '.min.js')
    .pipe sourcemap.write('.')
    .pipe gulp.dest('./public/app')

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
  'sass-install',
  'sass-build',
  'coffeelint',
  'eslint',
  'app-build',
  'app-angular'
]
