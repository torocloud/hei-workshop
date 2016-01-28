'use strict'

fs          = require 'fs'
path        = require 'path'
gulp        = require 'gulp'
util        = require 'gulp-util'
sass        = require 'gulp-sass'
shell       = require 'shelljs'
minify      = require 'gulp-cssnano'
eslint      = require 'gulp-eslint'
imagemin    = require 'gulp-imagemin'
pngquant    = require 'imagemin-pngquant'
sourcemap   = require 'gulp-sourcemaps'
coffeelint  = require 'gulp-coffeelint'

env = {
  api:  "#{path.join(__dirname, '/../api/dist')}/blog-api-1.0.0-SNAPSHOT.jar"
  web:  "#{path.join(__dirname, 'public')}"
  dist: "#{path.join(__dirname, '/../dist')}"
}

###
# @name task: process-less
# @desc task for processing less syntax to css
###
gulp.task 'process-less', () ->


###
# @name task: process-sass
# @desc task for processing sass syntax to css
###
gulp.task 'process-sass', () ->


###
# @name task: process-html
# @desc task to process html and replace development markup
###
gulp.task 'process-html', () ->


###
# @name task: eslint
# @desc
#   task that lints our javascript files making
#   sure we comply to the team's coding standards
###
gulp.task 'eslint', () ->
  return gulp.src "#{path.join(__dirname, '/../src/javascript/*.js')}"
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
  return gulp.src "#{path.join(__dirname, '*.coffee')}"
    .pipe coffeelint()
    .pipe coffeelint.reporter('coffeelint-stylish')
    .pipe coffeelint.reporter('fail')
