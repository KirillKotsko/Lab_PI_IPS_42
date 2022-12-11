$(function() {
window.addEventListener('scroll', animOnScroll);
var currentMinutes = parseInt($('#dur').val());
var time = currentMinutes * 60;
setTimeout(function() {$('form').submit();},1000 * 60 * currentMinutes);

const timeEl = document.getElementById('time');
setInterval(updateCountdown, 1000);

function updateCountdown() {
	let minutes = Math.floor(time/60);
	if (minutes < 10) {
		minutes = '0' + minutes;
	}
	let seconds = time % 60;
	if (seconds < 10) {
		seconds = '0' + seconds;
	}
	timeEl.innerText = minutes + ':' + seconds;
	time--;
}	
	
	});
	
	function animOnScroll() {
	const elem = document.querySelector('.save_line');
	if (pageYOffset > 70) {
		elem.classList.replace('removeAble', 'noneRemoveAble')
	} else {
		elem.classList.replace('noneRemoveAble', 'removeAble')
	}
}
