# Tech Test Challenge - Zinio
This is a technical test for Zinio.

## Instructions
Clone the repo:

Git:
```
$ git clone git@github.com:joseator/Zinio.git
```

Or download a ZIP of master [manually](https://github.com/joseator/Zinio/archive/master.zip) and expand the contents someplace on your system

## Prerequisites
* [Java 15 SDK](https://www.oracle.com/java/technologies/javase/jdk15-archive-downloads.html)
* [Maven 3.6.3](https://maven.apache.org/download.cgi) or Upper

## Verify installation

Open a command window and run:

    mvn test

This runs Cucumber features using Cucumber's JUnit runner. The `@RunWith(Cucumber.class)` annotation on the `TestRunner`
class tells JUnit to kick off Cucumber.

## Filter Options

The Cucumber runtime parses command line options to know what features to run.
When you use the JUnit runner, these options are generated from the `@CucumberOptions` annotation on your test in the `tag` option.

Sometimes it can be useful to override these tags without changing or recompiling the JUnit class. This can be done with the
`cucumber.filter.tags` system property. The general form is:

    mvn test -D"cucumber.filter.tags=@TAG"

## Output
When the test execution finish, a html report is generated in `test-output/Report/ReporteHTML.html`.
Also there are availables in the path a JSON and PDF reports.

## JavaDoc
There are available JavaDoc information in the next path:
`JavaDoc/index.html`. 