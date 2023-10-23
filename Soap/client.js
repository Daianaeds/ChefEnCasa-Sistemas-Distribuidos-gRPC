const soap = require('soap')
const url = 'http://127.0.0.1:8087/?WSDL'
const urlDenunciation = 'http://127.0.0.1:8088/?WSDL'

class SoapConfiguration {
  constructor() {}

  createRecipeBook(args, callback) {
    soap.createClient(url, (err, client) => {
      if (err) {
        callback(err, null)
      } else {
        client.createRecipeBook({ request: args }, function (err, result) {
          callback(null, result)
        })
      }
    })
  }

  deleteRecipeBook(args, callback) {
    soap.createClient(url, (err, client) => {
      if (err) {
        callback(err, null)
      } else {
        client.deleteRecipeBook({ idRecipeBook: args }, function (err, result) {
          callback(null, result)
        })
      }
    })
  }

  addRecipeToRecipeBook(args, callback) {
    soap.createClient(url, (err, client) => {
      if (err) {
        callback(err, null)
      } else {
        client.addRecipe(
          { idRecipeBook: args.idRecipeBook, idRecipe: args.idRecipe },
          function (err, result) {
            callback(null, result)
          }
        )
      }
    })
  }

  deleteRecipeToRecipeBook(args, callback) {
    soap.createClient(url, (err, client) => {
      if (err) {
        callback(err, null)
      } else {
        client.deleteRecipe(
          { idRecipeBook: args.idRecipeBook, idRecipe: args.idRecipe },
          function (err, result) {
            callback(null, result)
          }
        )
      }
    })
  }

  listRecipeBooks(args, callback) {
    soap.createClient(url, (err, client) => {
      if (err) {
        callback(err, null)
      } else {
        client.listRecipeBooks({ username: args }, function (err, result) {
          callback(null, result)
        })
      }
    })
  }

  getRecipeBook(args, callback) {
    soap.createClient(url, (err, client) => {
      if (err) {
        callback(err, null)
      } else {
        client.getRecipeBook({ idRecipeBook: args }, function (err, result) {
          callback(null, result)
        })
      }
    })
  }

  addDenunciation(args, callback) {
    soap.createClient(urlDenunciation, (err, client) => {
      if (err) {
        callback(err, null)
      } else {
        client.createDenunciation(
          { username: args.username, motive: args.motive, idRecipe: args.idRecipe },
          function (err, result) {
            callback(null, result)
          }
        )
      }
    })
  }

  deleteDenunciation(args, callback) {
    soap.createClient(urlDenunciation, (err, client) => {
      if (err) {
        callback(err, null)
      } else {
        client.deleteDenunciation({ idDenunciation: args }, function (err, result) {
          callback(null, result)
        })
      }
    })
  }

  ignoreDenunciation(args, callback) {
    soap.createClient(urlDenunciation, (err, client) => {
      if (err) {
        callback(err, null)
      } else {
        client.ignoreDenunciation({ idDenunciation: args }, function (err, result) {
          callback(null, result)
        })
      }
    })
  }
  denunciations(args, callback) {
    soap.createClient(urlDenunciation, (err, client) => {
      if (err) {
        callback(err, null)
      } else {
        client.denunciationList({}, function (err, result) {
          callback(null, result)
        })
      }
    })
  }
}

module.exports = SoapConfiguration
