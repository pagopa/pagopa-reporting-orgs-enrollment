const axios = require("axios");

const app_host = process.env.reporting_enrollment_host;
const subkey = process.env.API_SUBSCRIPTION_KEY;

function get(url) {
    return axios.get(app_host + url, {
            headers: {
                "Ocp-Apim-Subscription-Key": process.env.API_SUBSCRIPTION_KEY
            }
        })
         .then(res => {
             return res;
         })
         .catch(error => {
             return error.response;
         });
}

function post(url, body) {
    return axios.post(app_host + url, body, {
            headers: {
                "Ocp-Apim-Subscription-Key": process.env.API_SUBSCRIPTION_KEY
            }
        })
        .then(res => {
            return res;
        })
        .catch(error => {
            return error.response;
        });
}

function put(url, body) {
    return axios.put(app_host + url, body, {
            headers: {
                "Ocp-Apim-Subscription-Key": process.env.API_SUBSCRIPTION_KEY
            }
        })
        .then(res => {
            return res;
        })
        .catch(error => {
            return error.response;
        });
}


function del(url) {
    return axios.delete(app_host + url, {
            headers: {
                "Ocp-Apim-Subscription-Key": process.env.API_SUBSCRIPTION_KEY
            }
        })
        .then(res => {
            return res;
        })
        .catch(error => {
            return error.response;
        });
}

function randomOrg() {
    return "Org_" + Math.floor(Math.random() * 100);
}


module.exports = {get, post, put, del, randomOrg}
