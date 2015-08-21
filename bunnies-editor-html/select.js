//Select  objects


function activateSelectionMode() {
  changeCurrentMouseListener(function(){}, selectElement);
}

function onSelectionMouseMove(event) {
  if (event.buttons == 1) {
    selectElement(event);
  }
}

function selectElement(event) {
  resetSelection();
  selectCircles(event);
  selectRectangles(event);
}

function resetSelection() {
    $("#editorCanvas").children().css('stroke', '');
}

function selectCircles(event) {
  $("#editorCanvas").find("circle").filter(function() {
    var diffX = Number.parseInt($(this).attr('cx')) - event.offsetX;
		var diffY = Number.parseInt($(this).attr('cy')) - event.offsetY;
    return Math.sqrt(Math.pow(diffX, 2) + Math.pow(diffY, 2)) < $(this).attr('r');
  }).each(function (child) {
    $(this).css('stroke', 'black');
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
    $(this).css('stroke', 'black');
  });
}
