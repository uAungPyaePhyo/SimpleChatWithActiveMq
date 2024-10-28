const socket = new SockJS('http://localhost:8080/ws');
const stompClient = Stomp.over(socket);

stompClient.connect({}, function (frame) {
    console.log('Connected: ' + frame);
    stompClient.subscribe('/queue/messages', function (message) {
        showMessage(JSON.parse(message.body));
    });
});

function sendMessage() {
    const message = {
        sender: document.getElementById("sender").value,
        content: document.getElementById("message").value
    };
    stompClient.send("/app/chat", {}, JSON.stringify(message));
}

function showMessage(message) {
    const messages = document.getElementById("messages");
    const messageElement = document.createElement("div");
    messageElement.innerText = message.sender + ": " + message.content;
    messages.appendChild(messageElement);
}
