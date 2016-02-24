HEI Workshop
===

Table of Contents
---

1. [Dev Tools Pre-Requisites](#dev-pre-requisites)
1. [Dev Tools Installation](#dev-tools-installation)
  - 1a. [Windows](#windows)
  - 1b. [Linux](#linux)
  - 1c. [OSX](#osx)
1. [Getting Started](#getting-started)
1. [Blog App Backend](#blog-app-back-end)
1. [Blog App WebUI](#blog-app-front-end)
1. [Workshop Schedule](#workshop-schedule)


## Dev Pre-Requisites

- Java SE Development Kit 8
- Git `>= v2.x.x`
- Node.js `>= 4.x.x || >= 5.x.x`
- Maven `>= 3.2.5`
- Eclipse, NetBeans or any preferred IDE

## Dev Tools Installation

### Windows

To make tool installations a breeze, PowerShell 3.0 comes in handy accompanied
by [Scoop](http://scoop.sh/) a command-line installer for windows.


> **Installing PowerShell 3.0**
>
> For detailed instructions on installing Windows PowerShell 3.0 please head to
> this [link](https://technet.microsoft.com/en-us/library/hh847837.aspx).
> If you're running Windows 8.1 or Windows 10, you may now jump to the
> instructions below.

**Installing Scoop**

Open Windows PowerShell and type in the command below:

```bash
iex (new-object net.webclient).downloadstring('https://get.scoop.sh')

# As per Scoop's page: if you get an error you might need to
# change the execution policy (i.e. enable Powershell) with:
set-executionpolicy unrestricted -s cu

# After Scoop has installed, we'll also need the extras to access
# components that aren't available in scoop's default bucket.
scoop bucket add extras
```

**Installing Dev Pre-Requisites**

```bash
# Install JDK 8
scoop install oraclejdk

# Install Git
scoop install git

# Install Node.js
scoop install nodejs4

# Install Maven
scoop install maven
```

### Linux

If you're a `*nix`, you probably know this already or have a better method in
doing it (please create a PR!).

```bash
## Prepare repositories
$ sudo add-apt-repository ppa:webupd8team/java
$ sudo add-apt-repository ppa:git-core/ppa
$ curl -sL https://deb.nodesource.com/setup | sudo bash -
$ sudo apt-get update

## Install JDK 8
$ sudo apt-get install oracle-java8-installer

# Verify installation
$ java -version

## Install Git
$ sudo apt-get install git

## Install nodejs
# Setup for Ubuntu info from https://github.com/joyent/node/wiki/installing-node.js-via-package-manager
$ sudo apt-get install nodejs

# Install Packages
npm install -g npm bower

# Cleanup package cache (optional)
$ sudo apt-get clean

## Install Maven
$ sudo apt-get install maven
```

### OSX

```bash
# Install Homebrew
$ /usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"

# Tap into caskroom and homebrew-versions
$ brew tap caskroom/cask
$ brew tap homebrew/versions

# Install Pre-requisites
$ brew cask install java
$ brew install git
$ brew install node
$ brew install maven
```

## Getting Started

To follow through the entire workshop or just run app to your local environment,
you may clone this repository

```bash
$ git clone --depth=1 --branch=master --recurse-submodules https://github.com/toro-io/hei-workshop.git
```

## Blog App Back-end

> Please refer to the Backend [README.md](app/api/README.md)

## Blog App Front-end

> Please refer to the Frontend [README.md](app/web/README.md)

## Workshop Schedule

| TIME 	| TOPIC 	| ACTIVITIES 	|
|----------	|-------------------------------------------------------------------	|---------------------------------------	|
| 8:30 am 	| Building a RESTful service with Spring 	| 1. Demo (Jerrick Pua) 2. Hands-on 	|
| 10:00 am 	| Break 	|  	|
| 10:30 am 	| Applying Spring Security on a web application 	| 1. Demo (RJ Campos) 2. Hands-on 	|
| 11:30 am 	| Building a basic single page application using AngularJS (Part 1) 	|  Demo (Mykel Perez) 	|
| 12:01 pm 	| Lunch 	|  	|
| 1:00 pm 	| Building a basic single page application using AngularJS (Part 2) 	| 1. Demo (Mykel Perez) 2. Hands-on 	|
| 1:30 pm 	| Practical usage of CSS Preprocessors (LESS, SASS, SCSS) 	| 1. Demo (Noelito Calma) 2. Hands-on 	|
| 2:30 pm 	| Front end development Build Tasks 	| 1. Demo (Philip and Alex) 2. Hands-on 	|
| 3:30 pm 	| Break 	|  	|
| 4:00 pm 	| Twitter integration and Google Sheets integration 	| 1. Demo (Connectors Team) 2. Hands-on 	|
| 5:00 pm 	| Wrap-up	|  	|
