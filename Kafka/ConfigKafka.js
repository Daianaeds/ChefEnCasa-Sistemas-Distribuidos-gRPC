const { Kafka } = require('kafkajs')

class KafkaConfiguration {
  constructor() {
    this.kafka = new Kafka({
      clientId: 'my-app',
      brokers: ['127.0.0.1:9092'],
    })
    this.producer = this.kafka.producer()
    this.messagesNovedades = []
    this.messagesComentarios = []
    this.messagesPopularidadUsuario = []
    this.messagesPopularidadReceta = []
  }

  async produce(topic, messages) {
    try {
      await this.producer.connect()
      await this.producer.send({
        topic: topic,
        messages: [messages],
      })
    } catch (error) {
      console.error(error)
    } finally {
      await this.producer.disconnect()
    }
  }

  //TOPICO NOVEDADES
  async consumeNovedades(topic) {
    const maxMessages = 5 // Número máximo de mensajes a consumir
    try {
      const consumer = this.kafka.consumer({ groupId: 'group' })
      await consumer.connect()
      await consumer.subscribe({ topic: topic })
      await consumer.run({
        eachMessage: async ({ message }) => {
          const jsonString = message.value.toString('utf-8')
          const jsonObject = JSON.parse(jsonString)
          this.messagesNovedades.push(jsonObject)

          if (this.messagesNovedades.length > maxMessages) {
            this.messagesNovedades.shift() // Eliminar el mensaje más antiguo
          }

          console.log(jsonObject)
        },
      })
    } catch (error) {
      console.error(error)
    }
  }

  lastMessageNovedades() {
    return this.messagesNovedades
  }

  //TOPICO COMENTARIOS
  async consumeComentarios(topic) {
    try {
      const consumer = this.kafka.consumer({ groupId: 'group' })
      await consumer.connect()
      await consumer.subscribe({ topic: topic })
      await consumer.run({
        eachMessage: async ({ message }) => {
          this.messagesComentarios.push(message.value.toString('utf-8'))
          console.log(message.value.toString())
        },
      })
    } catch (error) {
      console.error(error)
    }
  }

  messageComentarios() {
    return this.messageComentarios
  }

  //TOPICO POPULARIDAD USUARIO
  async consumePopularidadUsuario(topic) {
    try {
      const consumer = this.kafka.consumer({ groupId: 'group' })
      await consumer.connect()
      await consumer.subscribe({ topic: topic })
      await consumer.run({
        eachMessage: async ({ message }) => {
          this.messagesPopularidadUsuario.push(message.value.toString('utf-8'))
          console.log(message.value.toString())
        },
      })
    } catch (error) {
      console.error(error)
    }
  }

  messagesPopularidadUsuario() {
    this.messagesPopularidadUsuario
  }

  //TOPICO POPULARIDAD RECETA
  async consumePopularidadReceta(topic) {
    try {
      const consumer = this.kafka.consumer({ groupId: 'group' })
      await consumer.connect()
      await consumer.subscribe({ topic: topic })
      await consumer.run({
        eachMessage: async ({ message }) => {
          this.messagesPopularidadReceta.push(message.value.toString('utf-8'))
          console.log(message.value.toString())
        },
      })
    } catch (error) {
      console.error(error)
    }
  }
  messagesPopularidadReceta() {
    this.messagesPopularidadReceta
  }
}
module.exports = KafkaConfiguration
