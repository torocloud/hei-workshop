
http    = require 'http'
path    = require 'path'
url     = require 'url'
fs      = require 'fs'
server  = require 'node-static'
pkg     = require path.join(process.cwd(), 'package.json')

svr = new server.Server path.join(process.cwd(), 'dist')
app = http.createServer (request, response) ->
  pathname = url.parse(request.url).pathname
  filename = path.join(process.cwd(), 'dist', pathname)

  request.addListener 'end', () ->
    svr.serve request, response, (e, res) ->
      if request.url == "/download/#{pkg.name}.tar.zip"
        console.log request.url
  .resume()
  return

app.listen process.env.PORT || 9002, () ->
  console.log "Listening to http://127.0.0.1:#{process.env.port || 9002}"
