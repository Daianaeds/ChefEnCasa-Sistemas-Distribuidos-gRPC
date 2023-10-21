const soap = require('soap')
const url = 'http://127.0.0.1:8087/?WSDL'

class SoapConfiguration {
  constructor() {}

  createRecipeBook(args, callback) {
    soap.createClient(url, (err, client) => {
      if (err) {
        callback(err, null)
      } else {
        console.log('client:', client)
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
        console.log('client:', client)
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
        console.log('client:', client)
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
        console.log('client:', client)
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
        console.log('client:', client)
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
        console.log('client:', client)
        client.getRecipeBook({ idRecipeBook: args }, function (err, result) {
          callback(null, result)
        })
      }
    })
  }
}

module.exports = SoapConfiguration
