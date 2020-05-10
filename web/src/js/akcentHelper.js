const akcentSk = 'éěÉĚřŘťŤžŽúÚůŮüÜíÍóÓáÁšŠďĎýÝčČňŇäÄĺĹľĽŕŔöÖô';
const akcentEn = 'eeEErRtTzZuUuUuUiIoOaAsSdDyYcCnNaAlLlLrRoOo';
const specUriChars = '!\'*-._|~';

const deleteAkcent = (txt) => {
  let sb = '';
  for (let i = 0; i < txt.length; i += 1) {
    const ch = txt[i];
    if (ch === ' ') {
      sb += ' ';
    } else if (ch >= 'a' && ch <= 'z') {
      sb += ch;
    } else if (ch >= 'A' && ch <= 'Z') {
      sb += ch;
    } else if (ch >= '0' && ch <= '9') {
      sb += ch;
    } else if (specUriChars.indexOf(ch) !== -1) {
      sb += ch;
    } else {
      for (let j = 0; j < akcentSk.length; j += 1) {
        if (ch === akcentSk[j]) {
          sb += akcentEn[j];
          break;
        }
      }
    }
  }
  return sb;
};

export default {
  deleteAkcent,
};
