[![Published on Vaadin  Directory](https://img.shields.io/badge/Vaadin%20Directory-published-00b4f0.svg)](https://vaadin.com/directory/component/gofullscreen)
[![Stars on Vaadin Directory](https://img.shields.io/vaadin-directory/star/gofullscreen.svg)](https://vaadin.com/directory/component/gofullscreen)

GoFullScreen Vaadin UI Component Add On
=======================================

[![Build Status](https://epic.siika.fi/jenkins/job/GoFullScreen%20(Vaadin)/badge/icon)](https://epic.siika.fi/jenkins/job/GoFullScreen%20(Vaadin)/)

GoFullScreen add on provides button that will handle fullscreen mode switching
on client side. Can be used to fullscreen whole Vaadin application or single
components inside it (like images or videos).

* Demo application: [http://app.siika.fi/GoFullScreenDemo/](http://app.siika.fi/GoFullScreenDemo/)
* Source code: [https://github.com/alump/GoFullScreen](https://github.com/alump/GoFullScreen)
* Vaadin Directory: [https://vaadin.com/directory#addon/gofullscreen](https://vaadin.com/directory#addon/gofullscreen)
* License: Apache License 2.0

## Usage

### Button

```java
FullScreenButton button = new FullScreenButton(VaadinIcons.EXPAND_FULL);
button.setFullScreenTarget(componentShownInFullscreen);
```

### MenuBar

```java
FullScreenMenuBar menuBar = new FullScreenMenuBar();
MenuBar.MenuItem view = menuBar.addItem("View", null);
MenuBar.MenuItem fullscreenImage = view.addItem("Image in fullscreen", null);
menuBar.addFullScreenMapping(fullscreenImage, imageComponent);
MenuBar.MenuItem fullscreenAll = view.addItem("All in fullscreen", null);
menuBar.addFullScreenMapping(fullscreenAll, null);
```

## Release notes

### Version 0.6.1 (2017-06-21)
- Add support for MenuBar

### Version 0.6.0 (2017-03-10)
- Version 0.6.0 and versions after it require and support Vaadin 8. For Vaadin 7, use 0.5.x versions.

### Version 0.5.3 (2016-04-12)
- Fixing project manifest files to format accepted again by Vaadin Directory
- Cleaning API little more, as 0.5.0 release failed. Allowing adding non-button components later.

### Version 0.5.0 (2016-04-07)
- OSGi bundled
- Listener API cleanup (API break)
- Moved to use SCSS theming (add-on currently do not include any theming rules, so no actions required)
