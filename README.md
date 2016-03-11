HEI Workshop WebUI
===

## Table of Contents

- Getting Started
- Build Tasks

### Getting Started

**Pre-requisites**

- Git
- node.js v4.x.x
- bower

**Install Application Dependencies**

Install package that will let the application run. Please see `package.json` to
see the complete list of packages.

```bash
# When the command below is invoked, it also triggers commands declared
# in package.json file, aside from npm packages, it also installs front-end
# packages via bower, builds and initializes the app for dev or production
$ npm install

# After package installation and initial app build, you may start
# the node server to get the up and running
$ npm start; open http://127.0.0.1:9000
```

### Build Tasks

**Building App Styles & Assets**

```bash
# installs bootstrap scss and bourbon mixins
$ gulp sass-install

# builds scss files to css
$ gulp sass-build
```


**Building Application Scripts**

```bash
# concat, minify and writes source maps of angular
# modules to the application directory
$ gulp app-angular

# concat, minify and writes source maps of application
# scripts to the application directory.
$ gulp app-build
```

**Development Mode**

Watch for file changes and automatically build source to the app directory

```bash
# Watch and build sass styles
$ gulp develop --sass

# Watch and build app js
$ gulp develop --app
```
