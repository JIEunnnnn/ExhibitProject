
/*jslint devel: true */
/* eslint-disable no-console */
/*eslint no-undef: "error"*/
/*eslint-env node*/

// db에 현재인원수를 받아오면 업데이트시키는 문서 필요....! Update./......? 

var express = require('express'); // 웹서버용 모듈 서치
var http = require('http');
var app = express();  // app은 express의 미들웨어=함수 
 var bodyparser = require('body-parser'); // body-parser 은 post 위한 모듈 ㅇㅇ 
// var path = require('path'); 
var mongoose = require('mongoose');


const path = require('path');
const fixturesPath = path.join(__dirname, 'media');
const extractFrame = require('ffmpeg-extract-frame');
const ffmpeg = require('ffmpeg');
const PythonShell = require('python-shell');
var count = 0;

mongoose.connect('mongodb://localhost:27017/local', { useNewUrlParser:  true, useUnifiedTopology: true });


// 노드에서 안드로이드로 이미지 전송할때에는  multer이라는 모듈을 사용함... 
// post방식으로 사진 전송....! 
// 근데 mongodb 에 사진 받아서 전송은 불가능한건가... 아 ㅅㅄ
// db에 과부하가 걸리기때문에 관련경로같은 정보들만 저장하고 
//서버에 파일시스템을 만들어서 저장함 
// ㅇㅅㅇ....? 

// 파일업로드용..미들웨어 
var multer = require('multer');
var fs = require('fs');



var db = mongoose.connection; 


// router.route


app.use(bodyparser.urlencoded({ extended: false }));
 app.use(bodyparser.json()); // post용! json 형식으로 
// app.use(express.static('public'));
app.use(express.static(path.join(__dirname,'public')));

app.set('port', process.env.PORT ||3000); 

// db연결실패? 
db.on('error', function(){
    console.log('몽구스연결실패,...');
});

// db연결성공? 
db.once('open', function(){   
    console.log('몽구스 연결 성공ㅇㅇ ');
});

// mongoose.connect(uri, );
//MongoClient.connect('mongodb://localhost:27017',{useUnifiedTopology:true}, function (err, client) {

 

// 할게... 라우터? 처리해서 
// 안스에서 메세지 전송하면 그걸로 몽고db 에 가서 저장된데이터 출력하는 그런..ㅠ 
//https://velopert.com/594 참고사이트 



var schemaexhib = mongoose.Schema({
   
    // 이름 예상인원 현재인원수 예상시간 관별크기 전시회정보 전시회내 관정보 
    // 혼잡도는 인원수와 관별크기로 계산 
    
    // 제목 사진 예상시간 예상인원 
    // name, expecnum, expectime, data, detaildata
    
    name : 'string',
    expecnum : 'number',
    expectime : 'number' ,
    title : 'string',
    data : 'string',
    detaildata : 'string',
    guidedata : 'array',
    guidedata : 'array',
    exhibitpeople :'array',
    exhibitsize : 'array',
    exhibitname : 'array',
    exhibitdetaildata : 'array'
    

});



// 클라가 서버에게 1전송하면 return 값... 보내고 
// 클라가 서버에게 아무것도 전송안하면.... 예시값...? 아 뭐라하지

 var funcexhib = mongoose.model('Schema', schemaexhib); 


// 정의된 스키마를 객체처럼 사용!!!!!!!!
// var funcexhib = mongoose.model('Schema', schemaexhib); 
 


// var funexhibit1 = mongoose.model('Ex1', exhibit1); 



//var findalldata = 
    // 콜백함수로 데이터 저장후... 



