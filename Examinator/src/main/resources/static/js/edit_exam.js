$(function() {
	window.addEventListener('scroll', animOnScroll);

	swap_question();
	siblings_highlight();
	var scrollHeight = Math.max(
  document.body.scrollHeight, document.documentElement.scrollHeight,
  document.body.offsetHeight, document.documentElement.offsetHeight,
  document.body.clientHeight, document.documentElement.clientHeight
    )

	window.scrollTo(0,scrollHeight);
});

function animOnScroll() {
	const elem = document.querySelector('.save_line');
	if (pageYOffset > 70) {
		elem.classList.replace('removeAble', 'noneRemoveAble')
	} else {
		elem.classList.replace('noneRemoveAble', 'removeAble')
	}
}

function swap_question() {
	$('.wrap_question').on('dblclick', '.question', function () {
		var a = $('.wrap_question').find('.change');
		if( a.get(0)===$(this).get(0)) {
			a.removeClass('change');
			return false;
		}
		$(this).addClass('change');
		if(a.length>=1) {
			var b = $(this).clone();
			var c = a.clone();
			a.replaceWith(b);
			$(this).replaceWith(c);
			$('.question').removeClass('change');
			siblings_highlight();
		}});
}

function siblings_highlight() {
	const siblings = el => [].slice.call(el.parentNode.children).filter(child => (child !== el))

	const answers = document.querySelectorAll('.answer')

	answers.forEach(answer => {
		answer.addEventListener('mouseenter', () => {
			siblings(answer).forEach(el => {
				el.classList.add('answer_sibling')
			})
		})
		answer.addEventListener('mouseleave', () => {
			siblings(answer).forEach(el => {
				el.classList.remove('answer_sibling')
			})
		})
	})
};