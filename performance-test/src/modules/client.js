import http from 'k6/http';


export function postOrganization(rootUrl, organizationFiscalCode) {
	const url = `${rootUrl}/${organizationFiscalCode}`

	const params = {
        headers: {
            'Content-Type': 'application/json'
        },
    };

	const payload = {
	    "organizationFiscalCode": organizationFiscalCode
	}

	return http.post(url, JSON.stringify(payload), params);
}

export function deleteOrganization(rootUrl, organizationFiscalCode) {
	const url = `${rootUrl}/${organizationFiscalCode}`
	return http.del(url);
}

export function getOrganization(rootUrl, organizationFiscalCode) {
	const url = `${rootUrl}/${organizationFiscalCode}`
    return http.get(url);
}

export function getOrganizations(rootUrl) {
    return http.get(rootUrl);
}
