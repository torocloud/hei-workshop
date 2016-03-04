
path      = require 'path'
gulp      = require 'gulp'
util      = require 'gulp-util'
scss      = require 'gulp-sass'
concat    = require 'gulp-concat'
coffee    = require 'gulp-coffee'
htmlmin   = require 'gulp-htmlmin'
cssmin    = require 'gulp-cssnano'
replace   = require 'gulp-html-replace'
validator = require 'gulp-html-validator'
sourcemap = require 'gulp-sourcemaps'
rename    = require 'gulp-rename'
uglify    = require 'gulp-uglify'
tar       = require 'gulp-tar'
gzip      = require 'gulp-gzip'
clean     = require 'del'
opts =
  src:  'src/'
  pkg:  require path.join(__dirname, '../package.json')
  dist: path.join(__dirname, '/../dist/')
