/* global App */

'use strict';
App.controller('BoardController', ['$scope', 'BoardService',
 function($scope, BoardService) {
 var self = this;
 self.board={id:null,name:'',passwd:'',title:'',content:''};
 self.page=[];

 //리스트 보기
 self.list = function(curPage){
 BoardService.list(curPage)
 .then(
 function(data) {
 self.page = data;
 console.log("[controller:list]", self.page);
 //alert("목록보기 성공!");
 },
 function(errResponse){
 console.error('Error while fetching page...');
 }
 );
 };
 self.list(0);
}]);