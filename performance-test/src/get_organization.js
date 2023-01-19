import http from 'k6/http';
import {check} from 'k6';
import {SharedArray} from 'k6/data';
import { getMockFiscalCode, getRandomItemFromArray } from './modules/helpers.js';
import { getOrganization, postOrganization, deleteOrganization } from "./modules/client.js";

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
const numberOfEventsToPreload = `${vars.numberOfEventsToPreload}`;

var containerFCs = new Array();


export function setup() {
    // 2. setup code (once)
    // The setup code runs, setting up the test environment (optional) and generating data
    // used to reuse code for the same VU

    let url = `${rootUrl}`;

    for (let i = 0; i < numberOfEventsToPreload; i++) {
    	let fc = getMockFiscalCode(25);
        let res = postOrganization(url, fc);
    	check(res, { "status is 201": (res) => (res.status === 201) });
    	containerFCs.push(fc);
    }


    // return the array with preloaded id
    return { fcs: containerFCs }


    // precondition is moved to default fn because in this stage
    // __VU is always 0 and cannot be used to create env properly
}

// teardown the test data
export function teardown(data) {
    let url = `${rootUrl}`;

	for (const fc of data.fcs) {
	    let res = deleteOrganization(url, fc);
		check(res, { "status is 200": (res) => (res.status === 200) });
	}
}

function precondition() {
    // no pre conditions
}

function postcondition() {

    // Delete the new entity created
}

export default function (data) {
    let tag = {
        method: "GET organization",
    };

    let url = `${rootUrl}`;
    let organizationFiscalCode = getRandomItemFromArray(data.fcs);
    let res = getOrganization(url, organizationFiscalCode);

    console.log(`GetOrganization/${organizationFiscalCode} -> ${res.status}`);

    check(res, {
        'check status is 200': (_r) => res.status === 200,
    }, tag);

    postcondition();
}
