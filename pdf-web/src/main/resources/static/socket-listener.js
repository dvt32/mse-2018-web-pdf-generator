var stompClient = null;

function connect() {
    var socket = new SockJS('http://localhost:8200/pdf-generator-service-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/pdf-messages', function (msg) {
            showMessage(msg.body);
        });
    });
}

function showMessage(message) {
    $("#messages").append(
    	"<br><tr><td>" + message + " <a href=\"/pdf-web/pdf-file\">Download</a></td></tr>"
    );
}

$(function () {
    connect();
});