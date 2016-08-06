<%@ page contentType="text/html; charset=UTF-8" %><!doctype html>
<html>
<head>
  <base href="/">
  <meta charset="utf-8">
  <title>Model Server Console</title>
    <link rel="stylesheet" href="/resources/styles/ms-styles.css">
    <link rel="stylesheet" href="/resources/styles/styles.css">
<!-- ${pageContext.request.contextPath} -->
<!--   {{#unless environment.production}} -->
<!--   <script src="modelserver/ember-cli-live-reload.js" type="text/javascript"></script> -->
<!--   {{/unless}} -->
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="icon" type="image/x-icon" href="modelserver/favicon.ico">
  <link rel="stylesheet" href="/vendor/codemirror/lib/codemirror.css">
</head>
<body>
  <div id="mainContentCtr">
    <ms-home>Loading...</ms-home>
  </div>
  
<%--   <script src="/systemjs.config.js"></script> --%>
  <script src="/vendor/d3/build/d3.js"></script>
  <script src="/vendor/systemjs/dist/system.src.js"></script>
  <script src="/vendor/dateformat/lib/dateformat.js"></script>
  <script src="/vendor/codemirror/lib/codemirror.js"></script>
  <script src="/vendor/es6-shim/es6-shim.js"></script>
  <script src="/vendor/zone.js/dist/zone.js"></script>
  <script src="/vendor/reflect-metadata/Reflect.js"></script>
  
<!--     {{#each scripts.polyfills}} -->
<!--     <script src="{{.}}"></script> -->
<!--     {{/each}} -->
    <script>
      System.import('/system-config.js').then(function () {
        System.import('/main.js');
      }).catch(console.error.bind(console));
    </script>
  
</body>
</html>
