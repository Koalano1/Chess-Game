const socket = new WebSocket("ws://localhost:8081/ws/room/189855ef-a225-42c4-9938-b40740ec8ab5/start");

socket.onopen = () => {
    console.log("WebSocket connection established");

    // Send the test message
    socket.send(JSON.stringify({
        "destination": "/app/room/785117b0-d8d8-4242-8196-50a7c934d220/start",
        "content-type": "application/json"
    }));
};

socket.onmessage = (message) => {
    console.log("Message received from server:", message.data);
};

socket.onerror = (error) => {
    console.error("WebSocket error:", error);
};
