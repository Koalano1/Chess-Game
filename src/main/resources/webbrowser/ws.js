/**
 * Initializes a WebSocket connection using SockJS and STOMP.
 * @param {string} roomId - The ID of the room to connect to.
 * @param {string} principal - The principal/user identifier.
 * @param {string} baseUrl - The base URL of the WebSocket server (default: http://localhost:8081/ws).
 */
function initializeWebSocket(roomId, principal, baseUrl = 'http://localhost:8081/ws') {
    if (!roomId) {
        console.error('Error: Room ID is required to establish WebSocket connection.');
        return;
    }

    if (!principal) {
        console.error('Error: Principal is required to establish WebSocket connection.');
        return;
    }

    // Establish a WebSocket connection using SockJS
    const socket = new SockJS(baseUrl);
    const stompClient = Stomp.over(socket);

    // Connect to the WebSocket server
    stompClient.connect({}, function (frame) {
        console.log('Connected to WebSocket server: ' + frame);

        // Send a message to the server
        const messagePayload = {
            roomId: roomId,
            principal: principal
        };

        stompClient.send(
            `/app/room/${roomId}/start`,
            {},
            JSON.stringify(messagePayload)
        );
        console.log(`Message sent to room: ${roomId}`);

        // Subscribe to receive messages for the specific room
        stompClient.subscribe(`/room/${roomId}`, function (message) {
            console.log('Message received from server:', message.body);
        });
    });

    // Handle WebSocket disconnection
    stompClient.onclose = function () {
        console.log('WebSocket connection closed.');
    };

    // Handle WebSocket errors
    stompClient.onerror = function (error) {
        console.error('WebSocket error:', error);
    };
}

// Example usage
// Uncomment and modify as needed:
// const roomId = prompt('Enter Room ID:');
// const principal = 'tranglh';
// initializeWebSocket(roomId, principal);
