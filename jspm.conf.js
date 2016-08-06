System.config({
  defaultJSExtensions: true,
  transpiler: false,
  paths: {
    "github:*": "jspm_packages/github/*",
    "npm:*": "jspm_packages/npm/*"
  },

  map: {
    "@angular/common": "npm:@angular/common@2.0.0-rc.4",
    "@angular/compiler": "npm:@angular/compiler@2.0.0-rc.4",
    "@angular/core": "npm:@angular/core@2.0.0-rc.4",
    "@angular/http": "npm:@angular/http@2.0.0-rc.4",
    "@angular/platform-browser": "npm:@angular/platform-browser@2.0.0-rc.4",
    "@angular/platform-browser-dynamic": "npm:@angular/platform-browser-dynamic@2.0.0-rc.4",
    "@angular/router": "npm:@angular/router@3.0.0-beta.2",
    "@angular/testing": "npm:@angular/testing@0.0.0-0",
    "babel": "npm:babel-core@6.7.4",
    "babel-runtime": "npm:babel-runtime@6.6.1",
    "codemirror": "npm:codemirror@5.17.0",
    "core-js": "npm:core-js@2.4.0",
    "d3": "npm:d3@4.2.0",
    "dateformat": "npm:dateformat@1.0.12",
    "es6-shim": "npm:es6-shim@0.35.1",
    "normalize.css": "github:necolas/normalize.css@3.0.3",
    "process": "github:jspm/nodelibs-process@0.1.2",
    "reflect-metadata": "npm:reflect-metadata@0.1.3",
    "require.js": "npm:require.js@1.0.0",
    "rxjs": "npm:rxjs@5.0.0-beta.6",
    "github:jspm/nodelibs-assert@0.1.0": {
      "assert": "npm:assert@1.4.1"
    },
    "github:jspm/nodelibs-buffer@0.1.0": {
      "buffer": "npm:buffer@3.6.0"
    },
    "github:jspm/nodelibs-constants@0.1.0": {
      "constants-browserify": "npm:constants-browserify@0.0.1"
    },
    "github:jspm/nodelibs-events@0.1.1": {
      "events": "npm:events@1.0.2"
    },
    "github:jspm/nodelibs-http@1.7.1": {
      "Base64": "npm:Base64@0.2.1",
      "events": "github:jspm/nodelibs-events@0.1.1",
      "inherits": "npm:inherits@2.0.1",
      "stream": "github:jspm/nodelibs-stream@0.1.0",
      "url": "github:jspm/nodelibs-location@0.1.0",
      "util": "github:jspm/nodelibs-util@0.1.0"
    },
    "github:jspm/nodelibs-https@0.1.0": {
      "https-browserify": "npm:https-browserify@0.0.0"
    },
    "github:jspm/nodelibs-path@0.1.0": {
      "path-browserify": "npm:path-browserify@0.0.0"
    },
    "github:jspm/nodelibs-process@0.1.2": {
      "process": "npm:process@0.11.7"
    },
    "github:jspm/nodelibs-querystring@0.1.0": {
      "querystring": "npm:querystring@0.2.0"
    },
    "github:jspm/nodelibs-stream@0.1.0": {
      "stream-browserify": "npm:stream-browserify@1.0.0"
    },
    "github:jspm/nodelibs-url@0.1.0": {
      "url": "npm:location@0.10.3"
    },
    "github:jspm/nodelibs-util@0.1.0": {
      "util": "npm:util@0.10.3"
    },
    "github:jspm/nodelibs-vm@0.1.0": {
      "vm-browserify": "npm:vm-browserify@0.0.4"
    },
    "github:necolas/normalize.css@3.0.3": {
      "css": "github:systemjs/plugin-css@0.1.20"
    },
    "npm:@angular/common@0.0.0-0": {
      "@angular/core": "npm:@angular/core@0.0.0-0",
      "process": "github:jspm/nodelibs-process@0.1.2"
    },
    "npm:@angular/common@2.0.0-rc.4": {
      "@angular/core": "npm:@angular/core@2.0.0-rc.4",
      "process": "github:jspm/nodelibs-process@0.1.2"
    },
    "npm:@angular/compiler@0.0.0-0": {
      "@angular/core": "npm:@angular/core@0.0.0-0",
      "process": "github:jspm/nodelibs-process@0.1.2"
    },
    "npm:@angular/compiler@2.0.0-rc.4": {
      "@angular/core": "npm:@angular/core@2.0.0-rc.4",
      "process": "github:jspm/nodelibs-process@0.1.2"
    },
    "npm:@angular/core@0.0.0-0": {
      "process": "github:jspm/nodelibs-process@0.1.2",
      "rxjs": "npm:rxjs@5.0.0-beta.2",
      "zone.js": "npm:zone.js@0.6.12"
    },
    "npm:@angular/core@2.0.0-rc.4": {
      "process": "github:jspm/nodelibs-process@0.1.2",
      "rxjs": "npm:rxjs@5.0.0-beta.6",
      "zone.js": "npm:zone.js@0.6.12"
    },
    "npm:@angular/http@2.0.0-rc.4": {
      "@angular/core": "npm:@angular/core@2.0.0-rc.4",
      "@angular/platform-browser": "npm:@angular/platform-browser@2.0.0-rc.4",
      "rxjs": "npm:rxjs@5.0.0-beta.6"
    },
    "npm:@angular/platform-browser-dynamic@2.0.0-rc.4": {
      "@angular/common": "npm:@angular/common@2.0.0-rc.4",
      "@angular/compiler": "npm:@angular/compiler@2.0.0-rc.4",
      "@angular/core": "npm:@angular/core@2.0.0-rc.4",
      "@angular/platform-browser": "npm:@angular/platform-browser@2.0.0-rc.4",
      "process": "github:jspm/nodelibs-process@0.1.2"
    },
    "npm:@angular/platform-browser@0.0.0-0": {
      "@angular/common": "npm:@angular/common@0.0.0-0",
      "@angular/compiler": "npm:@angular/compiler@0.0.0-0",
      "@angular/core": "npm:@angular/core@0.0.0-0",
      "process": "github:jspm/nodelibs-process@0.1.2",
      "rxjs": "npm:rxjs@5.0.0-beta.2",
      "zone.js": "npm:zone.js@0.6.12"
    },
    "npm:@angular/platform-browser@2.0.0-rc.4": {
      "@angular/common": "npm:@angular/common@2.0.0-rc.4",
      "@angular/compiler": "npm:@angular/compiler@2.0.0-rc.4",
      "@angular/core": "npm:@angular/core@2.0.0-rc.4",
      "process": "github:jspm/nodelibs-process@0.1.2"
    },
    "npm:@angular/router@3.0.0-beta.2": {
      "@angular/common": "npm:@angular/common@2.0.0-rc.4",
      "@angular/compiler": "npm:@angular/compiler@2.0.0-rc.4",
      "@angular/core": "npm:@angular/core@2.0.0-rc.4",
      "@angular/platform-browser": "npm:@angular/platform-browser@2.0.0-rc.4",
      "@angular/platform-browser-dynamic": "npm:@angular/platform-browser-dynamic@2.0.0-rc.4",
      "process": "github:jspm/nodelibs-process@0.1.2",
      "rxjs": "npm:rxjs@5.0.0-beta.6"
    },
    "npm:@angular/testing@0.0.0-0": {
      "@angular/common": "npm:@angular/common@0.0.0-0",
      "@angular/compiler": "npm:@angular/compiler@0.0.0-0",
      "@angular/core": "npm:@angular/core@0.0.0-0",
      "@angular/platform-browser": "npm:@angular/platform-browser@0.0.0-0",
      "zone.js": "npm:zone.js@0.6.12"
    },
    "npm:amdefine@1.0.0": {
      "fs": "github:jspm/nodelibs-fs@0.1.2",
      "module": "github:jspm/nodelibs-module@0.1.0",
      "path": "github:jspm/nodelibs-path@0.1.0",
      "process": "github:jspm/nodelibs-process@0.1.2"
    },
    "npm:assert@1.4.1": {
      "assert": "github:jspm/nodelibs-assert@0.1.0",
      "buffer": "github:jspm/nodelibs-buffer@0.1.0",
      "process": "github:jspm/nodelibs-process@0.1.2",
      "util": "npm:util@0.10.3"
    },
    "npm:babel-code-frame@6.7.4": {
      "babel-runtime": "npm:babel-runtime@5.8.38",
      "chalk": "npm:chalk@1.1.3",
      "esutils": "npm:esutils@2.0.2",
      "js-tokens": "npm:js-tokens@1.0.3",
      "repeating": "npm:repeating@1.1.3"
    },
    "npm:babel-core@6.7.4": {
      "babel-code-frame": "npm:babel-code-frame@6.7.4",
      "babel-generator": "npm:babel-generator@6.7.2",
      "babel-helpers": "npm:babel-helpers@6.6.0",
      "babel-messages": "npm:babel-messages@6.7.2",
      "babel-register": "npm:babel-register@6.7.2",
      "babel-runtime": "npm:babel-runtime@5.8.38",
      "babel-template": "npm:babel-template@6.7.0",
      "babel-traverse": "npm:babel-traverse@6.7.4",
      "babel-types": "npm:babel-types@6.7.2",
      "babylon": "npm:babylon@6.7.0",
      "convert-source-map": "npm:convert-source-map@1.2.0",
      "debug": "npm:debug@2.2.0",
      "fs": "github:jspm/nodelibs-fs@0.1.2",
      "json5": "npm:json5@0.4.0",
      "lodash": "npm:lodash@3.10.1",
      "minimatch": "npm:minimatch@2.0.10",
      "module": "github:jspm/nodelibs-module@0.1.0",
      "path": "github:jspm/nodelibs-path@0.1.0",
      "path-exists": "npm:path-exists@1.0.0",
      "path-is-absolute": "npm:path-is-absolute@1.0.0",
      "private": "npm:private@0.1.6",
      "process": "github:jspm/nodelibs-process@0.1.2",
      "shebang-regex": "npm:shebang-regex@1.0.0",
      "slash": "npm:slash@1.0.0",
      "source-map": "npm:source-map@0.5.3",
      "systemjs-json": "github:systemjs/plugin-json@0.1.2",
      "util": "github:jspm/nodelibs-util@0.1.0"
    },
    "npm:babel-generator@6.7.2": {
      "babel-messages": "npm:babel-messages@6.7.2",
      "babel-runtime": "npm:babel-runtime@5.8.38",
      "babel-types": "npm:babel-types@6.7.2",
      "buffer": "github:jspm/nodelibs-buffer@0.1.0",
      "detect-indent": "npm:detect-indent@3.0.1",
      "is-integer": "npm:is-integer@1.0.6",
      "lodash": "npm:lodash@3.10.1",
      "repeating": "npm:repeating@1.1.3",
      "source-map": "npm:source-map@0.5.3",
      "trim-right": "npm:trim-right@1.0.1"
    },
    "npm:babel-helpers@6.6.0": {
      "babel-runtime": "npm:babel-runtime@5.8.38",
      "babel-template": "npm:babel-template@6.7.0"
    },
    "npm:babel-messages@6.7.2": {
      "babel-runtime": "npm:babel-runtime@5.8.38",
      "util": "github:jspm/nodelibs-util@0.1.0"
    },
    "npm:babel-register@6.7.2": {
      "babel-core": "npm:babel-core@6.7.4",
      "babel-runtime": "npm:babel-runtime@5.8.38",
      "core-js": "npm:core-js@2.4.1",
      "fs": "github:jspm/nodelibs-fs@0.1.2",
      "home-or-tmp": "npm:home-or-tmp@1.0.0",
      "lodash": "npm:lodash@3.10.1",
      "mkdirp": "npm:mkdirp@0.5.1",
      "path": "github:jspm/nodelibs-path@0.1.0",
      "path-exists": "npm:path-exists@1.0.0",
      "process": "github:jspm/nodelibs-process@0.1.2",
      "source-map-support": "npm:source-map-support@0.2.10"
    },
    "npm:babel-runtime@5.8.38": {
      "process": "github:jspm/nodelibs-process@0.1.2"
    },
    "npm:babel-runtime@6.6.1": {
      "core-js": "npm:core-js@2.4.1",
      "process": "github:jspm/nodelibs-process@0.1.2"
    },
    "npm:babel-template@6.7.0": {
      "babel-runtime": "npm:babel-runtime@5.8.38",
      "babel-traverse": "npm:babel-traverse@6.7.4",
      "babel-types": "npm:babel-types@6.7.2",
      "babylon": "npm:babylon@6.7.0",
      "lodash": "npm:lodash@3.10.1"
    },
    "npm:babel-traverse@6.7.4": {
      "babel-code-frame": "npm:babel-code-frame@6.7.4",
      "babel-messages": "npm:babel-messages@6.7.2",
      "babel-runtime": "npm:babel-runtime@5.8.38",
      "babel-types": "npm:babel-types@6.7.2",
      "babylon": "npm:babylon@6.7.0",
      "debug": "npm:debug@2.2.0",
      "globals": "npm:globals@8.18.0",
      "invariant": "npm:invariant@2.2.1",
      "lodash": "npm:lodash@3.10.1",
      "process": "github:jspm/nodelibs-process@0.1.2",
      "repeating": "npm:repeating@1.1.3"
    },
    "npm:babel-types@6.7.2": {
      "babel-runtime": "npm:babel-runtime@5.8.38",
      "babel-traverse": "npm:babel-traverse@6.7.4",
      "esutils": "npm:esutils@2.0.2",
      "lodash": "npm:lodash@3.10.1",
      "to-fast-properties": "npm:to-fast-properties@1.0.2"
    },
    "npm:babylon@6.7.0": {
      "babel-runtime": "npm:babel-runtime@5.8.38",
      "fs": "github:jspm/nodelibs-fs@0.1.2",
      "process": "github:jspm/nodelibs-process@0.1.2"
    },
    "npm:brace-expansion@1.1.3": {
      "balanced-match": "npm:balanced-match@0.3.0",
      "concat-map": "npm:concat-map@0.0.1"
    },
    "npm:buffer@3.6.0": {
      "base64-js": "npm:base64-js@0.0.8",
      "child_process": "github:jspm/nodelibs-child_process@0.1.0",
      "fs": "github:jspm/nodelibs-fs@0.1.2",
      "ieee754": "npm:ieee754@1.1.6",
      "isarray": "npm:isarray@1.0.0",
      "process": "github:jspm/nodelibs-process@0.1.2"
    },
    "npm:builtin-modules@1.1.1": {
      "process": "github:jspm/nodelibs-process@0.1.2",
      "systemjs-json": "github:systemjs/plugin-json@0.1.2"
    },
    "npm:camelcase-keys@2.1.0": {
      "camelcase": "npm:camelcase@2.1.1",
      "map-obj": "npm:map-obj@1.0.1"
    },
    "npm:chalk@1.1.3": {
      "ansi-styles": "npm:ansi-styles@2.2.1",
      "escape-string-regexp": "npm:escape-string-regexp@1.0.5",
      "has-ansi": "npm:has-ansi@2.0.0",
      "process": "github:jspm/nodelibs-process@0.1.2",
      "strip-ansi": "npm:strip-ansi@3.0.1",
      "supports-color": "npm:supports-color@2.0.0"
    },
    "npm:codemirror@5.17.0": {
      "buffer": "github:jspm/nodelibs-buffer@0.1.0",
      "child_process": "github:jspm/nodelibs-child_process@0.1.0",
      "https": "github:jspm/nodelibs-https@0.1.0",
      "process": "github:jspm/nodelibs-process@0.1.2"
    },
    "npm:constants-browserify@0.0.1": {
      "systemjs-json": "github:systemjs/plugin-json@0.1.2"
    },
    "npm:convert-source-map@1.2.0": {
      "buffer": "github:jspm/nodelibs-buffer@0.1.0",
      "fs": "github:jspm/nodelibs-fs@0.1.2",
      "path": "github:jspm/nodelibs-path@0.1.0"
    },
    "npm:core-js@2.4.0": {
      "fs": "github:jspm/nodelibs-fs@0.1.2",
      "path": "github:jspm/nodelibs-path@0.1.0",
      "process": "github:jspm/nodelibs-process@0.1.2",
      "systemjs-json": "github:systemjs/plugin-json@0.1.2"
    },
    "npm:core-js@2.4.1": {
      "fs": "github:jspm/nodelibs-fs@0.1.2",
      "path": "github:jspm/nodelibs-path@0.1.0",
      "process": "github:jspm/nodelibs-process@0.1.2",
      "systemjs-json": "github:systemjs/plugin-json@0.1.2"
    },
    "npm:core-util-is@1.0.2": {
      "buffer": "github:jspm/nodelibs-buffer@0.1.0"
    },
    "npm:currently-unhandled@0.4.1": {
      "array-find-index": "npm:array-find-index@1.0.1",
      "process": "github:jspm/nodelibs-process@0.1.2"
    },
    "npm:d3-brush@1.0.1": {
      "d3-dispatch": "npm:d3-dispatch@1.0.0",
      "d3-drag": "npm:d3-drag@1.0.0",
      "d3-interpolate": "npm:d3-interpolate@1.1.0",
      "d3-selection": "npm:d3-selection@1.0.1",
      "d3-transition": "npm:d3-transition@1.0.0"
    },
    "npm:d3-chord@1.0.1": {
      "d3-array": "npm:d3-array@1.0.0",
      "d3-path": "npm:d3-path@1.0.0"
    },
    "npm:d3-drag@1.0.0": {
      "d3-dispatch": "npm:d3-dispatch@1.0.0",
      "d3-selection": "npm:d3-selection@1.0.1"
    },
    "npm:d3-dsv@1.0.0": {
      "rw": "npm:rw@1.3.2"
    },
    "npm:d3-force@1.0.1": {
      "d3-collection": "npm:d3-collection@1.0.0",
      "d3-dispatch": "npm:d3-dispatch@1.0.0",
      "d3-quadtree": "npm:d3-quadtree@1.0.0",
      "d3-timer": "npm:d3-timer@1.0.1",
      "process": "github:jspm/nodelibs-process@0.1.2"
    },
    "npm:d3-geo@1.2.0": {
      "buffer": "github:jspm/nodelibs-buffer@0.1.0",
      "d3-array": "npm:d3-array@1.0.0"
    },
    "npm:d3-interpolate@1.1.0": {
      "d3-color": "npm:d3-color@1.0.0"
    },
    "npm:d3-request@1.0.1": {
      "d3-collection": "npm:d3-collection@1.0.0",
      "d3-dispatch": "npm:d3-dispatch@1.0.0",
      "d3-dsv": "npm:d3-dsv@1.0.0",
      "xmlhttprequest": "npm:xmlhttprequest@1.8.0"
    },
    "npm:d3-scale@1.0.2": {
      "d3-array": "npm:d3-array@1.0.0",
      "d3-collection": "npm:d3-collection@1.0.0",
      "d3-color": "npm:d3-color@1.0.0",
      "d3-format": "npm:d3-format@1.0.1",
      "d3-interpolate": "npm:d3-interpolate@1.1.0",
      "d3-time": "npm:d3-time@1.0.1",
      "d3-time-format": "npm:d3-time-format@2.0.1"
    },
    "npm:d3-shape@1.0.1": {
      "d3-path": "npm:d3-path@1.0.0"
    },
    "npm:d3-time-format@2.0.1": {
      "d3-time": "npm:d3-time@1.0.1"
    },
    "npm:d3-transition@1.0.0": {
      "d3-color": "npm:d3-color@1.0.0",
      "d3-dispatch": "npm:d3-dispatch@1.0.0",
      "d3-ease": "npm:d3-ease@1.0.0",
      "d3-interpolate": "npm:d3-interpolate@1.1.0",
      "d3-selection": "npm:d3-selection@1.0.1",
      "d3-timer": "npm:d3-timer@1.0.1"
    },
    "npm:d3-zoom@1.0.2": {
      "d3-dispatch": "npm:d3-dispatch@1.0.0",
      "d3-drag": "npm:d3-drag@1.0.0",
      "d3-interpolate": "npm:d3-interpolate@1.1.0",
      "d3-selection": "npm:d3-selection@1.0.1",
      "d3-transition": "npm:d3-transition@1.0.0"
    },
    "npm:d3@4.2.0": {
      "buffer": "github:jspm/nodelibs-buffer@0.1.0",
      "d3-array": "npm:d3-array@1.0.0",
      "d3-axis": "npm:d3-axis@1.0.1",
      "d3-brush": "npm:d3-brush@1.0.1",
      "d3-chord": "npm:d3-chord@1.0.1",
      "d3-collection": "npm:d3-collection@1.0.0",
      "d3-color": "npm:d3-color@1.0.0",
      "d3-dispatch": "npm:d3-dispatch@1.0.0",
      "d3-drag": "npm:d3-drag@1.0.0",
      "d3-dsv": "npm:d3-dsv@1.0.0",
      "d3-ease": "npm:d3-ease@1.0.0",
      "d3-force": "npm:d3-force@1.0.1",
      "d3-format": "npm:d3-format@1.0.1",
      "d3-geo": "npm:d3-geo@1.2.0",
      "d3-hierarchy": "npm:d3-hierarchy@1.0.1",
      "d3-interpolate": "npm:d3-interpolate@1.1.0",
      "d3-path": "npm:d3-path@1.0.0",
      "d3-polygon": "npm:d3-polygon@1.0.0",
      "d3-quadtree": "npm:d3-quadtree@1.0.0",
      "d3-queue": "npm:d3-queue@3.0.1",
      "d3-random": "npm:d3-random@1.0.0",
      "d3-request": "npm:d3-request@1.0.1",
      "d3-scale": "npm:d3-scale@1.0.2",
      "d3-selection": "npm:d3-selection@1.0.1",
      "d3-shape": "npm:d3-shape@1.0.1",
      "d3-time": "npm:d3-time@1.0.1",
      "d3-time-format": "npm:d3-time-format@2.0.1",
      "d3-timer": "npm:d3-timer@1.0.1",
      "d3-transition": "npm:d3-transition@1.0.0",
      "d3-voronoi": "npm:d3-voronoi@1.0.1",
      "d3-zoom": "npm:d3-zoom@1.0.2",
      "process": "github:jspm/nodelibs-process@0.1.2"
    },
    "npm:dateformat@1.0.12": {
      "get-stdin": "npm:get-stdin@4.0.1",
      "meow": "npm:meow@3.7.0"
    },
    "npm:debug@2.2.0": {
      "ms": "npm:ms@0.7.1"
    },
    "npm:detect-indent@3.0.1": {
      "fs": "github:jspm/nodelibs-fs@0.1.2",
      "get-stdin": "npm:get-stdin@4.0.1",
      "minimist": "npm:minimist@1.2.0",
      "process": "github:jspm/nodelibs-process@0.1.2",
      "repeating": "npm:repeating@1.1.3",
      "systemjs-json": "github:systemjs/plugin-json@0.1.2"
    },
    "npm:error-ex@1.3.0": {
      "is-arrayish": "npm:is-arrayish@0.2.1",
      "util": "github:jspm/nodelibs-util@0.1.0"
    },
    "npm:es6-shim@0.35.1": {
      "process": "github:jspm/nodelibs-process@0.1.2"
    },
    "npm:find-up@1.1.2": {
      "path": "github:jspm/nodelibs-path@0.1.0",
      "path-exists": "npm:path-exists@2.1.0",
      "pinkie-promise": "npm:pinkie-promise@2.0.1"
    },
    "npm:get-stdin@4.0.1": {
      "buffer": "github:jspm/nodelibs-buffer@0.1.0",
      "process": "github:jspm/nodelibs-process@0.1.2"
    },
    "npm:globals@8.18.0": {
      "systemjs-json": "github:systemjs/plugin-json@0.1.2"
    },
    "npm:graceful-fs@4.1.5": {
      "assert": "github:jspm/nodelibs-assert@0.1.0",
      "constants": "github:jspm/nodelibs-constants@0.1.0",
      "fs": "github:jspm/nodelibs-fs@0.1.2",
      "process": "github:jspm/nodelibs-process@0.1.2",
      "stream": "github:jspm/nodelibs-stream@0.1.0",
      "util": "github:jspm/nodelibs-util@0.1.0"
    },
    "npm:has-ansi@2.0.0": {
      "ansi-regex": "npm:ansi-regex@2.0.0"
    },
    "npm:home-or-tmp@1.0.0": {
      "os-tmpdir": "npm:os-tmpdir@1.0.1",
      "user-home": "npm:user-home@1.1.1"
    },
    "npm:hosted-git-info@2.1.5": {
      "url": "github:jspm/nodelibs-location@0.1.0"
    },
    "npm:https-browserify@0.0.0": {
      "http": "github:jspm/nodelibs-http@1.7.1"
    },
    "npm:indent-string@2.1.0": {
      "repeating": "npm:repeating@2.0.1"
    },
    "npm:inherits@2.0.1": {
      "util": "github:jspm/nodelibs-util@0.1.0"
    },
    "npm:invariant@2.2.1": {
      "loose-envify": "npm:loose-envify@1.1.0",
      "process": "github:jspm/nodelibs-process@0.1.2"
    },
    "npm:is-builtin-module@1.0.0": {
      "builtin-modules": "npm:builtin-modules@1.1.1"
    },
    "npm:is-finite@1.0.1": {
      "number-is-nan": "npm:number-is-nan@1.0.0"
    },
    "npm:is-integer@1.0.6": {
      "is-finite": "npm:is-finite@1.0.1"
    },
    "npm:json5@0.4.0": {
      "fs": "github:jspm/nodelibs-fs@0.1.2",
      "path": "github:jspm/nodelibs-path@0.1.0",
      "process": "github:jspm/nodelibs-process@0.1.2",
      "systemjs-json": "github:systemjs/plugin-json@0.1.2"
    },
    "npm:load-json-file@1.1.0": {
      "graceful-fs": "npm:graceful-fs@4.1.5",
      "parse-json": "npm:parse-json@2.2.0",
      "path": "github:jspm/nodelibs-path@0.1.0",
      "pify": "npm:pify@2.3.0",
      "pinkie-promise": "npm:pinkie-promise@2.0.1",
      "process": "github:jspm/nodelibs-process@0.1.2",
      "strip-bom": "npm:strip-bom@2.0.0"
    },
    "npm:lodash@3.10.1": {
      "process": "github:jspm/nodelibs-process@0.1.2"
    },
    "npm:loose-envify@1.1.0": {
      "js-tokens": "npm:js-tokens@1.0.3",
      "process": "github:jspm/nodelibs-process@0.1.2",
      "stream": "github:jspm/nodelibs-stream@0.1.0",
      "util": "github:jspm/nodelibs-util@0.1.0"
    },
    "npm:loud-rejection@1.6.0": {
      "currently-unhandled": "npm:currently-unhandled@0.4.1",
      "process": "github:jspm/nodelibs-process@0.1.2",
      "signal-exit": "npm:signal-exit@3.0.0",
      "util": "github:jspm/nodelibs-util@0.1.0"
    },
    "npm:meow@3.7.0": {
      "camelcase-keys": "npm:camelcase-keys@2.1.0",
      "decamelize": "npm:decamelize@1.2.0",
      "loud-rejection": "npm:loud-rejection@1.6.0",
      "map-obj": "npm:map-obj@1.0.1",
      "minimist": "npm:minimist@1.2.0",
      "normalize-package-data": "npm:normalize-package-data@2.3.5",
      "object-assign": "npm:object-assign@4.1.0",
      "path": "github:jspm/nodelibs-path@0.1.0",
      "process": "github:jspm/nodelibs-process@0.1.2",
      "read-pkg-up": "npm:read-pkg-up@1.0.1",
      "redent": "npm:redent@1.0.0",
      "trim-newlines": "npm:trim-newlines@1.0.0"
    },
    "npm:minimatch@2.0.10": {
      "brace-expansion": "npm:brace-expansion@1.1.3",
      "path": "github:jspm/nodelibs-path@0.1.0"
    },
    "npm:mkdirp@0.5.1": {
      "fs": "github:jspm/nodelibs-fs@0.1.2",
      "minimist": "npm:minimist@0.0.8",
      "path": "github:jspm/nodelibs-path@0.1.0",
      "process": "github:jspm/nodelibs-process@0.1.2"
    },
    "npm:normalize-package-data@2.3.5": {
      "hosted-git-info": "npm:hosted-git-info@2.1.5",
      "is-builtin-module": "npm:is-builtin-module@1.0.0",
      "semver": "npm:semver@5.3.0",
      "systemjs-json": "github:systemjs/plugin-json@0.1.2",
      "url": "github:jspm/nodelibs-location@0.1.0",
      "util": "github:jspm/nodelibs-util@0.1.0",
      "validate-npm-package-license": "npm:validate-npm-package-license@3.0.1"
    },
    "npm:os-tmpdir@1.0.1": {
      "process": "github:jspm/nodelibs-process@0.1.2"
    },
    "npm:parse-json@2.2.0": {
      "error-ex": "npm:error-ex@1.3.0"
    },
    "npm:path-browserify@0.0.0": {
      "process": "github:jspm/nodelibs-process@0.1.2"
    },
    "npm:path-exists@1.0.0": {
      "fs": "github:jspm/nodelibs-fs@0.1.2"
    },
    "npm:path-exists@2.1.0": {
      "fs": "github:jspm/nodelibs-fs@0.1.2",
      "pinkie-promise": "npm:pinkie-promise@2.0.1"
    },
    "npm:path-is-absolute@1.0.0": {
      "process": "github:jspm/nodelibs-process@0.1.2"
    },
    "npm:path-type@1.1.0": {
      "graceful-fs": "npm:graceful-fs@4.1.5",
      "pify": "npm:pify@2.3.0",
      "pinkie-promise": "npm:pinkie-promise@2.0.1"
    },
    "npm:pify@2.3.0": {
      "process": "github:jspm/nodelibs-process@0.1.2"
    },
    "npm:pinkie-promise@2.0.1": {
      "pinkie": "npm:pinkie@2.0.4"
    },
    "npm:process@0.11.7": {
      "assert": "github:jspm/nodelibs-assert@0.1.0",
      "fs": "github:jspm/nodelibs-fs@0.1.2",
      "vm": "github:jspm/nodelibs-vm@0.1.0"
    },
    "npm:punycode@1.3.2": {
      "process": "github:jspm/nodelibs-process@0.1.2"
    },
    "npm:read-pkg-up@1.0.1": {
      "find-up": "npm:find-up@1.1.2",
      "read-pkg": "npm:read-pkg@1.1.0"
    },
    "npm:read-pkg@1.1.0": {
      "load-json-file": "npm:load-json-file@1.1.0",
      "normalize-package-data": "npm:normalize-package-data@2.3.5",
      "path": "github:jspm/nodelibs-path@0.1.0",
      "path-type": "npm:path-type@1.1.0"
    },
    "npm:readable-stream@1.1.14": {
      "buffer": "github:jspm/nodelibs-buffer@0.1.0",
      "core-util-is": "npm:core-util-is@1.0.2",
      "events": "github:jspm/nodelibs-events@0.1.1",
      "inherits": "npm:inherits@2.0.1",
      "isarray": "npm:isarray@0.0.1",
      "process": "github:jspm/nodelibs-process@0.1.2",
      "stream-browserify": "npm:stream-browserify@1.0.0",
      "string_decoder": "npm:string_decoder@0.10.31"
    },
    "npm:redent@1.0.0": {
      "indent-string": "npm:indent-string@2.1.0",
      "strip-indent": "npm:strip-indent@1.0.1"
    },
    "npm:repeating@1.1.3": {
      "is-finite": "npm:is-finite@1.0.1",
      "process": "github:jspm/nodelibs-process@0.1.2",
      "systemjs-json": "github:systemjs/plugin-json@0.1.2"
    },
    "npm:repeating@2.0.1": {
      "is-finite": "npm:is-finite@1.0.1"
    },
    "npm:rw@1.3.2": {
      "buffer": "github:jspm/nodelibs-buffer@0.1.0",
      "fs": "github:jspm/nodelibs-fs@0.1.2",
      "process": "github:jspm/nodelibs-process@0.1.2"
    },
    "npm:rxjs@5.0.0-beta.2": {
      "buffer": "github:jspm/nodelibs-buffer@0.1.0",
      "process": "github:jspm/nodelibs-process@0.1.2"
    },
    "npm:rxjs@5.0.0-beta.6": {
      "buffer": "github:jspm/nodelibs-buffer@0.1.0",
      "process": "github:jspm/nodelibs-process@0.1.2"
    },
    "npm:semver@5.3.0": {
      "process": "github:jspm/nodelibs-process@0.1.2"
    },
    "npm:signal-exit@3.0.0": {
      "assert": "github:jspm/nodelibs-assert@0.1.0",
      "events": "github:jspm/nodelibs-events@0.1.1",
      "process": "github:jspm/nodelibs-process@0.1.2"
    },
    "npm:source-map-support@0.2.10": {
      "assert": "github:jspm/nodelibs-assert@0.1.0",
      "buffer": "github:jspm/nodelibs-buffer@0.1.0",
      "child_process": "github:jspm/nodelibs-child_process@0.1.0",
      "fs": "github:jspm/nodelibs-fs@0.1.2",
      "http": "github:jspm/nodelibs-http@1.7.1",
      "path": "github:jspm/nodelibs-path@0.1.0",
      "process": "github:jspm/nodelibs-process@0.1.2",
      "querystring": "github:jspm/nodelibs-querystring@0.1.0",
      "source-map": "npm:source-map@0.1.32"
    },
    "npm:source-map@0.1.32": {
      "amdefine": "npm:amdefine@1.0.0",
      "fs": "github:jspm/nodelibs-fs@0.1.2",
      "path": "github:jspm/nodelibs-path@0.1.0",
      "process": "github:jspm/nodelibs-process@0.1.2"
    },
    "npm:source-map@0.5.3": {
      "process": "github:jspm/nodelibs-process@0.1.2"
    },
    "npm:spdx-correct@1.0.2": {
      "spdx-license-ids": "npm:spdx-license-ids@1.2.2"
    },
    "npm:spdx-expression-parse@1.0.2": {
      "spdx-exceptions": "npm:spdx-exceptions@1.0.5",
      "spdx-license-ids": "npm:spdx-license-ids@1.2.2"
    },
    "npm:spdx-license-ids@1.2.2": {
      "systemjs-json": "github:systemjs/plugin-json@0.1.2"
    },
    "npm:stream-browserify@1.0.0": {
      "events": "github:jspm/nodelibs-events@0.1.1",
      "inherits": "npm:inherits@2.0.1",
      "readable-stream": "npm:readable-stream@1.1.14"
    },
    "npm:string_decoder@0.10.31": {
      "buffer": "github:jspm/nodelibs-buffer@0.1.0"
    },
    "npm:strip-ansi@3.0.1": {
      "ansi-regex": "npm:ansi-regex@2.0.0"
    },
    "npm:strip-bom@2.0.0": {
      "buffer": "github:jspm/nodelibs-buffer@0.1.0",
      "is-utf8": "npm:is-utf8@0.2.1"
    },
    "npm:strip-indent@1.0.1": {
      "fs": "github:jspm/nodelibs-fs@0.1.2",
      "get-stdin": "npm:get-stdin@4.0.1",
      "process": "github:jspm/nodelibs-process@0.1.2",
      "systemjs-json": "github:systemjs/plugin-json@0.1.2"
    },
    "npm:supports-color@2.0.0": {
      "process": "github:jspm/nodelibs-process@0.1.2"
    },
    "npm:url@0.10.3": {
      "assert": "github:jspm/nodelibs-assert@0.1.0",
      "punycode": "npm:punycode@1.3.2",
      "querystring": "npm:querystring@0.2.0",
      "util": "github:jspm/nodelibs-util@0.1.0"
    },
    "npm:user-home@1.1.1": {
      "process": "github:jspm/nodelibs-process@0.1.2",
      "systemjs-json": "github:systemjs/plugin-json@0.1.2"
    },
    "npm:util@0.10.3": {
      "inherits": "npm:inherits@2.0.1",
      "process": "github:jspm/nodelibs-process@0.1.2"
    },
    "npm:validate-npm-package-license@3.0.1": {
      "spdx-correct": "npm:spdx-correct@1.0.2",
      "spdx-expression-parse": "npm:spdx-expression-parse@1.0.2"
    },
    "npm:vm-browserify@0.0.4": {
      "indexof": "npm:indexof@0.0.1"
    },
    "npm:xmlhttprequest@1.8.0": {
      "buffer": "github:jspm/nodelibs-buffer@0.1.0",
      "child_process": "github:jspm/nodelibs-child_process@0.1.0",
      "fs": "github:jspm/nodelibs-fs@0.1.2",
      "http": "github:jspm/nodelibs-http@1.7.1",
      "https": "github:jspm/nodelibs-https@0.1.0",
      "process": "github:jspm/nodelibs-process@0.1.2",
      "url": "github:jspm/nodelibs-location@0.1.0"
    },
    "npm:zone.js@0.6.12": {
      "buffer": "github:jspm/nodelibs-buffer@0.1.0",
      "process": "github:jspm/nodelibs-process@0.1.2"
    }
  }
});
