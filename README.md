# dailyresu.me

Dailyresu.me is an application that will show you the best posts of each day of your social networks. Before you try to use it, you should change some parts of the code.

# The database
Create a MySQL database with the name that you want. By default the application will look for a database called 'dailyresume'. Use UTF8mb4 for encoding (why or how? https://mathiasbynens.be/notes/mysql-utf8mb4). In this project we use "root" as user and a blank password. You can change the database configuration in /src/main/resources/config, in the application-dev.yml and the application-prod.yml. If you're going to use a password and you're going to add a new table to the database, liquibase will ask for a diff with its plugin, so you'll also have to add the password on the pom.xml, where the plugin is declared.

# Social networks
You should create an app on every social network you're going to use (by default it only works with Twitter and Tumblr). They will provide you with a public key and a secret key that you will have to insert on src/main/java/com/yourname/dailyresume/domain, in the TwitterConnection.java and TumblrConnection.java to allow the application to connect to the API, and on the application.yml (in /src/main/resources/config) to allow the application to connect a user with the application you have created.

#E-mail
In order to keep everything secure, dailyresu.me sends you an e-mail once you create a new account. That's configured at /src/main/resources/config, in the application-dev.yml and the application-prod.yml. Everything is commented and prepared to be connected with a GMail account. Once you do it, you'll have to take care with security on gmail, because by default it doesn't allow that kind of use.

# JHipster

This application was generated using JHipster, you can find documentation and help at [https://jhipster.github.io](https://jhipster.github.io).

Before you can build this project, you must install and configure the following dependencies on your machine:

1. [Node.js][]: We use Node to run a development web server and build the project.
   Depending on your system, you can install Node either from source or as a pre-packaged bundle.

After installing Node, you should be able to run the following command to install development tools (like
[Bower][] and [BrowserSync][]). You will only need to run this command when dependencies change in package.json.

    npm install

We use [Grunt][] as our build system. Install the grunt command-line tool globally with:

    npm install -g grunt-cli

Run the following commands in two separate terminals to create a blissful development experience where your browser
auto-refreshes when files change on your hard drive.

    mvn
    grunt

Bower is used to manage CSS and JavaScript dependencies used in this application. You can upgrade dependencies by
specifying a newer version in `bower.json`. You can also run `bower update` and `bower install` to manage dependencies.
Add the `-h` flag on any command to see how you can use it. For example, `bower update -h`.

## Building for production

To optimize the dailyresume client for production, run:

    mvn -Pprod clean package

This will concatenate and minify CSS and JavaScript files. It will also modify `index.html` so it references
these new files.

To ensure everything worked, run:

    java -jar target/*.war --spring.profiles.active=prod

Then navigate to [http://localhost:8080](http://localhost:8080) in your browser.

## Testing

Unit tests are run by [Karma][] and written with [Jasmine][]. They're located in `src/test/javascript` and can be run with:

    grunt test



## Continuous Integration

To setup this project in Jenkins, use the following configuration:

* Project name: `dailyresume`
* Source Code Management
    * Git Repository: `git@github.com:xxxx/dailyresume.git`
    * Branches to build: `*/master`
    * Additional Behaviours: `Wipe out repository & force clone`
* Build Triggers
    * Poll SCM / Schedule: `H/5 * * * *`
* Build
    * Invoke Maven / Tasks: `-Pprod clean package`
* Post-build Actions
    * Publish JUnit test result report / Test Report XMLs: `build/test-results/*.xml`

[JHipster]: https://jhipster.github.io/
[Node.js]: https://nodejs.org/
[Bower]: http://bower.io/
[Grunt]: http://gruntjs.com/
[BrowserSync]: http://www.browsersync.io/
[Karma]: http://karma-runner.github.io/
[Jasmine]: http://jasmine.github.io/2.0/introduction.html
[Protractor]: https://angular.github.io/protractor/
