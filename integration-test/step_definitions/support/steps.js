const {Given, When, Then} = require('@cucumber/cucumber')
const {healthCheckInfo} = require("./reporting_orgs_enrollment_client");
const assert = require("assert");


Given('Reporting Enrollment running', async function () {
    const response = await healthCheckInfo();
    assert.strictEqual(response.status, 200);
});
