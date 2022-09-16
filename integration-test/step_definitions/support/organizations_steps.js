const assert = require('assert')
const {Given, When, Then} = require('@cucumber/cucumber')
const {randomOrg} = require("./common");
const {
    createOrganization, getOrganizations, getOrganization, removeOrganization
} = require("./reporting_orgs_enrollment_client");

let idOrg;
let responseToCheck;
let organization;
let service;


// Given

Given('a random id organization', async function () {
    idOrg = randomOrg();
    // precondition -> deletion possible dirty data
    await removeOrganization(idOrg);
});

Given('a valid enrollment', async function () {
	idOrg = randomOrg();
	// precondition -> creation of an organization to delete
    responseToCheck = await createOrganization(idOrg);
    assert.strictEqual(responseToCheck.status, 201);
    // save data
    organization = responseToCheck.data;
});

// When

When('the organization {string} enrollment', async function (action) {
	if (action==="creates"){
		responseToCheck = await createOrganization(idOrg);
    	// save data
    	organization = responseToCheck.data;
	} else if (action==="deletes"){
    	responseToCheck = await removeOrganization(idOrg);
    } else if (action==="gets"){
    	responseToCheck = await getOrganization(idOrg);
    }
    else if (action==="gets all"){
    	responseToCheck = await getOrganizations();
    }
});




// Then
Then('the organization gets the status code {int}', function (status) {
    assert.strictEqual(responseToCheck.status, status);
});

