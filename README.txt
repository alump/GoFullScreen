GoFullScreen Vaadin UI Component Add On

GoFullScreen add on provides button that will handle fullscreen mode switching
on client side. Can be used to fullscreen whole Vaadin application or single
components inside it (like images or videos).

Demo application: http://siika.fi:8080/GoFullScreenDemo/
Source code: https://github.com/alump/GoFullScreen
Vaadin Directory: https://vaadin.com/directory#addon/gofullscreen
License: Apache License 2.0

This project can be imported to Eclipse with m2e.

Simple Maven tutorials:


***** How to compile add on jar package for your project *****

> cd gofullscreen-addon
> mvn package

add on can be found at: picker-addon/target/GoFullScreen-<version>.jar
zip package used at Vaadin directory can be found at:
picker-addon/target/GoFullScreen-<version>.zip

***** How to install GoFullScreen to your Maven repository *****

To install addon to your local repository, run:

> cd gofullscreen-addon
> mvn install


***** How to run test application *****

First compile and install addon (if not already installed)
> cd gofullscreen-addon
> mvn install

Then compile demo widgetset and start HTTP server
> cd ../gofullscreen-demo
> mvn vaadin:compile
> mvn jetty:run

Demo application is running at http://localhost:8080/gofullscreen



***** How to compile test application WAR *****

First compile and install addon (if not already installed)
> cd gofullscreen-addon
> mvn install

Then construct demo package (this should automatically compile widgetset)
> cd ../gofullscreen-demo
> mvn package

War package can be now found at gofullscreen-demo/target/GoFullScreenDemo.war
