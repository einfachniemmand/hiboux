function isMobileDevice() {
  const ua = navigator.userAgent || navigator.vendor || window.opera;
  return /iPhone|iPod|iPad|Android|Windows Phone|BlackBerry|webOS|Opera Mini|IEMobile|MeeGo|Nokia|SymbianOS/i.test(ua);
}

document.addEventListener("DOMContentLoaded", () => {
    const audioDown = new Audio('../static/media/button-down.mp3');
    const audioUp = new Audio('../static/media/button-up.mp3');
    document.querySelectorAll("button.default:not(.disabled), label, .add-big, .default-list").forEach((el) => {
        let downTimestamp = 0;
        let upTimeout = null;
        const playDown = () => {
            audioDown.currentTime = 0;
            audioDown.volume = 0.8;
            audioDown.play();
            downTimestamp = Date.now();
        };
        const playUp = () => {
            const elapsed = Date.now() - downTimestamp;
            const delay = Math.max(0, 170 - elapsed);
            if (upTimeout) clearTimeout(upTimeout);
            upTimeout = setTimeout(() => {
                audioUp.currentTime = 0;
                audioUp.volume = 0.8;
                audioUp.play();
            }, delay);
        };
        if (!isMobileDevice()) {
            el.addEventListener('mousedown', playDown);
        }
        el.addEventListener('mouseup', playUp);
        el.addEventListener('touchstart', playDown);
        el.addEventListener('touchend', playUp);
    });
});