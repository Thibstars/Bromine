# Bromine [![Build Status](https://travis-ci.org/Thibstars/Bromine.svg)](https://travis-ci.org/Thibstars/Bromine) #
Selenium tools for Java. Offers means to simplify the setup of a Selenium project.

## Features ##
* Commands to find elements;
* Screenshot support (and a JUnit Rule capturing screenshots on test failure);
* Custom JUnit rules with various purposes;
* Improved general navigation;
* Support for multitude of wait methods for the WebDriver;
* Structure for pages and their sections in your SUT/Project;
* Functionality for tracking of events, such as mouse clicks;
* Reporting features ([Allure](https://github.com/allure-framework/allure-core) support, custom reporting support);
* Structure for the SUT/Project environment and accounts;
* Highlighting WebElements;
* Cookies management;
* Customizable action and wait implementations;
* Wrapped WebElements (like check boxes);
* Storing By locator in WebElementByProxy (not provided in Selenium);

## Dependencies ##
* Selenium Server Standalone 3.0.0;
* Apache Commons Lang 3.5;
* Log4J 1.2 - API 2.5;
* Log4J - API 2.5;
* Log4J - Core 2.5;
* Allure JUnit Adaptor
* Allure Java Annotations

## Getting started ##
* Copy al the resources into your project (important, won't work otherwise!);
* Execute `new InitFrameworkCommand().execute();`;
* Create a new Navigator: `NavigatorFactory.createNavigator(myEnvironment);`;
* Register your pages: `Pages.registerPage(myPage);`;
* Add sections to your pages if any: `myPage.addSection(mySection);`;

You're now good to go. You can now start building your framework. 
A good start could be `myPage.goTo();`.

## Projects using this library ##
Here's a [wikipage list of live projects using Bromine](https://github.com/Thibstars/Bromine/wiki/Projects-using-Bromine).

*If you are using `Bromine` in a live application, please [add it to this list](https://github.com/Thibstars/Bromine/wiki/Projects-using-Bromine).*