const { Client } = require('@elastic/elasticsearch')
const { events } = require('@elastic/elasticsearch')
const { errors } = require('@elastic/elasticsearch')

const logger = require('my-logger')
const fs = require('fs');
const Mock = require('@elastic/elasticsearch-mock')

const mock = new Mock()
const client = new Client({
  node: 'https://localhost:9200/',
  auth: {
    username: 'elastic',
    // password: 'elastic'
    password: 'gcf5csbvgj8mt6nkjn65gwfm'
  },
  // ssl: {
  //   ca: fs.readFileSync('./SHA256CAbundle.pem'),
  //   rejectUnauthorized: true
  // },
  Connection: mock.getConnection()
})
console.log(errors.ConnectionError.toString())

// mock.add({
//   method: 'GET',
//   path: '/_security/role_mapping/write-user'
// }, () => {
//   return { status: 'ok' }
// })

// client.info(console.log)

// console.log(client)

client.on('response', (err, result) => {
  if (err) {
    logger.error(err)
  } else {
    logger.info(result)
  }
})