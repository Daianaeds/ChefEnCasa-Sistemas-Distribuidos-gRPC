const PROTO_PATH = './protos/UserService.proto'
const grpc = require('@grpc/grpc-js')
const protoLoader = require('@grpc/proto-loader')

const options = {
  keepCase: true,
  longs: String,
  enums: String,
  defaults: true,
  oneofs: true,
}

var packageDefinition = protoLoader.loadSync(PROTO_PATH, options)

const UserService = grpc.loadPackageDefinition(packageDefinition).com.sistemasdistribuidos
const client = new UserService.UserService('localhost:8900', grpc.credentials.createInsecure())

module.exports = client
