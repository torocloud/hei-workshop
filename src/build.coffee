
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


gulp.task 'clean', () ->
  return clean([
    "#{opts.dist}**/*.*"
    "!#{opts.dist}.gitkeep"
  ])

gulp.task 'html:resource', () ->
  min  = if util.env.minify then '.min' else ''
  proc = {}

  proc.css =
    src: "/assets/css/#{opts.pkg.name}#{min}.css"
    tpl: '<link rel="stylesheet" href="%s">'

  proc.js =
    src: "/assets/js/#{opts.pkg.name}#{min}.js"
    tpl: '<script src="%s"></script>'

  proc.button =
    src: "#{opts.pkg.name}.zip"
    tpl: "
    <a download class=\"btn btn-lg btn-success\" href=\"/download/%s\">Download</a>
    "

  return gulp.src "#{opts.src}html/**/*.html"
    .pipe replace(proc)
    .pipe gulp.dest(opts.dist)


gulp.task 'html:minify', () ->
  return gulp.src "#{opts.dist}**/*.html"
    .pipe htmlmin(collapseWhitespace: true)
    .pipe gulp.dest('.')


gulp.task 'scss:compile', () ->
  return gulp.src "#{opts.src}scss/**/*.scss"
    .pipe scss()
    .pipe rename(
      basename: "#{opts.pkg.name}"
      extname: '.css'
    )
    .pipe gulp.dest("#{opts.dist}assets/css")


gulp.task 'scss:minify', () ->
  return gulp.src "#{opts.src}scss/*.scss"
    .pipe sourcemap.init()
    .pipe cssmin()
    .pipe rename(
      basename: "#{opts.pkg.name}"
      extname: '.min.css'
    )
    .pipe sourcemap.write('.')
    .pipe gulp.dest("#{opts.dist}assets/css")


gulp.task 'coffee:compile', () ->
  return gulp.src "#{opts.src}coffee/**/*.coffee"
    .pipe sourcemap.init()
    .pipe coffee(bare: true)
    .pipe concat("#{opts.pkg.name}.js")
    .pipe gulp.dest("#{opts.dist}assets/js")
    .pipe uglify()
    .pipe rename(extname: '.min.js')
    .pipe sourcemap.write('.')
    .pipe gulp.dest("#{opts.dist}assets/js")

gulp.task 'compress', () ->
  return gulp.src [
    "#{opts.dist}/*"
    "#{opts.dist}/**"
  ]
  .pipe tar("#{opts.pkg.name}")
  .pipe gzip(extension: 'zip')
  .pipe gulp.dest("#{opts.dist}download")

gulp.task 'default', [
  'html:resource'
  'html:minify'
  'scss:compile'
  'scss:minify',
  'coffee:compile'
  'compress'
]
