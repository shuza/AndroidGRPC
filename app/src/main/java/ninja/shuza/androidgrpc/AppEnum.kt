package ninja.shuza.androidgrpc

/**
 *
 * :=  created by:  Shuza
 * :=  create date:  28-Jun-18
 * :=  (C) CopyRight Shuza
 * :=  www.shuza.ninja
 * :=  shuza.sa@gmail.com
 * :=  Fun  :  Coffee  :  Code
 *
 **/

sealed class AppEnum

object HttpResponseCode:AppEnum(){
    const val SUCCESS = 200
    const val FAILED = 400
}