app.use(function(req, res, next) { // 함수임 ㅇㅇ 
 
    //res.send({name:'test', age:'24'});

    // 여기서....mongoDB에 접속해서 데이터 출력하게끔? 
     // name, expecnum, expectime, data, detaildata
  //  var query = funcexhib.find({}).select('')
    
  
    
    var msg = req.query.msg || req.body.msg;
    
    
        if(msg == 'exhibit1'){

            console.log('exhibit1 실행중!!!!');
          
            
                  
            
              funcexhib.find()
                        .select('title data detaildata guidedata exhibitpeople exhibitsize exhibitname exhibitdetaildata -_id')
                        .where('name').equals('exhibit1')
                        .exec(function(error, schemaexhib){    
                            if(error){
                                console.log(error);  
                            }else{
                                console.log(schemaexhib);
                                res.send(schemaexhib);
                                    //  여기에 파일이미지도 같이 전송해야ㅣㅣㅣㅈ....
                                }
    
        
                    });
            
                  
    }else if(msg == 'exhibit2'){
    
                console.log('exhibit2 실행중!!!!');
                        
                           funcexhib.find()
                            .select('title data detaildata guidedata exhibitpeople exhibitsize exhibitname exhibitdetaildata -_id')
                               .where('name').equals('exhibit2')
                               .exec(function(error, schemaexhib){    
                               if(error){
                                   console.log(error);
                               }else{
                                   console.log(schemaexhib);
                                   res.send(schemaexhib);
        
                                }
                          
    
                           });
    
    }else{
        
        console.log('MainActivity 실행중!!!!');
       
        
        funcexhib.find() // MainActiviy ㅇㅇ 
            .select('name expecnum expectime data detaildata -_id') // 사진 
            .where()
            .exec(function(error, schemaexhib){    
            if(error){
                    console.log(error);
            }else{
                    console.log(schemaexhib);
                    res.send(schemaexhib);
                //   console.log()
                   //
        /*
           next(); 
                
                    fs.readFile('./public/exhibit.jpg', function(err, data){
                    //  res.writeHead(200, { 'Content-Type' : 'image/jpeg'}); 
                    if(!data){
                         console.log("이미지전송실패");
                    }else{
                    
                    res.set('Context-Type', 'image/jpg');
                        //res.writeHead(200, { "Context-Type": "image/jpg" }); 이거는 오류남 ㅇㅇ 
                    res.send(data);
                        // 이미지전송은 알겠어... 근데 데이터값이랑 같이 전송해야할거같은데... 이걸 어떻게하지? 
                   
                    console.log("이미지전송성공");
                    console.log(data)
                    // res.end(); 
                    }

                });
        
        */
             
            
                            }
        }); 
        
      
    }
    
   
    
    
});

        
    
    
    /*
    
    if(msg.equals('exhibit1')){
        
         
            console.log('exhibit1 실행중!!!!');
                  
                    funcexhib.find()
                        .select('name expecnum expectime data detaildata -_id')
                        .where('name').equals('exhibit1')
                        .exec(function(error, schemaexhib){    
                            if(error){
                                console.log(error);  
                            }else{
                                console.log(schemaexhib);
                                res.send(schemaexhib);
        
                                }
    
        
                    });
    }else if(msg.equals('exhibit2')){
    
                console.log('exhibit2 실행중!!!!');
                        
                           funcexhib.find()
                                .select('name expecnum expectime data detaildata -_id')
                               .where('name').equals('exhibit2')
                               .exec(function(error, schemaexhib){    
                               if(error){
                                   console.log(error);
                               }else{
                                   console.log(schemaexhib);
                                   res.send(schemaexhib);
        
                                }
                          
    
                           });
    
    
    }else{
        
         console.log('MainActivity 실행중!!!!');
        
        
        funcexhib.find() // MainActiviy ㅇㅇ 
            .select('name expecnum expectime data detaildata -_id') // 사진 
            .exec(function(error, schemaexhib){    
            if(error){
                    console.log(error);
            }else{
                    console.log(schemaexhib);
                    res.send(schemaexhib);
        
            }
        });   
}
*/



  
 

// 사진을 db에 저장해서 서버에서 불러오게끔 설정하기..! => 키참조? 
// 데이터베이스 설정하기..  


http.createServer(app).listen(app.get('port'), function(){
    console.log('익스프레스 서버 시작 ');
    
});




/*





// let은 var 과 다르게 변수에 재할당이 가능하다. 
let users = [
    
    {
    id : 1, name : 'jieun'
}
    
    
]

app.get('/users',(req,res)=>{
    
    console.log('안드로이드 통신테스트');
    res.json(users);  // 응답객체 
   
    
});

app.listen(3000,() => {
    console.log('포트는 3000');
    
});

*/

/*

app.use(function(req, res, next ){ // 요청객체(get), 응답객체(post), next를 통해 다음 미들웨어로 전송
                                // 미들웨어 = 클라이언트의 요청이 들어오면 가로채서 처리하는것, 요청을 받는다 
    
    console.log('첫번째 미들웨어에서 요청을 처리함.? '); 
    
    
   //    next(); // 다음 이들웨어에 전달하기 
}); 


*/

