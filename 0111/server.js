const express = require('express');
const app = express();
const bodyParser = require('body-parser');
const cookieParser = require('cookie-parser');
const { list } = require('mongodb/lib/gridfs/grid_store');
const MongoClient = require('mongodb').MongoClient;
const methodOverride = require('method-override');
require('dotenv').config(); 
app.use(cookieParser());
app.set('view engine', 'ejs');
app.use('/public', express.static('public'));
app.use(methodOverride('_method'));

var db;
MongoClient.connect(process.env.DB_URL, function(error, client){
   //연결되면 할일
   if(error) return console.log(error)

   db = client.db('todoapp');

   app.listen(process.env.PORT, () =>
   console.log('listening on 9090')
   );
});

app.use(bodyParser.urlencoded({extended : true}));

// 누군가가 /pet 으로 방문을 하면..
// pet관련된 안내문을 띄워주자

//app.get('/pet', function(요청, 응답){
//    응답.send('펫 용품 쇼핑가능한 사이트입니다.')
//});

 app.get('/pet', (req, res) => {
    res.send('펫 용품을 쇼핑가능한 사이트입니다.')
});

 app.get('/beauty', (req,res) => {
    res.send('뷰티용품을 쇼핑가능한 사이트입니다.')
 });

 app.get('/', (req,res) => {
    res.render('index.ejs');
 });

 app.get('/write', (req,res) => {
    res.render('write.ejs');
 });

 app.get('/list', (req,res) => {
   //데이터 꺼내기
   db.collection('post').find().toArray(function(error, result){
      console.log(result);
      res.render('list.ejs', { posts : result});
   });   
 });

 app.delete('/delete', function(req, res){
   console.log(req.body);
   req.body._id = parseInt(req.body._id);
   db.collection('post').deleteOne(req.body,function(){
      console.log('삭제완료');
      res.status(200).send({message : '성공했습니다.'});
   });
 });

 app.get('/detail/:id', function(req, res){
   db.collection('post').findOne({'_id' : parseInt(req.params.id)}, function(error, result){
      console.log(result);
      res.render('detail.ejs', { data : result});
   });
 });

 app.get('/edit/:id', function(req, res){
   db.collection('post').findOne({'_id' : parseInt(req.params.id)}, function(error, result){
      res.render('edit.ejs', { post : result});
   });
 });

 app.put('/edit', (req,res) => {
   db.collection('post').findOneAndUpdate(
      {'_id': parseInt(req.body._id)}, 
      {$set: {'제목': req.body.title,'날짜': req.body.date}},
      function(error, result){
      console.log('수정완료');
      res.redirect('/list')
   });
});

const passport = require('passport');
const LocalStrategy = require('passport-local');
const session = require('express-session');

app.use(session({secret : '0326', resave : true, saveUninitialized: false}));
app.use(passport.initialize());
app.use(passport.session());

app.get('/login', function(req, res){
   res.render('login.ejs')
});

app.post('/login', passport.authenticate('local',{
   failureRedirect : '/fail'
}), function(req, res){
   res.redirect('/')
});

app.get('/mypage', 로그인했니, function(req, res){
   console.log('유저정보=>' + JSON.stringify(req.user));
   res.render('mypage.ejs');
});

app.get('/search', (req, res) => {
   console.log(req.query.value);
   var 검색조건 = [
      {
        $search: {
          index: 'titleSearch',
          text: {
            query: req.query.value,
            path: ['제목', '날짜']  // 제목날짜 둘다 찾고 싶으면 ['제목', '날짜']
          }
        }
      },
      //{$project : {제목: 1, _id: 0, score: {$meta: "searchScore"} }}
  ] 
   db.collection('post').aggregate(검색조건).toArray((error,result) => {
      console.log(result);
      res.render('search.ejs', {posts : result});
   });
})

function 로그인했니(req, res, next){
   if (req.user) next();
   else res.send('로그인 안하셨는데요?');  
}

passport.use(new LocalStrategy({
   usernameField: 'id',
   passwordField: 'pw',
   session: true,
   passReqToCallback: false,
 }, function (입력한아이디, 입력한비번, done) {
   //console.log(입력한아이디, 입력한비번);
   db.collection('login').findOne({ id: 입력한아이디 }, function (에러, 결과) {
     if (에러) return done(에러)
     //console.log(결과);
     if (!결과) return done(null, false, { message: '존재하지않는 아이디요' })
     if (입력한비번 == 결과.pw) {
       return done(null, 결과)
     } else {
       return done(null, false, { message: '비번틀렸어요' })
     }
   })
 }));
 
passport.serializeUser(function(user, done){
   done(null, user.id);
});

passport.deserializeUser(function(아이디, done){
   db.collection('login').findOne({ id: 아이디}, function(err, rslt){
      done(null, rslt);
   });
});

app.post('/register', function(req,res){
   db.collection('login').insertOne( { id : req.body.id, pw : req.body.pw }, function(error,result){
		res.redirect('/');
   });  
});

 // POST 요청이 들어올 시
 app.post('/add', (req,res) => {
   console.log(req.body.title);
   console.log(req.body.date);
   console.log(req.cookies['connect.sid']);
   db.collection('counter1').findOne({'name' : 'postNum'}, function(error, result){
      console.log(result.totalPost);
      var 총게시물갯수 = result.totalPost;
      db.collection('post').insertOne({ '_id' : 총게시물갯수 + 1, '제목' : req.body.title, '날짜' : req.body.date, '작성자' : req.body._id }, function(error, result){
         console.log('저장완료');
         db.collection('counter1').updateOne(
            {'name':'postNum'},
            { $inc : {totalPost:1}}, 
            function(error, result){
               console.log('전송완료')
               res.redirect('/list')
            if(error){
               return console.log(error);
            }
         });
      }); 
   });
});

