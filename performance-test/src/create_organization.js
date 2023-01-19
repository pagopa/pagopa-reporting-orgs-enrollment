import http from 'k6/http';
import { check } from 'k6';
import { SharedArray } from 'k6/data';

import { getMockFiscalCode } from './modules/helpers.js';

import { postOrganization, deleteOrganization } from "./modules/client.js";

export let options = JSON.parse(open(__ENV.TEST_TYPE));

// read configuration
// note: SharedArray can currently only be constructed inside init code
// according to https://k6.io/docs/javascript-api/k6-data/sharedarray
const varsArray = new SharedArray('vars', function () {
	return JSON.parse(open(`./${__ENV.VARS}`)).environment;
});
// workaround to use shared array (only array should be used)
const vars = varsArray[0];
const rootUrl = `${vars.host}/${vars.basePath}`;

const mock_fiscal_code = `mockOrganizationFC`;


export function setup() {
	// 2. setup code (once)
	// The setup code runs, setting up the test environment (optional) and generating data
	// used to reuse code for the same VU

	const response = postOrganization(rootUrl, mock_fiscal_code);

    console.log(`setup ... ${response.status}`);

    check(response, {
        "status is 201 or 409": (res) => (res.status === 201 || res.status === 409),
    });

	// precondition is moved to default fn because in this stage
	// __VU is always 0 and cannot be used to create env properly
}

export function teardown(data) {
    postcondition(mock_fiscal_code);
}

function precondition() {
	// no pre conditions
}

function postcondition(organizationFiscalCode) {
	// Delete the newly created organization
	let tag = {
		method: "DeleteOrganization",
	};

	let r = deleteOrganization(rootUrl, organizationFiscalCode);

	console.log("DeleteOrganization call - organization_fiscal_code " + organizationFiscalCode + ", Status " + r.status);

	check(r, {
		"DeleteOrganization status is 200": (_r) => r.status === 200,
	}, tag);
}

export default function() {
    let tag = {
        method: "CREATE organization",
    };

    let organizationFiscalCode = getMockFiscalCode(25);

	let r = postOrganization(rootUrl, organizationFiscalCode);

	console.log("CreateOrganization call - organization_fiscal_code " + organizationFiscalCode + ", Status " + r.status);

	check(r, {
		'CreateOrganization status is 201': (_r) => r.status === 201,
	}, tag);

	if (r.status === 201) {
		postcondition(organizationFiscalCode);
	}
}
