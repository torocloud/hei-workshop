
express = require 'express'
session = require 'express-session'
path    = require 'path'
favicon = require 'serve-favicon'

app     = express()
router  = express.Router()

router
  .route '/'
  .get (req, res, next) ->
    res.sendFile 'index.html', root: path.join(__dirname, '/../public')

router
  .route '/*'
  .get (req, res, next) ->
    res.sendFile 'index.html', root: path.join(__dirname, '/../public')


app.set 'port', process.env.PORT || 9000
app.enable 'trust proxy', 1
app.disable 'x-powered-by'

app.use '/app', express.static(path.join(__dirname, '/../public/app'))
app.use '/css', express.static(path.join(__dirname, '/../public/css'))
app.use router

app.listen app.get('port'), ->
  console.info "
    Express running on <http://0.0.0.0:#{process.env.PORT || 9000}>"
  return
