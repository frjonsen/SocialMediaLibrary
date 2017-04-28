
function initFacebook(appid) {
    window.fbAsyncInit = function() {
    FB.init({
    appId      : appid,
    xfbml      : true,
    version    : 'v2.9'
    });
    FB.AppEvents.logPageView();

    FB.getLoginStatus(function(response) {
            if (response.status === 'connected') {
                console.log('Logged in.');
            }
            else {
                FB.login();
            }
        });
    };

    (function(d, s, id){
        var js, fjs = d.getElementsByTagName(s)[0];
        if (d.getElementById(id)) {return;}
        js = d.createElement(s); js.id = id;
        js.src = "//connect.facebook.net/en_US/sdk.js";
        fjs.parentNode.insertBefore(js, fjs);

        
    }(document, 'script', 'facebook-jssdk'));
}
$.get('http://localhost:5678/getsecret', {}, function(res) {
    initFacebook(res.FACEBOOK.APPID)
});
