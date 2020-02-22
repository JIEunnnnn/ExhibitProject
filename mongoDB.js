

//몽고db 와 몽구스의 차이점은
//몽고db는 모듈을 정의할수없는 자유로운 db
//몽구스는 스키마를 정의해서 내맘대로 ㅇㅇ 구조정의가능한 ORM)
//object-realtaional mapping 은 모델이 데이터베이스의 데이터와 직접 연결되는 object이고 스키마는 이 데이터의 구조? 틀? 


var mongoose = require('mongoose');


var db = mongoose.connection; 


// db연결실패? 
db.on('error', function(){
    
    console.log('몽구스연결실패,...');

});

// db연결성공? 
db.once('open', function(){
   
    console.log('몽구스 연결 성공ㅇㅇ ');
    
    
});

mongoose.connect('mongodb://localhost:27017/local'); 
 // ip주소,포트주소,데이터베이스이름
// db연결 정보....



var schemaexhib = mongoose.Schema({
   
    // 이름 예상인원 현재인원수 예상시간 관별크기 전시회정보 전시회내 관정보 
    // 혼잡도는 인원수와 관별크기로 계산 
    
    name : 'string',
    expecnum : 'number',
    nownum : 'number',
    expectime : 'number' ,
    size : 'number',
    data : 'string',
    detaildata : 'string'
    

});



// 정의된 스키마를 객체처럼 사용!!!!!!!!
var funcexhib = mongoose.model('Schema', schemaexhib); 

//전시회데이터 삽입 
var newexhibit = new funcexhib({name : 'exhibit2', expecnum : '100', nownum: '23', expectime : '50'
                               , size : '1000', data : '전시회임', detaildata : '전시회1관임'});


newexhibit.save(function(error, data){
    
    
    if(error){
        console.log('데이터삽입실패');
    }
    else{
        console.log('데이터삽입성공');
    }
    
}); 


// 아... 비동기라서 함수 실행시간이 달라....... 

funcexhib.find(function(error, schemaexhib ){
   
if(error){
        console.log(error);
    }else{
        console.log(schemaexhib);
    }
        
    });
    




