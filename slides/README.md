HEI Workshop Slides
===

### Getting Started

1. Installing Dependencies
1. Running the Slide App
1. Directory Structure
1. Adding slides

### Installing Dependencies

As long as you have node.js in your machine, you're set to go, just invoke
`npm install` to install dependencies.

If you don't have node.js installed, you may have to: `brew install node` once
done, you may follow the instruction above.

### Running the Slide App

After installing dependencies, just invoke `npm start` to get the application
running via `pm2`, the application will run in the background. Below are list
of basic commands.

```bash
# Starting the slide app
$ pm2 start index.js

# Starting the app with multiple processes with loopback on available CPUs.
# this default command runs the app in cluster mode so it can scale. This
# bulletproofs from crash specially if you're serving this slide to a bigger
# audience.
#
# where [-i 5] flag indicates instances scale up to 5 processes, with this
# command
$ pm2 start index.js -i 5

# Stopping all processes
$ pm2 stop all

# Restarting all processes
$ pm2 restart all

# Remove all instances from the list
$ pm2 delete all # fyi: this does not delete the app, only from the pm2 list

# View the list of running instances
$ pm2 list

# Monitor the current processes that are running
$ pm2 monit

# Logs
$ pm2 logs  # display all processes logs
$ pm2 flush # empty log file
```

### Directory Structure

```
slides
  ├── public                # static dir that's served by the node.js server
  |   ├── controldeck       # scripts that control the app's sockets and events
  |   |   ├── control.js    # -> responsible for emitting socket events
  |   |   └── deck.js       # -> responsible for executing slide events emitted by control.js
  |   ├── template          # template directory, your starting point in creating a slide
  |   |   └── index.html    # -> your base template
  |   ├── ctrl.html         # slides controller
  |   └── index.html        # slide app table of contents
  └── server                # this is the node.js server dir that runs the app
```

### Adding Slides

You must follow how the slides folder is structured, you may add a folder
in the `public` directory, you may name the folder with your `topic-name` and
copy `public/template/index.html` into it. You may add your presentation assets
inside the folder you created, you may have to use absolute path to call it,
like so: `/topic-name/some-file.jpg`

**Taking it to the next level**

You may want want to head [reveal.js](https://github.com/hakimel/reveal.js/blob/master/README.md)
project page to see the options on what you can do with it.

Once done, you may start the app and open [http://127.0.0.1:9001] in your
favorite browser.
