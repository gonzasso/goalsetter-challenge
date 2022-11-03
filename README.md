# goalsetter-challenge

To use project locally, modify device configuration under qa.properties file specifying <code>"platform.name"</code>, <code>"platform.version"</code>, <code>"automation.name"</code>, <code>"device.udid"</code>, <code>"app.dir"</code>.

To run tests, execute <code>mvn clean tests</code>. You can also set parameters <code>--D"cucumber.filter.tags={tags}"</code> to run tests by filter (@Android or @API)

Report results are under <code>test-output</code> folder.
