var http = require('http');
var url = require('url');



var server = http.createServer(function (request, response) {
  var queryData = url.parse(request.url, true).query;
  response.writeHead(200, {"Content-Type": "text/plain"});

  if (queryData.sendsms) {
    // user submit GET request, ex: http://127.0.0.1:8000/?sendsms=true
    response.end('sendsms: ' + queryData.sendsms + '\n');


	var sinchSms = require('sinch-sms')({
        key: '43af4c97-9a8f-435d-87bb-5af95a65f0c4', 
        secret: '1zYWGBNhB0GwIPFJthABxQ=='
    });
   
  


	sinchSms.send('+60124103739', 'Dropbox Alert!!').then(function(response) {
    	//All good, response contains messageId
   	 console.log(response);
	}).fail(function(error) {
    	// Some type of error, see error object
    	console.log(error);
	});

  }
  //else if (queryData.pushData) {
    // user submit GET request, ex: http://127.0.0.1:8000/?pushData=yes&lat=101.20&long=3.212&expr=
  //  response.end('sendsms: ' + queryData.sendsms + '\n');
	

  //} 
  else {
    response.end("Hello attHack\n");
  }
});

// Listen on port 8000, IP defaults to 127.0.0.1
server.listen(8000);
