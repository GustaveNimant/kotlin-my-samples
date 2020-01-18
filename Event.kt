// export CLASSPATH=/usr/share/java/commons-codec.jar
// https://kotlinlang.org/docs/reference/enum-classes.html
// https://www.baeldung.com/kotlin-enum
// https://www.tutorialkart.com/kotlin/enum-classes-in-kotlin/

sealed class DeliveryStatus {
    class Delivered : DeliveryStatus()
    class Delivering : DeliveryStatus()
    class NotDelivered(val error: String) : DeliveryStatus()
}

sealed class Direction {
    class Incoming(val from: String) : Direction()
    class Outgoing(val status: DeliveryStatus) : Direction()
}

sealed class ContentType {
    class Text(val body: String) : ContentType()
    class Image(val url: String, val caption: String) : ContentType()
    class Audio(val url: String, val duration: Int) : ContentType()
}

data class Event (val contentType: ContentType, val direction: Direction)

fun renderEvent(event: Event): Unit {
    when(event.contentType) {
        is ContentType.Text -> println("${event.contentType.body}")
        is ContentType.Audio -> println("Audio of ${event.contentType.duration} secs.")
        is ContentType.Image -> println("Image (${event.contentType.caption})")
    }
}

fun main(args: Array<String>) {

// An event list example but this could be retrieved from a Conversation data store
// in a real app

val events = listOf(
    Event(ContentType.Text("Hey, I'm Tony"), Direction.Incoming("Tony Stark")),
    Event(ContentType.Image("URL_TO_IMAGE", "Avengers"),  Direction.Incoming("Bruce Banner")),
    Event(ContentType.Audio("URL_TO_AUDIO", 15), Direction.Outgoing(DeliveryStatus.Delivered()))
)

for(event in events) {
    renderEvent(event)
}


}

