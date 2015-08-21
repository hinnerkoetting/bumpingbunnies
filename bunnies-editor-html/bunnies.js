"use strict";

var startX;
var startY;

function createObject(event) {
  d3.select("#editorCanvas")
    .append("rect")
    .attr('class', 'editedObject')
    .attr('x', event.offsetX)
    .attr('y', event.offsetY)
    .attr('width', 5)
    .attr('height', 5);
}

function onDragCanvas(event) {
  var editedObjects = $(".editedObject");

  if (editedObjects.length == 0) {
    createObject(event);
    startX = event.offsetX;
    startY = event.offsetY;
  }
  editedObjects.each(function (object) {
      adjustWidth($(this), event);
      adjustHeight($(this), event);
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


$( document ).ready(function() {
            	 var canvas = document.getElementById("editorCanvas");
            	 canvas.addEventListener('mousemove', function(event) {
                	 onMouseMoveCanvas(event);
                	 });
            });
