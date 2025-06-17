 const modal = document.querySelector('.js-modal'); // layer要素に付与したjs-modalクラスを取得し変数に格納
 const modalButton = document.querySelector('.js-modal-button'); // button要素に付与したjs-modal-buttonクラスを取得し、変数に格納

 // 追記
 const modalClose = document.querySelector('.js-close-button');　// xボタンのjs-close-buttonを取得し変数に格納

 modalButton.addEventListener('click', () => {
   modal.classList.add('is-open');
 });

 // 追記
 modalClose.addEventListener('click', () => { // xボタンをクリックしたときのイベントを登録
   modal.classList.remove('is-open'); 
 });
 
 const modal2 = document.querySelector('.js-modal2'); // layer要素に付与したjs-modalクラスを取得し変数に格納
 const modalButton2 = document.querySelector('.js-modal-button2'); // button要素に付与したjs-modal-buttonクラスを取得し、変数に格納

 // 追記
 const modalClose2 = document.querySelector('.js-close-button2');　// xボタンのjs-close-buttonを取得し変数に格納

 modalButton2.addEventListener('click', () => {
   modal2.classList.add('is-open2');
 });

 // 追記
 modalClose2.addEventListener('click', () => { // xボタンをクリックしたときのイベントを登録
   modal2.classList.remove('is-open2'); 
 });