/*jslint devel: true */
/* eslint-disable no-console */
/*eslint no-undef: "error"*/
/*eslint-env node*/



var http = require('http'); 


var server = http.createServer();

var host = '172.30.1.46';
var port = 3000 ;
server.listen(port, host, '50000' , function(){ // 50000은 접속할수있는 클라이언트 수 
    
    console.log('웹서버가 시작되었습니다: $s %d',host, port );
    
});
