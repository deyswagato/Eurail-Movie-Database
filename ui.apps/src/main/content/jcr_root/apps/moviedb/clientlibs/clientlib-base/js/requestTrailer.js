$("#requestTrailer").click(function(e) {
    e.preventDefault();
    $.ajax({
        type: "GET",
        url: "/content/trigger-servlet.txt",
        data: { 
            fname: $("#fname").val(),
            lname: $("#lname").val(),
            movieName: $("#movieName").val(),
            requestDescription: $("#requestDescription").val() 
        },
        success: function(result) {
            var frm = document.getElementsByName('request-trailer-form')[0];
            frm.reset();  // Reset all form data
			$("#result").html("Trailer has been requested");
        },
        error: function(result) {
			$("#result").html("Error Occurred");
        }
    });
});