const PROTO_PATH = './protos/Server.proto'
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

const UserService = grpc.loadPackageDefinition(packageDefinition).com.grpc.grpcServer
const client = new UserService.Service('localhost:9090', grpc.credentials.createInsecure())

module.exports = client
