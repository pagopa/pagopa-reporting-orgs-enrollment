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

	precondition(mock_fiscal_code);

	const response = deleteOrganization(rootUrl, mock_fiscal_code);

    console.log(`setup ... ${response.status}`);

    check(response, {
        "status is 200": (res) => (res.status === 200),
    });

	// precondition is moved to default fn because in this stage
	// __VU is always 0 and cannot be used to create env properly
}

function precondition(organizationFiscalCode) {
	// Create organization to delete

    let tag = {
    	method: "CreateOrganization",
    };

    let r = postOrganization(rootUrl, organizationFiscalCode);

    console.log("CreateOrganization call - organization_fiscal_code " + organizationFiscalCode + ", Status " + r.status);

    check(r, {
    	"CreateOrganization status is 201": (_r) => r.status === 201,
    }, tag);
}

function postcondition(organizationFiscalCode) {
    // no post conditions
}

export default function() {
    let tag = {
        method: "DELETE organization",
    };

    let organizationFiscalCode = getMockFiscalCode(25);

    precondition(organizationFiscalCode);

	let r = deleteOrganization(rootUrl, organizationFiscalCode);

	console.log("DeleteOrganization call - organization_fiscal_code " + organizationFiscalCode + ", Status " + r.status);

	check(r, {
		'CreateOrganization status is 200': (_r) => r.status === 200,
	}, tag);

	if (r.status === 201) {
		postcondition(organizationFiscalCode);
	}
}
