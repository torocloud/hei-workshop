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
