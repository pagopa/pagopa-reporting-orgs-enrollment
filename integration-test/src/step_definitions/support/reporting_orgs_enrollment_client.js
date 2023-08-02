const {post, get, del} = require("./common");

function healthCheckInfo() {
    return get(`/info`)
}

function getOrganizations() {
    return get(`/organizations`)
}

function getOrganization(idOrg) {
    return get(`/organizations/${idOrg}`)
}

function createOrganization(idOrg) {
    return post(`/organizations/${idOrg}`)
}

function removeOrganization(idOrg) {
    return del(`/organizations/${idOrg}`)
}



module.exports = {
	healthCheckInfo,
	getOrganizations,
    getOrganization,
    createOrganization,
    removeOrganization
}
