//Select  objects

var lastX;
var lastY;

function resetDrag() {
  lastX = -1;
  lastY = -1;
}

function activateSelectionMode() {
  changeCurrentMouseListener(moveSelectedElements, selectElement);
}

function moveSelectedElements(event) {
  if (event.buttons == 1) {
    if (lastX != -1 && lastY != -1) {
      $("circle.selected").each(function() {
        $(this).attr('cx', Number.parseInt($(this).attr('cx')) + event.offsetX - lastX);
        $(this).attr('cy', Number.parseInt($(this).attr('cy')) + event.offsetY - lastY);
      });
      $("rect.selected").each(function() {
        $(this).attr('x', Number.parseInt($(this).attr('x')) + event.offsetX - lastX);
        $(this).attr('y', Number.parseInt($(this).attr('y')) + event.offsetY - lastY);
      });
    }
    lastX = event.offsetX;
    lastY = event.offsetY;
  } else {
    resetDrag();
  }
}

function selectElement(event) {
  resetSelection();
  selectCircles(event);
  selectRectangles(event);
}

function resetSelection() {
    $("#editorCanvas").children().attr('class', '');
}

function selectCircles(event) {
  $("#editorCanvas").find("circle").filter(function() {
    var diffX = Number.parseInt($(this).attr('cx')) - event.offsetX;
		var diffY = Number.parseInt($(this).attr('cy')) - event.offsetY;
    return Math.sqrt(Math.pow(diffX, 2) + Math.pow(diffY, 2)) < $(this).attr('r');
  }).each(function (child) {
    $(this).attr('class', 'selected');
  });
}

function selectRectangles(event) {
  $("#editorCanvas").find("rect").filter(function() {
    var tX = Number.parseInt($(this).attr('x'));
    var tY = Number.parseInt($(this).attr('y'));
    var xFits = tX < event.offsetX && tX +  Number.parseInt($(this).attr('width')) > event.offsetX;
    var yFits = tY < event.offsetY && tY +  Number.parseInt($(this).attr('height')) > event.offsetY;
    return xFits && yFits;
  }).each(function (child) {
    $(this).attr('class', 'selected');
  });
}
