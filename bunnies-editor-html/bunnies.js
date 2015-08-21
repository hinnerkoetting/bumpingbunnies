"use strict";

var startX;
var startY;

var currentEditMode;
var currentColor = "black";

function setRectangleEditingMode() {
  currentEditMode = {
    element: "rect",
    init: function(event) {
        d3.select(".editedObject").
          attr('x', event.offsetX).
          attr('y', event.offsetY);
    },
    movementListeners: [
      adjustWidth, adjustHeight
    ]
  };
}

function setCircleEditingMode() {
  currentEditMode = {
    element: "circle",
    init: function(event) {
      d3.select(".editedObject").
        attr('cx', event.offsetX).
        attr('cy', event.offsetY);
    },
    movementListeners: [
      adjustRadius
    ]
  };
}

// setRectangleEditingMode();
setCircleEditingMode();


function createObject(event) {
  d3.select("#editorCanvas")
    .append(currentEditMode.element)
    .style('fill', currentColor)
    .attr('class', 'editedObject');
  currentEditMode.init(event);
}

function onDragCanvas(event) {
  var editedObjects = $(".editedObject");

  if (editedObjects.length == 0) {
    createObject(event);
    startX = event.offsetX;
    startY = event.offsetY;
  }
  editedObjects.each(function (object) {
    var editedObject = $(this);
    currentEditMode.movementListeners.forEach(function(listener) {
        listener(editedObject, event);
    });
  });

}

function adjustWidth(editedObject, event) {
  editedObject.attr('width', Math.abs(event.offsetX - startX));
  if (event.offsetX < editedObject.attr('x')) {
    editedObject.attr('x', event.offsetX);
  } else if (event.offsetX < Number.parseInt(editedObject.attr('x')) + Number.parseInt(editedObject.attr('width'))) {
    editedObject.attr('x', event.offsetX);
  }
}

function adjustHeight(editedObject, event) {
  editedObject.attr('height', Math.abs(event.offsetY - startY));
  if (event.offsetY < editedObject.attr('y')) {
    editedObject.attr('y', event.offsetY);
  } else if (event.offsetY < Number.parseInt(editedObject.attr('y')) + Number.parseInt(editedObject.attr('height'))) {
    editedObject.attr('y', event.offsetY);
  }
}

function adjustRadius(editedObject, event) {
  var diffX = Math.pow(event.offsetX - startX, 2);
  var diffY = Math.pow(event.offsetY - startY, 2);
  editedObject.attr('r', Math.sqrt(diffX + diffY));
}

function onMouseMoveCanvas(event) {
  if (event.buttons == 0) {
    d3.select(".editedObject").attr('class', '');
  }
  if (event.buttons > 0) {
    onDragCanvas(event);
  }
}

function getCanvas() {
  return $("#editorCanvas");
}

function changeColor(button) {
  currentColor = $(button).attr('color');
}

$( document ).ready(function() {
	registerMouseListener();
  generateColors();
});

function registerMouseListener() {
  var canvas = document.getElementById("editorCanvas");
  canvas.addEventListener('mousemove', function(event) {
      onMouseMoveCanvas(event);
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

function createButton(red, green, blue) {
  var button = document.createElement("button");
  button.innerHTML = "&nbsp;";
  var color = 'rgb(' + red + ', ' + green + ', ' + blue + ')';
  $(button).attr('color', color);
  $(button).css('background', color);
  $(button).attr('onclick', 'changeColor(this);');
  $(button).attr('class', "colorButton");
  $("#colors").append(button);
}
