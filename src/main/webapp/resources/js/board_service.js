/* global App */

'use strict';
App.factory('BoardSvr', ['$http','$q',function($http, $q) {
    return {
        list:function(curPage) {
            return $http.get('http://localhost:8080/she/board/getList/'+curPage).then (
                        function(response) {
                            console.log("[service:list] server call succeed!!");
                            return response.data;
                        }, 
                        function(errResponse) {
                            console.log("[Error while fetching Contents");
                            return $q.reject(errResponse);
                        }
                    );
        }
    };  
}]);
