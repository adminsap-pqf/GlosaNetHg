export const numberLocales = {
  'es_MX': {
      currency: {
      symbol : '$',
      format: '%s%v',
      decimal : '.',
      thousand: ',',
      precision : 2
    },
    number: {
      precision : 0,  // default precision on numbers is 0
      thousand: '*',
      decimal : '+'
    }
  },
  'en_US': {
      currency: {
      symbol : '$',
      format: '%s%v',
      decimal : '.',
      thousand: ',',
      precision : 2
    },
    number: {
      precision : 0,  // default precision on numbers is 0
      thousand: ',',
      decimal : '.'
    }
  }
}

