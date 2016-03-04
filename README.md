Front-end Build Automation Demo
===

Getting Started
---

Directory Structure:

```text
demo
├── dist                # Target directory where built files will be saved.
├── src               
|   ├── coffee          # coffee source
|   ├── html            # html files
|   ├── scss            # scss files
|   ├── build.coffee    # build instructions for gulp
|   └── server.coffee   # node-static server
├── .editorconfig
├── .gitignore
├── Gulpfile.js         # Gulpfile, required to run gulp
├── index.js            # Index file where we bootstrap our server written in coffee
├── package.json        # project manifest of dev and app dependencies
├── Procfile            # Heroku Procfile
└── README.md
```

You will also find a folder called `node_modules`, this is the directory where
node.js looks the dependency modules up.
