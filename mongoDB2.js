

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



var exhibit1 = mongoose.Schema({
    
    name : 'string',
    nownum : 'number',
    size : 'number',
    detaildata : 'string',
    firstex : 'string',
    secondex : 'string',
    thirdex : 'string',
    fourthex : 'string'
    
    
}); 


// 정의된 스키마를 객체처럼 사용!!!!!!!!
var funcexhib1 = mongoose.model('Schema', exhibit1); 

//전시회데이터 삽입 
var newexhibit = new funcexhib1({ name : 'exhibit1',
    nownum : '11',
    size : '1000',
    detaildata : '전시고고1관',
    firstex : '111111',
    secondex : '222222',
    thirdex : '33333333',
    fourthex : '44444444'});


newexhibit.save(function(error, data){
    
    
    if(error){
        console.log('데이터삽입실패');
    }
    else{
        console.log('데이터삽입성공');
    }
    
}); 


// 아... 비동기라서 함수 실행시간이 달라....... 

funcexhib1.find(function(error, exhibit1 ){
   
if(error){
        console.log(error);
    }else{
        console.log(exhibit1);
    }
        
    });
    




