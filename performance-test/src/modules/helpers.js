export function getMockFiscalCode(length) {
  var result           = '';
  var characters       = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
  var charactersLength = characters.length;
  for ( var i = 0; i < length; i++ ) {
    result += characters.charAt(Math.floor(Math.random() * charactersLength));
  }
  return result;
}

export function getRandomItemFromArray(items) {
	return items[Math.floor(Math.random()*items.length)];
}

export function randomOrg(length, charset) {
  let res = '';
  while (length--) res += charset[(Math.random() * charset.length) | 0];
  return res;
}