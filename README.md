# Fourteen-Two

The core libraries for a cloud-based Ultimate Pool League scoring
application.

## quickstart

This project uses [Gradle](http://gradle.org/) as a build system, and provides [standardized project scripts](http://githubengineering.com/scripts-to-rule-them-all/) for developers.

* `./build/watch` - Runs all checks and tests, watching for dependent changes in source trees.
* `./build/cibuild` - Run all continuous integration tasks.
* `./gradlew tasks` - Show all Gradle build tasks.

## releasing

This product releases to Bintray / JCenter at present.  To produce releases

* set the environment variables required to authenticate to Bintray
  * `BINTRAY_USER`
  * `BINTRAY_KEY`
* remember to update `allProjects.group`

## development / API

### match schedule

Schedules in UPL leagues are exclusively published via PDF.  PDFs for match schedules are published on the the UPL public web site

http://www.m8pool.com/

at fairly regular URLs, e.g.:

* http://www.m8pool.com/pdfs/advsunsched.pdf, the current *advanced*, *Sunday* schedule
* http://www.m8pool.com/pdfs/opensunsched.pdf, the current *open*, *Sunday* schedule

#### OCR API

To obtain machine-parsable text data, the application currently makes use of a pulbic OCR REST service ([described here](http://okfnlabs.org/blog/2015/02/21/documents-to-text.html)).

`curl --upload-file ./src/test/resources/2015/fall/advsundiv.pdf http://beta.offenedaten.de:9998/tika`

will process one of the downloaded test resoures.  *Note* that direct push to the server

`curl http://www.m8pool.com/pdfs/advsunsched.pdf http://beta.offenedaten.de:9998/tika`

does not currently work, so obtaining the data and pushing to the Web API is currently a two-step process.
