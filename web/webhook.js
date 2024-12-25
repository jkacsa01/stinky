// 1. WebSocket kapcsolat létrehozása
const name = window.prompt("Mi a neved?","");
const socket = new WebSocket('ws://localhost:8025/stinky'); // Itt az URL-t cseréld ki a saját szervered URL-jére!
var pointer = 0;

// 2. WebSocket kapcsolat nyitásakor
socket.addEventListener('open', function (event) {
    console.log('WebSocket kapcsolat létrejött!');
    // Példa üzenet küldésére
	
	// new TextEncoder().encode(message)
	const byteArray = new Uint8Array([...new Uint8Array([0, name.length]), ...(new TextEncoder().encode(name))]); // Az 'A', 'B', 'C', 'D' karakterek byte formátumban

	console.log('Az ArrayBuffer tartalma (bytes):', byteArray);

    // ArrayBuffer létrehozása
    const arrayBuffer = byteArray.buffer;
    
    // Üzenet küldése bináris adatként
    socket.send(arrayBuffer);
});

function readString(array) {
	var result = new TextDecoder().decode(array.slice(pointer+1, pointer+1+array[pointer]));
	pointer += array[pointer]+1;
	return result;
}

function readByte(array) {
	return array[pointer++];
}

function igen() {
	socket.send(new Uint8Array([1, 0]));
}

function ut() {
	socket.send(new Uint8Array([1, 1]));
}

// 3. WebSocket üzenet érkezésekor
socket.addEventListener('message', function (event) {
	event.data.arrayBuffer().then(function(buffer) {
		// ArrayBuffer byte adatainak kiíratása
		const data = new Uint8Array(buffer);
		console.log('Az ArrayBuffer tartalma (bytes):', data);
		
		let id = readByte(data);
		let card = "";
		let slot = 0;
		let enemy = "";
		
		console.log(pointer);
		if (id === 0) {
			slot = readByte(data);
			card += readString(data);
			card += readString(data);
			pakli.innerText = card;
		} else if (id === 1) {
			let yes = readString(data);
			(yes !== name ? ellenfel : mi).classList = "kInfo myTurn";
			(yes !== name ? mi : ellenfel).classList = "kInfo";
		} else if (id === 2) {
			(readString(data) !== name ? ellenszam : miszam).innerText = readByte(data);
		} else if (id === 3) {
			alert(readString(data));
		}
		console.log(pointer);
		pointer = 0;
	});
});

// 4. WebSocket hibák kezelése
socket.addEventListener('error', function (event) {
    console.error('WebSocket hiba történt:', event);
});

// 5. WebSocket kapcsolat bezárásakor
socket.addEventListener('close', function (event) {
    console.log('WebSocket kapcsolat bezárult.');
});
