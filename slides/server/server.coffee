
http    = require 'http'
util    = require 'util'
url     = require 'url'
fs      = require 'fs'
server  = require 'node-static'

svr = new server.Server './public'
app = http.createServer (req, res) ->
  pathname = url.parse(req.url).pathname
  req.addListener 'end', () ->
    svr.serve(req, res)
  .resume()
  return
app.listen process.env.PORT || 9001, () ->
  console.log "Listening to http://127.0.0.1:#{process.env.port || 9001}"

io = require('socket.io').listen app
io.sockets.on 'connection', (socket) ->

  # Message Events
  socket.on 'message', (msg) ->
    socket.broadcast.emit 'message', msg

  # KeyDown Events
  socket.on 'key down', (data) ->
    socket.broadcast.emit 'key down', data

  # KeyUp Events
  socket.on 'key up', (data) ->
    socket.broadcast.emit 'key up', data

  # Flowtime Minimap Events
  socket.on 'flowtime minimap complete', (data) ->
    socket.broadcast.emit 'flowtime minimap complete', data

  # Navigate Events
  socket.on 'navigate', (data) ->
    socket.broadcast.emit 'navigate', data

  socket.on 'disconnect', () ->
    console.log "Connection #{socket.id} terminated."
