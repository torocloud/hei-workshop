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
imagemin    = require 'gulp-imagemin'
pngquant    = require 'imagemin-pngquant'
sourcemap   = require 'gulp-sourcemaps'
coffeelint  = require 'gulp-coffeelint'

options.angular = [
  './bower_components/angular/angular.js'
  './bower_components/angular-route/angular-route.js'
  './bower_components/angular-cookies/angular-cookies.js'
  './bower_components/angular-resource/angular-resource.js'
]


options.app = [
  './src/javascript/app.js'
  './src/javascript/router.js'
  './src/javascript/controllers/auth.js'
  './src/javascript/directives/header.js'
  './src/javascript/directives/login.js'
  './src/javascript/factories/api.js'
  './src/javascript/services/user.js'
]


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
# @name build-angular
# @desc
###
gulp.task 'build-angular', () ->
  return gulp.src options.angular
    .pipe sourcemap.init()
    .pipe concat('angular.js')
    .pipe gulp.dest './public/app'
    .pipe rename extname: '.min.js'
    .pipe sourcemap.write('.')
    .pipe gulp.dest './public/app'


###
# @name build-app
# @desc
###
gulp.task 'build-app', () ->
  return gulp.src options.app
    .pipe sourcemap.init()
    .pipe concat('workshop.js')
    .pipe gulp.dest './public/app'
    .pipe rename extname: '.min.js'
    .pipe sourcemap.write('.')
    .pipe gulp.dest './public/app'
