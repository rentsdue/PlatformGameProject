const canvas = document.getElementById('gameCanvas');
const ctx = canvas.getContext('2d');

fetch('game.wasm').then(response =>
    response.arrayBuffer()
).then(bytes =>
    WebAssembly.instantiate(bytes, {
        env: {
            // Provide any necessary imports here
        }
    })
).then(results => {
    const instance = results.instance;

    // Assuming your WASM module has an 'init' function
    instance.exports.init();

    function gameLoop() {
        // Call the WASM update function
        instance.exports.update();

        // Call the WASM render function
        instance.exports.render();

        // Clear the canvas for the next frame
        ctx.clearRect(0, 0, canvas.width, canvas.height);

        requestAnimationFrame(gameLoop);
    }

    gameLoop();
}).catch(console.error);

async function loadWasm() {
    try {
        const response = await fetch('build/teavm/game.wasm');
        const bytes = await response.arrayBuffer();
        const { instance } = await WebAssembly.instantiate(bytes);

        // Assuming your WASM module has a 'main' function
        instance.exports.main();
    } catch (err) {
        console.error("Failed to load WASM file", err);
    }
}

window.onload = loadWasm;
