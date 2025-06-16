//負担度を表す星
const stars = document.querySelectorAll('#star-rating .star');
const burdenInput = document.getElementById('housework_level');

stars.forEach(star => {
  star.addEventListener('click', () => {
    const value = parseInt(star.dataset.value);
    burdenInput.value = value; // hiddenに保存

    // 星を色付け
    stars.forEach(s => {
      if (parseInt(s.dataset.value) <= value) {
        s.classList.add('selected');
      } else {
        s.classList.remove('selected');
      }
    });
  });
});
