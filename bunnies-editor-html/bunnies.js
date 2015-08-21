"use strict";

var currentMoveListener;
var currentClickListener;

$( document ).ready(function() {
	registerMouseListener();
  generateColors();
});

function registerMouseListener() {
  var canvas = document.getElementById("editorCanvas");
  canvas.addEventListener('mousemove', function(event) {
      currentMoveListener(event);
  });
  canvas.addEventListener('click', function(event) {
      currentClickListener(event);
  });
}

function generateColors() {
  for (var red = 0; red <= 256; red += 128) {
    for (var green = 0; green <= 256; green += 128) {
      for (var blue = 0; blue <= 256; blue += 128) {
        createButton(red == 256 ? 255 : red, green == 256 ? 255 : green, blue == 256 ? 255 : blue);
      }
    }
  }
}

function changeCurrentMouseListener(mouseListener, clickListener) {
  currentMoveListener = mouseListener;
  currentClickListener = clickListener;
}
