import http from 'k6/http';
import { check } from 'k6';
import { SharedArray } from 'k6/data';
import { randomOrg } from './modules/helpers.js';

//k6 run -o influxdb=http://influxdb:8086/k6 -e BASE_URL=http://localhost:8085 performance-test/src/full_load_test.js
//k6 run performance-test/src/full_load_test.js
export let options = JSON.parse(open(__ENV.TEST_TYPE));

const varsArray = new SharedArray('vars', function () {
    return JSON.parse(open(`${__ENV.VARS}`)).environment;
});

// workaround to use shared array (only array should be used)
const vars = varsArray[0];
const rootUrl = `${vars.host}`;

const params = {
    headers: {
        'Content-Type': 'application/json',
        'Ocp-Apim-Subscription-Key': __ENV.API_SUBSCRIPTION_KEY
    },
};

const frameworkType = `?frameworkType=quarkus`

export default function () {

  const organization_fiscal_code = randomOrg(11, "0123456789");

  // Create new debt position (no validity date).

  var url = `${rootUrl}/organizations/${organization_fiscal_code}${frameworkType}`;

  var r = http.post(url, "", params);

  console.log("CreateOrganization call - organization_fiscal_code = " + organization_fiscal_code + ", Status = " + r.status);

  check(r, {
    'CreateOrganization status is 201': (r) => r.status === 201,
  });
}