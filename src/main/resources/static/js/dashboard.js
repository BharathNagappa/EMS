$(document).ready(function() {
    $('#mytasks').DataTable({
	"paging":   false,
	"ordering": false,
        "info":     false,
        "searching":false
});
$('#taskList').DataTable({
});
google.charts.load('current', {'packages':['corechart']});
google.charts.setOnLoadCallback(drawChart);
} 
     
);

function createTask()
{
	window.location.href="/createtask";
}

 function drawChart() {
	var arrays =[];
		arrays.push(['Status','count']);
		$('.statusMap').each(function(){
			var arr = [];
			arr.push(this.id);
			arr.push(this.value);
			arrays.push(arr);
			});
			

        var data = google.visualization.arrayToDataTable(arrays);

        var options = {
          title: 'My Task'
        };

        var chart = new google.visualization.PieChart(document.getElementById('piechart'));

        chart.draw(data, options);
      }


